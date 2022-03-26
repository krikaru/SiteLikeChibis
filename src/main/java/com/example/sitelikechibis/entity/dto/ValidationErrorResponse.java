package com.example.sitelikechibis.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorResponse {
    private List<ErrorInfo> errors = new ArrayList<>();

}
