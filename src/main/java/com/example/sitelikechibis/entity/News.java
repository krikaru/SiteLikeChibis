package com.example.sitelikechibis.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String head;
    private String text;
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}
