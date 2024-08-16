package com.example.onlinecourse.repository;
import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    void deleteByCourse(Course course);
}