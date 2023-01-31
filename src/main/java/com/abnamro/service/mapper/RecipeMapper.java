package com.abnamro.service.mapper;

import com.abnamro.entity.Reciepes;
import com.abnamro.model.Reciepe;

public interface RecipeMapper {

    Reciepes mapRecipeToRecipesDomain(Reciepe reciepe,boolean isUpdated);

    Reciepe mapRecipesDomainToRecipe(Reciepes reciepes);
}
