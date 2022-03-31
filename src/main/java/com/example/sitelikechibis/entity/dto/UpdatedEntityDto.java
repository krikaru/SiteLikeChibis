package com.example.sitelikechibis.entity.dto;

import com.example.sitelikechibis.entity.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@JsonView(Views.FullNews.class)
public class UpdatedEntityDto<T> {

    private T entity;

    private List<ErrorInfo> errors;
}
