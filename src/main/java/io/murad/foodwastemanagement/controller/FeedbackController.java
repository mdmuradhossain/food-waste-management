package io.murad.foodwastemanagement.controller;

import io.murad.foodwastemanagement.model.Feedback;
import io.murad.foodwastemanagement.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/list")
    public String getAllFeedback(Model model) {
        List<Feedback> feedbackList = feedbackService.getAllFeedback();
        model.addAttribute("feedbacks", feedbackList);
        return "feedback/list";
    }

    @GetMapping("/{id}")
    public String getFeedbackById(@PathVariable("id") Long id, Model model) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
        feedback.ifPresent(value -> model.addAttribute("feedback", value));
        return feedback.map(value -> "feedback/detail").orElse("redirect:/feedback/list");
    }

//    @GetMapping("/new")
//    public String createFeedbackForm(Model model) {
//        model.addAttribute("feedback", new Feedback());
//        return "feedback/feedback_form";
//    }

    @PostMapping("/new")
    public String createFeedback(@ModelAttribute Feedback feedback) {
        feedbackService.saveFeedback(feedback);
        return "redirect:/feedback/list";
    }

    @GetMapping("/edit/{id}")
    public String editFeedbackForm(@PathVariable Long id, Model model) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
        feedback.ifPresent(value -> model.addAttribute("feedback", value));
        return feedback.map(value -> "feedback/form").orElse("redirect:/feedback/list");
    }

    @PostMapping("/edit/{id}")
    public String editFeedback(@PathVariable Long id, @ModelAttribute Feedback feedback) {
        feedbackService.saveFeedback(feedback);
        return "redirect:/feedback/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return "redirect:/feedback/list";
    }
}

