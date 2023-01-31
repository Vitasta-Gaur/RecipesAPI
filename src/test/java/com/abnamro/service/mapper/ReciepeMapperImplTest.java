package com.abnamro.service.mapper;

import com.abnamro.entity.Reciepes;
import com.abnamro.model.Reciepe;
import com.abnamro.service.AbstractRecipeTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReciepeMapperImplTest  extends AbstractRecipeTest {

    @InjectMocks
    ReciepeMapperImpl reciepeMapper;

    @Test
    void mapRecipeToRecipesDomain() {
        Reciepes reciepe = reciepeMapper.mapRecipeToRecipesDomain(getReciepe(),true);
        assertNotNull(reciepe);
    }

    @Test
    void mapRecipesDomainToRecipe() {
        Reciepe reciepe = reciepeMapper.mapRecipesDomainToRecipe(getReciepes());
        assertNotNull(reciepe);
    }
}