package com.abnamro.service;

import com.abnamro.api.ReciepesApiDelegate;
import com.abnamro.entity.Reciepes;
import com.abnamro.exception.ReciepeException;
import com.abnamro.model.Reciepe;
import com.abnamro.repository.ReciepeRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReciepesApiDelegateImpl implements ReciepesApiDelegate {

    @Autowired
    private ReciepeRepository reciepeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<Reciepe> addReciepe(Reciepe reciepe) {
        return new ResponseEntity(reciepeRepository.save(modelMapper.map(reciepe, Reciepes.class)),HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Reciepe>> findReciepes(String dishType,
                                                      Integer servings,
                                                      String ingredient,
                                                      String additionalContent,
                                                      String name) {

        List<Reciepes> reciepes =
                reciepeRepository.findReciepesByNameAndIngredientAndServingsAndAdditionalData(name,ingredient,servings,additionalContent,dishType);

        if (null == reciepes || reciepes.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<Reciepe> reciepesData = reciepes.stream().map(recipe -> modelMapper.map(recipe, Reciepe.class)).collect(Collectors.toList());
        return ResponseEntity.ok(reciepesData);

    }



    @Override
    public ResponseEntity<Reciepe> updateReciepe(Reciepe reciepe) {
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
                    return modelMapper.map(reciepe,Reciepes.class);
                })
                .orElseThrow(() -> new ReciepeException("Recipe does not exist. Please add the recipe first."));
        return new ResponseEntity(reciepeRepository.save(reciepesOptional.get()),HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteReciepes(String name, String apiKey) {

        Optional<Reciepes> reciepesOptional = reciepeRepository.findById(name);
        reciepesOptional.orElseThrow(() -> new ReciepeException("No Recipe found to delete."));

        reciepeRepository.deleteById(name);
        return new ResponseEntity(HttpStatus.OK);

    }

}
