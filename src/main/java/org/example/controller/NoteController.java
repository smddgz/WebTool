package org.example.controller;

import org.example.entity.NoteInfo;
import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class NoteController {
    @Autowired
    FileService fileService;

    @Value("${filepath}/note")
    String root;

    @GetMapping("/note")
    public void home(HttpServletResponse response){
        try {
            response.sendRedirect("/note/home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/note/{filename}")
    public ModelAndView note(@PathVariable String filename){
        filename+=".txt";
        ModelAndView mv = new ModelAndView("note");
        fileService.createDirs(root);
        fileService.createFile(root,filename);
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(root + "/"+filename));
            mv.addObject("content", new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
            mv.addObject("content","SYSTEM ERROR");
        }
        return mv;
    }

    @ResponseBody
    @PostMapping("/note/modify")
    public void modify(@RequestBody NoteInfo noteInfo){
        String filename=noteInfo.getPathname().substring(5)+".txt";
        try {
            FileOutputStream os = new FileOutputStream(root + "/" + filename);
            os.write(noteInfo.getContent().getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
