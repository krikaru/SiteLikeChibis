package com.example.sitelikechibis.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
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
    @JsonView(Views.FullProfile.class)
    private String head;
    @JsonView(Views.FullProfile.class)
    private String text;
    @JsonView(Views.FullProfile.class)
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullProfile.class)
    private User author;
}
