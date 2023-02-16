package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Result {
    int code;
    String msg;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
