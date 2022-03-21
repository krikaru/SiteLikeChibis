package com.example.sitelikechibis.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString(of = {"head", "text", "creationDate"})
@Entity
@Table
public class News implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "У новости должен быть заголовок!")
    @JsonView(Views.FullNews.class)
    private String head;
    @NotBlank(message = "У новости должен быть основной текст!")
    @JsonView(Views.FullNews.class)
    private String text;
    @JsonView(Views.FullNews.class)
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullNews.class)
    private User author;
}
