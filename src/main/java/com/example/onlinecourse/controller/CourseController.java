package com.example.onlinecourse.controller;

import com.example.onlinecourse.Dto.CourseDTO;
import com.example.onlinecourse.Dto.ResponseDto;
import com.example.onlinecourse.exception.CourseNotFoundException;
import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto> addCourse(@RequestBody CourseDTO courseDTO) {
        Course course = courseService.addCourse(courseDTO);
        ResponseDto responseDTO = new ResponseDto();
        responseDTO.setStatus("SUCCESS");
        responseDTO.setMessage("Course added successfully");
        return ResponseEntity.ok(responseDTO);
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setPrice(course.getPrice());
        dto.setSlots(course.getSlots());
        return dto;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        try {
            return ResponseEntity.ok(courseService.updateCourse(id, courseDTO));
        } catch (CourseNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}