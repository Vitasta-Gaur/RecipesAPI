package com.abnamro.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reciepes")
public class Reciepes {

    private String ingredient;
    private Integer servings;

    @Column(name = "additional_data")
    private String additionalText;

    @Id
    private String name;

    @Column(name = "dish_type")
    private String dishType;
}
