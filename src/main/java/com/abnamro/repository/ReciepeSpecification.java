package com.abnamro.repository;

import com.abnamro.entity.Reciepes;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;

public final class ReciepeSpecification {


    private ReciepeSpecification() {
        //constructor
    }
    public static Specification<Reciepes> dishTypeEquals(String expression) {
        return (root, query, builder) -> builder.equal(root.get("dishType"), expression);
    }

    public static Specification<Reciepes> nameEquals(String expression) {
        return (root, query, builder) -> builder.equal(root.get("name"), expression);
    }

    public static Specification<Reciepes> servingsEquals(Integer expression) {
        return (root, query, builder) -> builder.equal(root.get("servings"), expression);
    }

    public static Specification<Reciepes> instructionsContains(String expression) {
        return (root, query, builder) -> builder.equal(root.get("instructions"), contains(expression));
    }

    public static Specification<Reciepes> ingredientsIncludes(String expression) {
        return (root, query, builder) -> builder.like(root.get("ingredient"), contains(expression));
    }

    public static Specification<Reciepes> ingredientsExcludes(String expression) {
        return (root, query, builder) -> builder.notLike(root.get("ingredient"), contains(expression));
    }

    private static String contains(String expression) {
        return MessageFormat.format("%{0}%", expression);
    }



}
