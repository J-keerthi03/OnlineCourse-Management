package com.example.onlinecourse.service;

import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.model.Survey;
import com.example.onlinecourse.model.Purchase;
import com.example.onlinecourse.repository.CourseRepository;
import com.example.onlinecourse.repository.PurchaseRepository;
import com.example.onlinecourse.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    public List<Survey> generateSurveyReport() {
        try {
            List<Course> courses = courseRepository.findAll();
            return courses.stream().map(this::calculateProfitAndLoss).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate survey report: " + e.getMessage());
        }
    }

    private Survey calculateProfitAndLoss(Course course) {
        try {
            BigDecimal totalPurchases = purchaseRepository.findAll().stream()
                    .filter(purchase -> purchase.getCourse().equals(course))
                    .map(Purchase::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal costPrice = course.getPrice().multiply(BigDecimal.valueOf(course.getSlots()));
            BigDecimal profit = totalPurchases.subtract(costPrice);
            BigDecimal loss = (profit.compareTo(BigDecimal.ZERO) < 0) ? profit.negate() : BigDecimal.ZERO;

            Survey survey = new Survey();
            survey.setCourse(course);
            survey.setProfit(profit);
            survey.setLoss(loss);

            return surveyRepository.save(survey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate profit and loss for course ID "
                    + course.getId() + ": " + e.getMessage());
        }
    }
}