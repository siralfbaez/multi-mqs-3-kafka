package com.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class PublishController {

    @Autowired
    StreamBridge streamBridge;

    @GetMapping("/publish")
    public String showPublishForm() {
        return "publish";
    }

    @PostMapping("/publish")
    public String submitRegistration(@ModelAttribute FormData formData, Model model) {
        String destination = formData.getOrigin();
        model.addAttribute(
                "sendMessage",
                String.format("sent %s %s", formData.getFirstName(), formData.getLastName())
        );

        var customerName = String.format("%s %s", formData.getFirstName(), formData.getLastName());
        if (Objects.equals(formData.getOrigin(), "US")) {
            streamBridge.send(
                    "first-out-0", formData
            );
        } else {
            streamBridge.send(
                    "second-out-0", formData
            );
        }
        return "publish";
    }
}
