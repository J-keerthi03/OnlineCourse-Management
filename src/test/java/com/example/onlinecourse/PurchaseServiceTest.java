package com.example.onlinecourse;
import com.example.onlinecourse.Dto.PurchaseDTO;
import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.model.Purchase;
import com.example.onlinecourse.repository.CourseRepository;
import com.example.onlinecourse.repository.PurchaseRepository;
import com.example.onlinecourse.service.PurchaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PurchaseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPurchaseProduct_success() {
        // Arrange
        Long courseId = 1L;
        int slotsPurchased = 2;
        BigDecimal coursePrice = BigDecimal.valueOf(50.00);

        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setCourseId(courseId);
        purchaseDTO.setSlotsPurchased(slotsPurchased);

        Course course = new Course();
        course.setId(courseId);
        course.setPrice(coursePrice);
        course.setSlots(5);  // Sufficient slots available

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(purchaseRepository.save(any(Purchase.class))).thenAnswer(invocation -> invocation.getArgument(0));

         Purchase result = purchaseService.purchaseProduct(purchaseDTO);

        assertEquals(slotsPurchased, result.getSlotsPurchased());
        assertEquals(course, result.getCourse());
        assertEquals(coursePrice.multiply(BigDecimal.valueOf(slotsPurchased)), result.getTotalAmount());

        verify(courseRepository).save(course);
        verify(purchaseRepository).save(result);
    }


    @Test
    void testPurchaseProduct_courseNotFound() {
        // Arrange
        Long courseId = 1L;
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setCourseId(courseId);

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            purchaseService.purchaseProduct(purchaseDTO);
        });

        assertEquals("Invalid purchase data.", exception.getMessage()); // Update this line
        verify(courseRepository, never()).save(any(Course.class));
        verify(purchaseRepository, never()).save(any(Purchase.class));
    }

}
