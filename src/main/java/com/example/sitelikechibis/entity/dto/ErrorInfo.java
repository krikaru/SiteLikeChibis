package com.example.sitelikechibis.entity.dto;

import lombok.Data;

@Data
public class ErrorInfo {
    private final String fieldName;
    private final String message;
}

