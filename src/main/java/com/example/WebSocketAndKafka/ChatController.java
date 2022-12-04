package com.example.WebSocketAndKafka;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@Log4j2
public class ChatController {


    @GetMapping("/chat")
    public String chatGET(HttpSession session, Model model){
        model.addAttribute("name", UUID.randomUUID().toString());
        return "chat";
    }

    @GetMapping("/")
    public String index(){
        return "error";
    }

    @GetMapping("/{id}")
    public String chattingRoom(@PathVariable String id, HttpSession session, Model model){
        if(id.equals("loose")){
            model.addAttribute("name", "loose");
        }else if(id.equals("guest")){
            model.addAttribute("name", "guest");
        }else if(id.equals("master")){
            model.addAttribute("name", "master");
        }else{
            return "error";
        }
        return "chattingRoom";
    }
}