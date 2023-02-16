package org.example.entity;

import ch.qos.logback.core.util.FileSize;
import lombok.Data;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Data
public class SysFile {
    String icon;
    String filename;
    String abbrName;
    String type;
    String size;
    String modifyTime;
    boolean isDirectory;

    public static DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SysFile(File file){
        this.isDirectory=file.isDirectory();
        this.filename =file.getName();
        this.abbrName=file.getName();
        this.modifyTime=LocalDateTime.ofEpochSecond(file.lastModified()/1000,0, ZoneOffset.ofHours(8)).format(dateTimeFormatter);
        if(file.isDirectory()){
            type ="文件夹";
            this.size ="";
            this.icon="/img/dir.png";
        }else {
            this.size =new FileSize(file.length()).toString();
            int i = filename.lastIndexOf(".");
            if(i==-1){
                type ="文件";
            }else {
                type = filename.substring(i+1);
            }
            this.icon="/img/file.png";
        }
    }

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        long l = now.toEpochSecond(ZoneOffset.ofHours(8));
        System.out.println(System.currentTimeMillis());
        System.out.println(l);


    }
}
