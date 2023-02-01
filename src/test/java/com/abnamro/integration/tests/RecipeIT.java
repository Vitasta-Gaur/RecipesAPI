package com.abnamro.integration.tests;


import com.abnamro.model.Reciepe;
import com.abnamro.repository.ReciepeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@IntegrationTests
@Sql({"classpath:initscript.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeIT extends AbstractIntegrationTest {

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReciepeRepository reciepeRepository;


    @Test
    public void whenGetReciepes_thenStatus204() throws Exception {
        ResponseEntity<Reciepe> reciepe = this.restTemplate.getForEntity("http://localhost:" + port + "/reciepes?start=0&end=5",
                Reciepe.class);
        assertThat(reciepe).isNotNull();
        assertThat(reciepe.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void whenGetReciepes_thenStatus200() throws Exception {
        reciepeRepository.save(getReciepes());
        ResponseEntity<List> entity = this.restTemplate.getForEntity("http://localhost:" + port + "/reciepes?start=0&end=5",
                List.class);
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCodeValue()).isEqualTo(200);
        assertThat(entity.getBody().size()).isEqualTo(1);
    }

    @Test
    public void whenDeleteReciepes_thenStatus200() throws Exception {
        reciepeRepository.save(getReciepes());
        ResponseEntity entity = this.restTemplate.exchange("http://localhost:" + port + "/reciepes/Curry", HttpMethod.DELETE,null, RequestEntity.class);
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void whenAddReciepes_thenStatus201() throws Exception {
        HttpEntity<Reciepe> request = new HttpEntity<>(getRecipeRequest());
        ResponseEntity entity = this.restTemplate.exchange("http://localhost:" + port + "/reciepes", HttpMethod.POST,request, Reciepe.class);
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void whenUpdateReciepes_thenStatus201() throws Exception {
        reciepeRepository.save(getReciepes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>(getRecipeRequest(), headers);
        ResponseEntity entity = this.restTemplate.exchange("http://localhost:" + port + "/reciepes", HttpMethod.PUT,request, Reciepe.class);
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCodeValue()).isEqualTo(201);
    }

    @AfterEach
    public void tearDown() {
        reciepeRepository.deleteAll();
    }
}
