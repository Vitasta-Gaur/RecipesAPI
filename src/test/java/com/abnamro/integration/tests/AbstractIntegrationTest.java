package com.abnamro.integration.tests;

import com.abnamro.entity.Reciepes;
import com.abnamro.model.Reciepe;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.OffsetDateTime;
import java.util.List;

public class AbstractIntegrationTest {

    private static final PostgreSQLContainer POSTGRES_SQL_CONTAINER;

    static {
        POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13"));
        POSTGRES_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void overrideTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_SQL_CONTAINER::getPassword);
    }

    protected Reciepe getRecipeRequest() {
        Reciepe reciepes = new Reciepe();
        reciepes.setName("Curry");
        reciepes.setDishType(Reciepe.DishTypeEnum.ITALIAN);
        reciepes.setServings(2);
        reciepes.setIngredient(List.of("Tomato","Pesto"));
        reciepes.setCookTime("10M");
        reciepes.setCreated(OffsetDateTime.now());
        return reciepes;
    }

    protected Reciepes getReciepes(){
        Reciepes reciepes = new Reciepes();
        reciepes.setName("Curry");
        reciepes.setDishType(Reciepe.DishTypeEnum.ITALIAN.getValue());
        reciepes.setServings(2);
        reciepes.setIngredient("Pesto,Tomato");
        reciepes.setCookTime("10M");
        reciepes.setCreated(OffsetDateTime.now());
        return reciepes;
    }
}
