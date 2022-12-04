package com.example.WebSocketAndKafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class TestController {
    @Autowired
    Producer producer;

    @GetMapping("/test")
    public void chatGET(HttpSession session, Model model){
        producer.sendMessage("kafkaTest","오늘은 Kafka 샘플 코드를 작성해봅시다.");
    }
}
