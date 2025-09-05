package com.example.demo.Controllers;

import com.example.demo.Components.DomainManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/app")
public class AppController {

    private final DomainManager data;

    AppController(DomainManager _data) {
        this.data = _data;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("data", data);

        return "index";
    }

}
