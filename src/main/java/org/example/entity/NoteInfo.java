package org.example.entity;

import lombok.Data;

@Data
public class NoteInfo {
    String pathname;
    String content="";

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
