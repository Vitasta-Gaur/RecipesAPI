package com.abnamro.service;

import com.abnamro.api.ReciepesApiDelegate;
import com.abnamro.entity.Reciepes;
import com.abnamro.exception.ReciepeException;
import com.abnamro.model.Reciepe;
import com.abnamro.repository.ReciepeRepository;
import com.abnamro.service.mapper.RecipeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReciepesApiDelegateImpl implements ReciepesApiDelegate {

    @Autowired
    private ReciepeRepository reciepeRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Transactional
    @Override
    public ResponseEntity<Reciepe> addReciepe(Reciepe reciepe) {
        try {
            Reciepes reciepes = recipeMapper.mapRecipeToRecipesDomain(reciepe,false);
            reciepeRepository.save(reciepes);
            return new ResponseEntity(reciepe, HttpStatus.CREATED);
        } catch (DataAccessException dao) {
            log.error("Error while saving recipe {}." , reciepe , dao);
            throw new ReciepeException("Error while saving recipe.");
        }
    }

    @Override
    public ResponseEntity<List<Reciepe>> findReciepes(String dishType,
                                                      Integer servings,
                                                      String ingredient,
                                                      String additionalContent,
                                                      String name) {

        try {
            List<Reciepes> reciepes =
                    reciepeRepository.findReciepesByNameAndIngredientAndServingsAndAdditionalData(name, servings, additionalContent, dishType,ingredient);

            if (null == reciepes || reciepes.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<Reciepe> reciepesData = reciepes.stream().map(recipe -> recipeMapper.mapRecipesDomainToRecipe(recipe)).collect(Collectors.toList());
            return ResponseEntity.ok(reciepesData);
        }catch (DataAccessException dao) {
            log.error("Error while fetching the recipes."  , dao);
            throw new ReciepeException("Error while fetching the recipes.");
        }

    }



    @Transactional
    @Override
    public ResponseEntity<Reciepe> updateReciepe(Reciepe reciepe) {

        try {
            Optional<Reciepes> reciepesOptional = reciepeRepository.findById(reciepe.getName());
            reciepesOptional.orElseThrow( () ->  new ReciepeException("Recipe doesnot exist."));

            Reciepes reciepes = reciepeRepository.save(recipeMapper.mapRecipeToRecipesDomain(reciepe, true));
            return new ResponseEntity(recipeMapper.mapRecipesDomainToRecipe(reciepes), HttpStatus.CREATED);
        }catch (DataAccessException dao) {
            log.error("Error while updating the recipes {}."  , reciepe, dao);
            throw new ReciepeException("Error while updating existing recipes.");
        }
    }

    @Override
    public ResponseEntity<Void> deleteReciepes(String name) {

        try {
            Optional<Reciepes> reciepesOptional = reciepeRepository.findById(name);
            reciepesOptional.orElseThrow(() -> new ReciepeException("No Recipe found to delete."));
            reciepeRepository.delete(reciepesOptional.get());
            return new ResponseEntity(HttpStatus.OK);
        }catch (DataAccessException dao) {
            log.error("Error while deleting the recipe with name {}."  , name, dao);
            throw new ReciepeException("Error while deleting existing recipes.");
        }

    }

}
