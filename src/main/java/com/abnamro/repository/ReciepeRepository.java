package com.abnamro.repository;

import com.abnamro.entity.Reciepes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReciepeRepository extends JpaRepository<Reciepes,String> {

    @Query("SELECT c FROM Reciepes c WHERE (:name is null or c.name = :name) and (:ingredient is null"
            + " or c.ingredient like %:ingredient%) and (:servings is null or c.servings >= :servings) and (:additionalData is null or c.additionalText like %:additionalData%) " +
            "and (:dishType is null or c.dishType = :dishType)")
    List<Reciepes> findReciepesByNameAndIngredientAndServingsAndAdditionalData(
            @Param("name") String name, @Param("ingredient") String ingredient,
            @Param("servings") Integer servings , @Param("additionalData") String additionalData,
            @Param("dishType") String dishType);


}
