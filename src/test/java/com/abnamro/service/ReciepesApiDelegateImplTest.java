package com.abnamro.service;

import com.abnamro.exception.ReciepeException;
import com.abnamro.model.Reciepe;
import com.abnamro.repository.ReciepeRepository;
import com.abnamro.service.mapper.RecipeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@UnitTests
@ExtendWith(MockitoExtension.class)
class ReciepesApiDelegateImplTest extends AbstractRecipeTest {


    @Mock
    RecipeMapper mapper;
    @Mock
    ReciepeRepository reciepeRepository;

    @InjectMocks
    ReciepesApiDelegateImpl reciepesApiDelegate;

    @Test
    void givenReciepe_whenAddReciepe_thenRecipeIsCreated() {
        Mockito.when(mapper.mapRecipeToRecipesDomain(Mockito.any(Reciepe.class),Mockito.anyBoolean())).thenReturn(getReciepes());
        Mockito.when(reciepeRepository.save(Mockito.any())).thenReturn(getReciepes());
        ResponseEntity<Reciepe> entity = reciepesApiDelegate.addReciepe(getReciepe());

        assertAll(
                () -> assertNotNull(entity),
                () -> assertEquals(201,entity.getStatusCodeValue())
        );
    }

    @Test
    void givenInvalidReciepe_whenAddReciepe_thenAPIErrorIsThrown() {
        Mockito.when(mapper.mapRecipeToRecipesDomain(Mockito.any(Reciepe.class),Mockito.anyBoolean())).thenReturn(getReciepes());
        Mockito.when(reciepeRepository.save(Mockito.any())).thenThrow(new JpaSystemException(new RuntimeException()));

        ReciepeException exception = assertThrows(ReciepeException.class, () -> reciepesApiDelegate.addReciepe(getReciepe()));

        assertEquals("Error while saving recipe.", exception.getMessage());
    }


    @Test
    void givenValidReciepe_whenFindReciepe_thenRecipesAreReturned() {
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
        Mockito.when(mapper.mapRecipeToRecipesDomain(Mockito.any(Reciepe.class),Mockito.anyBoolean())).thenReturn(getReciepes());
        Mockito.when(reciepeRepository.findById(Mockito.any())).thenReturn(Optional.of(getReciepes()));
        Mockito.when(reciepeRepository.save(Mockito.any())).thenReturn(getReciepes());
        ResponseEntity<Reciepe> entity = reciepesApiDelegate.updateReciepe(getReciepe());

        assertAll(
                () -> assertNotNull(entity),
                () -> assertEquals(201,entity.getStatusCodeValue())
        );
    }

    @Test
    void givenInvalidSpecReciepe_whenUpdateReciepe_thenAPIErrorIsThrown() {
        Mockito.when(reciepeRepository.findById(Mockito.any())).thenThrow(new JpaSystemException(new RuntimeException()));

        ReciepeException exception = assertThrows(ReciepeException.class, () -> reciepesApiDelegate.updateReciepe(getReciepe()));

        assertEquals("Error while updating existing recipes.", exception.getMessage());
    }


    @Test
    void givenInvalidReciepe_whenUpdateReciepe_thenAPIErrorIsThrown() {
        Mockito.when(reciepeRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        ReciepeException exception = assertThrows(ReciepeException.class, () -> reciepesApiDelegate.updateReciepe(getReciepe()));

        assertEquals("Recipe doesnot exist.", exception.getMessage());
    }

    @Test
    void givenReciepeName_whenDeleteReciepe_thenRecipesIsDeleted() {
        Mockito.doNothing().when(reciepeRepository).delete(Mockito.any());
        Mockito.when(reciepeRepository.findById(Mockito.any())).thenReturn(Optional.of(getReciepes()));
        ResponseEntity<Void> entity = reciepesApiDelegate.deleteReciepes("curry");

        assertAll(
                () -> assertNotNull(entity),
                () -> assertEquals(200,entity.getStatusCodeValue())
        );
    }

    @Test
    void givenInvalidReciepe_whenDeleteReciepe_thenAPIErrorIsThrown() {
        Mockito.when(reciepeRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        ReciepeException exception = assertThrows(ReciepeException.class, () -> reciepesApiDelegate.deleteReciepes("curry"));
        assertEquals("No Recipe found to delete.", exception.getMessage());
    }

    @Test
    void givenDataAccessException_whenDeleteReciepe_thenAPIErrorIsThrown() {
        Mockito.when(reciepeRepository.findById(Mockito.any())).thenThrow(new JpaSystemException(new RuntimeException()));

        ReciepeException exception = assertThrows(ReciepeException.class, () -> reciepesApiDelegate.deleteReciepes("curry"));

        assertEquals("Error while deleting existing recipes.", exception.getMessage());
    }
}