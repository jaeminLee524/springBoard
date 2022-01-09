package com.example.springboard.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDTO {
    private Long idx;
    private String title;
    private String content;
    private String writer;
    private int viewCnt;
    private String noticeYn;
    private String secretYn;
    private String deleteYn;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime deleteTime;
}
