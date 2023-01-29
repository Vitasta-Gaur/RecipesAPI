package com.abnamro.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reciepes")
public class Reciepes {

    @Id
    private String name;
    @Lob
    private String ingredient;
    private Integer servings;
    @Lob
    @Column(name = "additional_data")
    private String additionalText;
    @Column(name = "dish_type")
    private String dishType;
}
