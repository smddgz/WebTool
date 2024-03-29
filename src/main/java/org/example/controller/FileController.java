package org.example.controller;

import org.example.entity.Result;
import org.example.entity.SysFile;
import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class FileController {
    @Value("${filepath}/file")
    String root;
    @Autowired
    FileService fileService;
    String logPath="/var/log/v2ray";
    @GetMapping("/v2ray/log")
    public void v2rayLog(HttpServletResponse response,@RequestHeader(required = false,value = "Authorization") String pwd) throws IOException {
        if(!"nmsl".equals(pwd)){
            return;
        }
        byte[] bytes = Files.readAllBytes(Paths.get(logPath+"/access.log"));
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"inline");
        response.getOutputStream().write(bytes);
    }

    @ResponseBody
    @GetMapping("/v2ray/clear")
    public String clearLog(){
        try {
            new FileOutputStream(logPath+"/access.log").write("".getBytes());
            new FileOutputStream(logPath+"/error.log").write("".getBytes());
            return "success";
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }

    @GetMapping("/file/**")
    public ModelAndView fileList(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String key = request.getParameter("key");
        key=key==null?"":key;
        int i = url.indexOf("/file");
        String dir = url.substring(i + 5);
        String decode = null;
        try {
            decode = URLDecoder.decode(dir, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<SysFile> files = fileService.files(root + decode,key);
        ModelAndView mv = new ModelAndView("file");
        mv.addObject("files", files);
        mv.addObject("key",key);
        return mv;
    }

    @GetMapping("/file/exist")
    public Result exist(String filepath,String filename){
        if (fileService.fileExist(filepath,filename)) {

        }
        return null;
    }

    @ResponseBody
    @PostMapping("/file/upload")
    public String String(MultipartFile file, String filepath) {
        try {
            StringBuilder sb = new StringBuilder();
            String[] split = filepath.split("/");
            for (int i = 2; i < split.length; i++) {
                sb.append(split[i]).append("/");
            }
            file.transferTo(new File(root + "/" + sb.toString() + file.getOriginalFilename()));
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }

    @GetMapping("/file/download")
    public void download(HttpServletResponse response, String filepath, String filename) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        byte[] bytes1 = filename.getBytes();
        String s = new String(bytes1, StandardCharsets.ISO_8859_1);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+s);
        byte[] bytes = new byte[1024 * 1024];
        ServletOutputStream os = null;
        FileInputStream fis = null;
        try {
            File file = fileService.getFileFromParam(filepath, filename);
            response.setHeader(HttpHeaders.CONTENT_LENGTH,file.length()+"");
            fis = new FileInputStream(file);
            os = response.getOutputStream();
            int l;
            while ((l = fis.read(bytes)) != -1) {
                os.write(bytes, 0, l);
            }
            os.flush();
        } catch (IOException ignored) {
        }
    }

    @ResponseBody
    @GetMapping("/file/delete")
    public Object delete(String filepath, String filename){
        File file = fileService.getFileFromParam(filepath, filename);
        if(!file.exists()){
            return new Result(-1,"文件不存在");
        }
        return file.delete() ? new Result(1,"删除成功") : new Result(-2,"删除失败");
    }

    @GetMapping("/file/createFile")
    public void createFile(){

    }

    @GetMapping("/file/createDir")
    public void createDir(){

    }
}
