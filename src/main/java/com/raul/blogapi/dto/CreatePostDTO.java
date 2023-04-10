package com.raul.blogapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDTO {
    private String title;
    private String content;
    @JsonView(Views.Public.class)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<String> tags;
}
