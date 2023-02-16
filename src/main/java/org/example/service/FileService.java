package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.PageInfo;
import org.example.entity.SysFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class FileService {
    @Value("${filepath}/file")
    String root;

    public boolean dirExist(String filepath) {
        File file = new File(filepath);
        return file.isDirectory() && file.exists();
    }

    public boolean createDirs(String filepath) {
        File file = new File(filepath);
        return file.mkdirs();
    }

    public boolean fileExist(String filepath, String filename) {
        File file = new File(filepath + "/" + filename);
        return file.isFile() && file.exists();
    }

    public boolean createFile(String filepath, String filename) {
        try {
            return new File(filepath + "/" + filename).createNewFile();
        } catch (IOException e) {
//            log.error("filepath:{},filename:{},msg:{}", filepath, filename, e.getMessage());
            return false;
        }
    }

    public List<SysFile> files(String filepath,String key){
        createDirs(filepath);
        return Arrays.stream(Objects.requireNonNull(new File(filepath).listFiles())).filter(e->e.getName().contains(key)).map(SysFile::new).collect(Collectors.toList());
    }

    public File getFileFromParam(String filepath,String filename){
        StringBuilder sb=new StringBuilder();
        Arrays.stream(filepath.split("/")).filter(e->!"".equals(e)).skip(1).forEach(e->sb.append(e).append("/"));
        return new File(root + "/" + sb.toString() + "/" + filename);
    }

    public static void main(String[] args) {
        String s="";
        System.out.println("dsjflsd".contains(""));
    }



//    public PageInfo files(String filepath, Integer pageNum, Integer pageSize, String word) {
//        File dir = new File(filepath);
//        File[] files = dir.listFiles();
//        assert files != null;
//        int skipCount = (pageNum - 1) * pageSize;
//        List<SysFile> list = Stream.of(files)
//                .filter(e -> e.getName().contains(word))
//                .skip(skipCount)
//                .limit(pageSize)
//                .map(SysFile::new)
//                .collect(Collectors.toList());
//
//        int i = list.size() % pageSize;
//        int j = list.size() / pageSize;
//        PageInfo pageInfo = new PageInfo();
//        pageInfo.setPageNum(pageNum);
//        pageInfo.setPageSize(pageSize);
//        pageInfo.setTotal(list.size());
//        pageInfo.setPages(i==0?j:++j);
//        pageInfo.setFiles(list);
//        return pageInfo;
//    }
}
