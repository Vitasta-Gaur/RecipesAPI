package com.abnamro.repository;

import com.abnamro.entity.Reciepes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReciepeRepository extends JpaRepository<Reciepes,String> {

    @Query("SELECT c FROM Reciepes c WHERE (:name is null or c.name = :name) "
            + " and (:servings is null or c.servings >= :servings) and (:instructions is null or c.instructions like %:instructions%) " +
            "and (:dishType is null or c.dishType = :dishType) and (:ingredient is null or c.ingredient like %:ingredient%) ")
    List<Reciepes> findReciepesByNameAndIngredientAndServingsAndAdditionalData(
            @Param("name") String name,
            @Param("servings") Integer servings , @Param("instructions") String instructions,
            @Param("dishType") String dishType ,
            @Param("ingredient") String ingredient);
}
