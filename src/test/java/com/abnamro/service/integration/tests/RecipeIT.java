package com.abnamro.service.integration.tests;


import com.abnamro.entity.Reciepes;
import com.abnamro.model.Reciepe;
import com.abnamro.repository.ReciepeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTests
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:application-it.properties")
public class RecipeIT {

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReciepeRepository reciepeRepository;


    @Test
    public void whenGetReciepes_thenStatus204() throws Exception {
        ResponseEntity<Reciepe> reciepe = this.restTemplate.getForEntity("http://localhost:" + port + "/reciepes",
                Reciepe.class);
        assertThat(reciepe).isNotNull();
        assertThat(reciepe.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void whenGetReciepes_thenStatus200() throws Exception {
        addReciepes();
        ResponseEntity<List> entity = this.restTemplate.getForEntity("http://localhost:" + port + "/reciepes",
                List.class);
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCodeValue()).isEqualTo(200);
        assertThat(entity.getBody().size()).isEqualTo(1);
    }

    @Test
    public void whenDeleteReciepes_thenStatus200() throws Exception {
        addReciepes();
        ResponseEntity entity = this.restTemplate.exchange("http://localhost:" + port + "/reciepes/Curry", HttpMethod.DELETE,null, RequestEntity.class);
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void whenAddReciepes_thenStatus201() throws Exception {
        Reciepe reciepes = new Reciepe();
        reciepes.setName("Curry");
        reciepes.setDishType("Vegetarian");
        HttpEntity<Reciepe> request = new HttpEntity<>(reciepes);
        ResponseEntity entity = this.restTemplate.exchange("http://localhost:" + port + "/reciepes", HttpMethod.POST,request, Reciepe.class);
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void whenUpdateReciepes_thenStatus201() throws Exception {
        addReciepes();

        Reciepe reciepes = new Reciepe();
        reciepes.setName("Curry");
        reciepes.setDishType("Vegetarian");
        HttpEntity<Reciepe> request = new HttpEntity<>(reciepes);
        ResponseEntity entity = this.restTemplate.exchange("http://localhost:" + port + "/reciepes", HttpMethod.PUT,request, Reciepe.class);
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCodeValue()).isEqualTo(201);
    }

    private void addReciepes(){
        Reciepes reciepes = new Reciepes();
        reciepes.setName("Curry");
        reciepes.setDishType("Vegetarian");
        reciepeRepository.saveAndFlush(reciepes);
    }

    @AfterEach
    public void tearDown(){
        reciepeRepository.deleteAll();
    }
}
