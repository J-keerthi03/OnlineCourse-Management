package com.example.onlinecourse.service;

import com.example.onlinecourse.Dto.PurchaseDTO;
import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.model.Purchase;
import com.example.onlinecourse.repository.CourseRepository;
import com.example.onlinecourse.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public Purchase purchaseProduct(PurchaseDTO purchaseDTO) {
        try {
            Optional<Course> optionalCourse = courseRepository.findById(purchaseDTO.getCourseId());
            if (optionalCourse.isPresent()) {
                Course course = optionalCourse.get();
                if (course.getSlots() >= purchaseDTO.getSlotsPurchased()) {
                    BigDecimal totalAmount = course.getPrice().multiply(BigDecimal.valueOf(purchaseDTO.getSlotsPurchased()));

                    course.setSlots(course.getSlots() - purchaseDTO.getSlotsPurchased());
                    courseRepository.save(course);

                    Purchase purchase = new Purchase();
                    purchase.setCourse(course);
                    purchase.setSlotsPurchased(purchaseDTO.getSlotsPurchased());
                    purchase.setTotalAmount(totalAmount);
                    purchase.setPurchaseDate(java.time.LocalDateTime.now());

                    return purchaseRepository.save(purchase);
                } else {
                    throw new RuntimeException("Not enough slots available");
                }
            } else {
                throw new RuntimeException("Course not found"); // Update this line
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid purchase data.");
        }
    }
}