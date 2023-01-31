package com.abnamro.service.mapper;

import com.abnamro.entity.Reciepes;
import com.abnamro.model.Reciepe;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ReciepeMapperImpl implements RecipeMapper{
    @Override
    public Reciepes mapRecipeToRecipesDomain(Reciepe reciepe, boolean isUpdated) {
        Reciepes reciepes = new Reciepes();
        if (isUpdated) {
            reciepes.setUpdated(OffsetDateTime.now());
        } else {
            reciepes.setCreated(OffsetDateTime.now());
        }
        reciepes.setServings(reciepe.getServings());
        reciepes.setDishType(reciepe.getDishType().getValue());
        reciepes.setInstructions(reciepe.getInstructions());
        reciepes.setCookTime(reciepe.getCookTime());
        reciepes.setIngredient(String.join(",",reciepe.getIngredient()));
        reciepes.setName(reciepe.getName());
        return reciepes;
    }

    @Override
    public Reciepe mapRecipesDomainToRecipe(Reciepes recipe) {
        Reciepe rep = new Reciepe();
        rep.setName(recipe.getName());
        rep.setServings(recipe.getServings());
        rep.setInstructions(recipe.getInstructions());
        rep.setCreated(recipe.getCreated());
        rep.setUpdated(recipe.getUpdated());
        rep.setCookTime(recipe.getCookTime());
        rep.setDishType(Reciepe.DishTypeEnum.fromValue(recipe.getDishType()));
        List<String> list = Stream.of(recipe.getIngredient().split(",")).map(String::trim).collect(Collectors.toList());
        rep.setIngredient(list);
        return rep;
    }
}
