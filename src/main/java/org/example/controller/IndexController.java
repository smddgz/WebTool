package org.example.controller;

import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Controller
public class IndexController {
    @Autowired
    FileService fileService;

    @Value("${filepath}/note")
    String root;

    @GetMapping("")
    public String index(){
        return "index";
    }

    public static void main(String[] args) {
        String s="ddd";

    }

}
