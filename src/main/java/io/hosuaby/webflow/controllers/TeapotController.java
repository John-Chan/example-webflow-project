package io.hosuaby.webflow.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class TeapotController {

    @RequestMapping(method = RequestMethod.GET)
    public String getTeapotList(Model model) {
        model.addAttribute("teapots", new ArrayList<String>() {
            {
                add("nemezis");
                add("mouse");
                add("einshtein");
            }
        });

        return "index";
    }

}
