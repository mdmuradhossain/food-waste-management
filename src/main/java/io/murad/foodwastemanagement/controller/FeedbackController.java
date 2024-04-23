package io.murad.foodwastemanagement.controller;

import io.murad.foodwastemanagement.model.Feedback;
import io.murad.foodwastemanagement.model.Food;
import io.murad.foodwastemanagement.repository.FoodRepository;
import io.murad.foodwastemanagement.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FoodRepository foodRepository;

    @GetMapping("/list")
    public String getAllFeedback(Model model) {
        List<Feedback> feedbacks = feedbackService.getAllFeedback();
        model.addAttribute("feedbacks", feedbacks);
        return "feedback/all";
    }

//    @GetMapping("/{id}")
//    public String getFeedbackById(@PathVariable("id") Long id, Model model) {
//        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
//        feedback.ifPresent(value -> model.addAttribute("feedback", value));
//        return feedback.map(value -> "feedback/detail").orElse("redirect:/feedback/list");
//    }

//    @GetMapping("/new")
//    public String createFeedbackForm(Model model) {
//        model.addAttribute("feedback", new Feedback());
//        return "feedback/feedback_form";
//    }

    @PostMapping("/new")
    public String createFeedback(@ModelAttribute("feedback") Feedback feedback, @RequestParam("title") String title, @RequestParam("id") Long id) {
        Food food = foodRepository.findById(id).get();
        feedback.setFood(food);
        feedbackService.saveFeedback(feedback);
        return "redirect:/food/" + title;
    }

//    @GetMapping("/edit/{id}")
//    public String editFeedbackForm(@PathVariable Long id, Model model) {
//        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
//        feedback.ifPresent(value -> model.addAttribute("feedback", value));
//        return feedback.map(value -> "feedback/form").orElse("redirect:/feedback/list");
//    }
//
//    @PostMapping("/edit/{id}")
//    public String editFeedback(@PathVariable Long id, @ModelAttribute Feedback feedback) {
//        feedbackService.saveFeedback(feedback);
//        return "redirect:/feedback/list";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteFeedback(@PathVariable Long id) {
//        feedbackService.deleteFeedback(id);
//        return "redirect:/feedback/list";
//    }
}

