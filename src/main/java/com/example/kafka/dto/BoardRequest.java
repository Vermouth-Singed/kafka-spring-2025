package com.example.kafka.dto;

import lombok.Getter;

@Getter
public class BoardRequest {
    private Long id;
    private String title;
    private String content;
}
