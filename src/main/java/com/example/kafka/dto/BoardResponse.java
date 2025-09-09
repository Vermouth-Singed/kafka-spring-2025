package com.example.kafka.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardResponse {
    private String title;
}
