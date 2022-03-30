package com.example.sitelikechibis.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UpdatedUserpicDto {
    private List<ErrorInfo> errors = new ArrayList<>();
    private String userpicName;
}
