package com.example.onlinecourse;

import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CourseRepositoryTests {

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        // Clean up the repository before each test
        courseRepository.deleteAll();
    }

    @Test
    void testSaveCourse() {
        // Arrange
        Course course = new Course();
        course.setName("Java Basics");
        course.setPrice(BigDecimal.valueOf(100.00));
        course.setSlots(10);

        // Act
        Course savedCourse = courseRepository.save(course);

        // Assert
        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getId()).isNotNull();
        assertThat(savedCourse.getName()).isEqualTo("Java Basics");
        assertThat(savedCourse.getPrice()).isEqualTo(BigDecimal.valueOf(100.00));
        assertThat(savedCourse.getSlots()).isEqualTo(10);
    }

    @Test
    void testFindById() {
        // Arrange
        Course course = new Course();
        course.setName("Java Basics");
        course.setPrice(BigDecimal.valueOf(100.00));
        course.setSlots(10);

        Course savedCourse = courseRepository.save(course);

        // Act
        Course foundCourse = courseRepository.findById(savedCourse.getId()).orElse(null);

        // Assert
        assertThat(foundCourse).isNotNull();
        assertThat(foundCourse.getId()).isEqualTo(savedCourse.getId());
        assertThat(foundCourse.getName()).isEqualTo("Java Basics");
    }

    @Test
    void testUpdateCourse() {
        // Arrange
        Course course = new Course();
        course.setName("Java Basics");
        course.setPrice(BigDecimal.valueOf(100.00));
        course.setSlots(10);

        Course savedCourse = courseRepository.save(course);

        // Act
        savedCourse.setName("Advanced Java");
        savedCourse.setPrice(BigDecimal.valueOf(150.00));
        savedCourse.setSlots(20);

        Course updatedCourse = courseRepository.save(savedCourse);

        // Assert
        assertThat(updatedCourse.getName()).isEqualTo("Advanced Java");
        assertThat(updatedCourse.getPrice()).isEqualTo(BigDecimal.valueOf(150.00));
        assertThat(updatedCourse.getSlots()).isEqualTo(20);
    }

    @Test
    void testDeleteCourse() {
        // Arrange
        Course course = new Course();
        course.setName("Java Basics");
        course.setPrice(BigDecimal.valueOf(100.00));
        course.setSlots(10);

        Course savedCourse = courseRepository.save(course);

        // Act
        courseRepository.deleteById(savedCourse.getId());

        // Assert
        assertThat(courseRepository.findById(savedCourse.getId())).isEmpty();
    }
}
