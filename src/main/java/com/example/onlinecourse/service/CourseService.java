package com.example.onlinecourse.service;

import com.example.onlinecourse.Dto.CourseDTO;
import com.example.onlinecourse.exception.CourseNotFoundException;
import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.repository.CourseRepository;
import com.example.onlinecourse.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    public Course addCourse(CourseDTO courseDTO) {
        try {
            if (courseDTO.getName() == null || courseDTO.getPrice() == null) {
                throw new IllegalArgumentException("Name and price must not be null.");
            }

            Course course = new Course();
            course.setName(courseDTO.getName());
            course.setPrice(courseDTO.getPrice());
            course.setSlots(courseDTO.getSlots()); // Slots can be null

            return courseRepository.save(course);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid course data: " + e.getMessage(), e);
        }
    }

    public Course getCourseById(Long id) {
        try {
            Optional<Course> courseOpt = courseRepository.findById(id);
            if (courseOpt.isPresent()) {
                return courseOpt.get();
            } else {
                throw new RuntimeException("Course not found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get course by ID: " + e.getMessage());
        }
    }

    public List<Course> getAllCourses() {
        try {
            return courseRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get all courses: " + e.getMessage());
        }
    }

    public Course updateCourse(Long id, CourseDTO courseDTO) {
        try {
            Optional<Course> courseOpt = courseRepository.findById(id);
            if (courseOpt.isPresent()) {
                Course course = courseOpt.get();
                course.setName(courseDTO.getName());
                course.setPrice(courseDTO.getPrice());
                course.setSlots(courseDTO.getSlots());
                return courseRepository.save(course);
            } else {
                throw new CourseNotFoundException("Course not found with ID: " + id);
            }
        } catch (CourseNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update course: " + e.getMessage());
        }
    }

    public void deleteCourse(Long id) {
        try {
            if (courseRepository.existsById(id)) {
                // Fetch the course along with its associated surveys
                Course course = courseRepository.findById(id).orElseThrow(() ->
                        new CourseNotFoundException("Course not found with ID: " + id));

                // Delete all surveys associated with this course
                surveyRepository.deleteByCourse(course);

                // Now delete the course
                courseRepository.deleteById(id);
            } else {
                throw new CourseNotFoundException("Course not found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete course: " + e.getMessage());
        }
    }

}
