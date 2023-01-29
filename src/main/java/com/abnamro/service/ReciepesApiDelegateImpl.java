package com.abnamro.service;

import com.abnamro.api.ReciepesApiDelegate;
import com.abnamro.entity.Reciepes;
import com.abnamro.exception.ReciepeException;
import com.abnamro.model.Reciepe;
import com.abnamro.repository.ReciepeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReciepesApiDelegateImpl implements ReciepesApiDelegate {

    @Autowired
    private ReciepeRepository reciepeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<Reciepe> addReciepe(Reciepe reciepe) {
        try {
            return new ResponseEntity(reciepeRepository.save(modelMapper.map(reciepe, Reciepes.class)), HttpStatus.CREATED);
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
                    reciepeRepository.findReciepesByNameAndIngredientAndServingsAndAdditionalData(name, ingredient, servings, additionalContent, dishType);

            if (null == reciepes || reciepes.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<Reciepe> reciepesData = reciepes.stream().map(recipe -> modelMapper.map(recipe, Reciepe.class)).collect(Collectors.toList());
            return ResponseEntity.ok(reciepesData);
        }catch (DataAccessException dao) {
            log.error("Error while fetching the recipes."  , dao);
            throw new ReciepeException("Error while fetching the recipes.");
        }

    }



    @Override
    public ResponseEntity<Reciepe> updateReciepe(Reciepe reciepe) {

        try {
            Optional<Reciepes> reciepesOptional = reciepeRepository.findById(reciepe.getName());
            reciepesOptional.map(o -> {
                        TypeMap<Reciepe, Reciepes> mappings = modelMapper.addMappings(new PropertyMap<>() {
                            @Override
                            protected void configure() {
                                when(Conditions.isNull()).skip().setName(source.getName());
                                when(Conditions.isNull()).skip().setIngredient(source.getIngredient());
                                when(Conditions.isNull()).skip().setServings(source.getServings());
                                when(Conditions.isNull()).skip().setDishType(source.getDishType());
                                when(Conditions.isNull()).skip().setAdditionalText(source.getAdditionalText());
                            }
                        });
                        return modelMapper.map(reciepe, Reciepes.class);
                    })
                    .orElseThrow(() -> new ReciepeException("Recipe does not exist. Please add the recipe first."));
            return new ResponseEntity(reciepeRepository.save(reciepesOptional.get()), HttpStatus.CREATED);
        }catch (DataAccessException dao) {
            log.error("Error while updating the recipes {}."  , reciepe, dao);
            throw new ReciepeException("Error while updating existing recipes.");
        }
    }

    @Override
    public ResponseEntity<Void> deleteReciepes(String name, String apiKey) {

        try {
            Optional<Reciepes> reciepesOptional = reciepeRepository.findById(name);
            reciepesOptional.orElseThrow(() -> new ReciepeException("No Recipe found to delete."));

            reciepeRepository.deleteById(name);
            return new ResponseEntity(HttpStatus.OK);
        }catch (DataAccessException dao) {
            log.error("Error while deleti the recipe with name {}."  , name, dao);
            throw new ReciepeException("Error while deleting existing recipes.");
        }

    }

}
