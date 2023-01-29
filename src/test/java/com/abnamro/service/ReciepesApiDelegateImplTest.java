package com.abnamro.service;

import com.abnamro.entity.Reciepes;
import com.abnamro.exception.ReciepeException;
import com.abnamro.model.Reciepe;
import com.abnamro.repository.ReciepeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@UnitTests
@ExtendWith(MockitoExtension.class)
class ReciepesApiDelegateImplTest {


    @Mock
    ModelMapper mapper;
    @Mock
    ReciepeRepository reciepeRepository;

    @InjectMocks
    ReciepesApiDelegateImpl reciepesApiDelegate;

    @Test
    void givenReciepe_whenAddReciepe_thenRecipeIsCreated() {
        Mockito.when(mapper.map(Mockito.any(Reciepe.class),Mockito.any())).thenReturn(getReciepes());
        Mockito.when(reciepeRepository.save(Mockito.any())).thenReturn(getReciepes());
        ResponseEntity<Reciepe> entity = reciepesApiDelegate.addReciepe(getReciepe());

        assertAll(
                () -> assertNotNull(entity),
                () -> assertEquals(201,entity.getStatusCodeValue())
        );
    }

    @Test
    void givenValidReciepe_whenFindReciepe_thenRecipesAreReturned() {
        //Mockito.when(mapper.map(Mockito.any(Reciepe.class),Mockito.any())).thenReturn(getReciepes());
        Mockito.when(reciepeRepository.findReciepesByNameAndIngredientAndServingsAndAdditionalData(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()))
                .thenReturn(Arrays.asList(getReciepes()));

        ResponseEntity<List<Reciepe>> reciepes = reciepesApiDelegate.findReciepes("vegetarian", 2, "tomato", "stove", "curry");

        assertAll(
                () -> assertNotNull(reciepes),
                () -> assertEquals(200,reciepes.getStatusCodeValue())
        );
    }

    @Test
    void givenInvalidReciepe_whenFindReciepe_thenNoContentIsReturned() {
        //Mockito.when(mapper.map(Mockito.any(Reciepe.class),Mockito.any())).thenReturn(getReciepes());
        Mockito.when(reciepeRepository.findReciepesByNameAndIngredientAndServingsAndAdditionalData(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()))
                .thenReturn(null);

        ResponseEntity<List<Reciepe>> reciepes = reciepesApiDelegate.findReciepes("vegetarian", 2, "tomato", "stove", "curry");

        assertAll(
                () -> assertNotNull(reciepes),
                () -> assertEquals(204,reciepes.getStatusCodeValue())
        );
    }
    @Test
    void givenReciepe_whenUpdateReciepe_thenRecipeIsUpdated() {
        Mockito.when(mapper.map(Mockito.any(Reciepe.class),Mockito.any())).thenReturn(getReciepes());
        Mockito.when(reciepeRepository.findById(Mockito.any())).thenReturn(Optional.of(getReciepes()));
        Mockito.when(reciepeRepository.save(Mockito.any())).thenReturn(getReciepes());
        ResponseEntity<Reciepe> entity = reciepesApiDelegate.updateReciepe(getReciepe());

        assertAll(
                () -> assertNotNull(entity),
                () -> assertEquals(201,entity.getStatusCodeValue())
        );
    }

    @Test
    void givenInvalidReciepe_whenUpdateReciepe_thenAPIErrorIsThrown() {
        Mockito.when(reciepeRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        ReciepeException exception = assertThrows(ReciepeException.class, () -> reciepesApiDelegate.updateReciepe(getReciepe()));

        assertEquals("Recipe does not exist. Please add the recipe first.", exception.getMessage());
    }

    @Test
    void givenReciepeName_whenDeleteReciepe_thenRecipesIsDeleted() {
        Mockito.doNothing().when(reciepeRepository).deleteById(Mockito.any());
        Mockito.when(reciepeRepository.findById(Mockito.any())).thenReturn(Optional.of(getReciepes()));
        ResponseEntity<Void> entity = reciepesApiDelegate.deleteReciepes("curry", null);

        assertAll(
                () -> assertNotNull(entity),
                () -> assertEquals(200,entity.getStatusCodeValue())
        );
    }

    @Test
    void givenInvalidReciepe_whenDeleteReciepe_thenAPIErrorIsThrown() {
        Mockito.when(reciepeRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        ReciepeException exception = assertThrows(ReciepeException.class, () -> reciepesApiDelegate.deleteReciepes("curry", null));
        assertEquals("No Recipe found to delete.", exception.getMessage());
    }
    private Reciepe getReciepe(){
        Reciepe reciepe = new Reciepe();
        reciepe.setName("curry");
        reciepe.setIngredient("Tomato,Curd");
        reciepe.setAdditionalText("put curd in container,add water and tomato");
        reciepe.setServings(2);
        reciepe.setDishType("vegetarian");
        return reciepe;
    }

    private Reciepes getReciepes(){
        Reciepes reciepe = new Reciepes();
        reciepe.setName("curry");
        reciepe.setIngredient("Tomato,Curd");
        reciepe.setAdditionalText("put curd in container,add water and tomato");
        reciepe.setServings(2);
        reciepe.setDishType("vegetarian");
        return reciepe;
    }
}