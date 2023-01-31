package com.abnamro.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "reciepes")
public class Reciepes {

    @Id
    private String name;

    @Column(name = "ingredient", columnDefinition = "text")
    private String ingredient;
    private Integer servings;
    @Column(columnDefinition = "text")
    private String instructions;
    @Column(name = "dish_type")
    private String dishType;

    @Column(name = "cook_time")
    private String cookTime;

    @Column(name = "created", columnDefinition = "TIME WITH TIME ZONE")
    private OffsetDateTime created;

    @Column(name = "updated", columnDefinition = "TIME WITH TIME ZONE")
    private OffsetDateTime updated;
}
