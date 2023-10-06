package org.example.controller;

import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RestController
public class ProxyController {
    @Value("${filepath}/note")
    String root;
    @Autowired
    FileService fileService;

    @GetMapping("/vmess")
    public String vmess(){
        if (!fileService.dirExist(root)) {
            fileService.createDirs(root);
        }
        if(!fileService.fileExist(root,"vmess.txt")){
            fileService.createFile(root,"vmess.txt");
        }
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(root, "vmess.txt"));
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/time")
    public String time(){
        return LocalDateTime.now().toString();
    }


}
