package com.example.sitelikechibis.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString(of = {"head", "text", "creationDate"})
@Entity
@Table
@EqualsAndHashCode(of = {"id", "head"})
public class News implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.FullNews.class)
    private Long id;
    @NotBlank(message = "У новости должен быть заголовок!")
    @Length(message = "Длина закголовка должна быть не меньше 5 и не больше 100 символов.", max=100, min = 5)
    @JsonView(Views.FullNews.class)
    private String head;
    @NotBlank(message = "У новости должен быть основной текст!")
    @Length(message = "Длина основного текста должна быть не меньше 100 и не больше 10000 символов.", max=10000, min = 100)
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

    @ManyToMany
    @JoinTable(
            name = "news_likes",
            joinColumns = {@JoinColumn(name = "news_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @JsonView(Views.FullNews.class)
    private Set<User> likes = new HashSet<>();


    private static final long serialVersionUID = -6502367144872682228L;
}
