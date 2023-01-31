package com.abnamro.service;

import com.abnamro.entity.Reciepes;
import com.abnamro.model.Reciepe;

import java.time.OffsetDateTime;
import java.util.Arrays;

public abstract class AbstractRecipeTest {

    protected Reciepe getReciepe(){
        Reciepe reciepe = new Reciepe();
        reciepe.setName("curry");
        reciepe.setIngredient(Arrays.asList("Tomato,Curd"));
        reciepe.setInstructions("put curd in container,add water and tomato");
        reciepe.setServings(2);
        reciepe.setDishType(Reciepe.DishTypeEnum.ITALIAN);
        return reciepe;
    }

    protected Reciepes getReciepes(){
        Reciepes reciepe = new Reciepes();
        reciepe.setName("curry");
        reciepe.setIngredient("Tomato,Potato");
        reciepe.setInstructions("put curd in container,add water and tomato");
        reciepe.setServings(2);
        reciepe.setDishType("Vegetarian");
        reciepe.setCreated(OffsetDateTime.now());
        reciepe.setUpdated(OffsetDateTime.now());
        return reciepe;
    }
}
