package org.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageInfo {
    Integer pageNum;
    Integer pageSize;
    Integer total;
    Integer pages;
    Integer next;
    Integer previous;
    List<SysFile> files;
}
