package com.example.sitelikechibis.entity;

import com.fasterxml.jackson.annotation.*;
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
    @JsonView(Views.FullNews.class)
    private Long id;
    @NotBlank(message = "У новости должен быть заголовок!")
    @JsonView(Views.FullNews.class)
    private String head;
    @NotBlank(message = "У новости должен быть основной текст!")
    @JsonView(Views.FullNews.class)
    private String text;
    @Column(updatable = false)
    @JsonView(Views.FullNews.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullNews.class)
    private User author;
}
