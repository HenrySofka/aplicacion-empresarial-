package co.com.sofka.questions.controllers;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.mappers.AnswerMapper;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.services.AnswerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Answer.class, AnswerDTO.class, AnswerController.class})
class AnswerControllerTest {

    @MockBean
    private AnswerService service;

    @Autowired
    private WebTestClient webTestClient;

    AnswerMapper mapper = new AnswerMapper();

    @Test
    @DisplayName("Conexion a /answer")
    void findAllTest() {
        //Arrange
        Flux<AnswerDTO> list = getAnswerList().map(mapper.mapperAnswerToDTO());
        //Act
        when(service.findAll()).thenReturn(list);
        //Assert
        webTestClient.get()
                .uri("/answer")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AnswerDTO.class)
                .value(answerDTOS -> {
                    //Answer1
                    assertEquals(list.blockFirst().getId(), answerDTOS.get(0).getId());
                    assertEquals(list.blockFirst().getQuestionId(), answerDTOS.get(0).getQuestionId());
                    assertEquals(list.blockFirst().getUserId(), answerDTOS.get(0).getUserId());
                    assertEquals(list.blockFirst().getAnswer(), answerDTOS.get(0).getAnswer());
                    assertEquals(list.blockFirst().getPosition(), answerDTOS.get(0).getPosition());
                    //Answer2
                    assertEquals(list.blockLast().getId(), answerDTOS.get(1).getId());
                    assertEquals(list.blockLast().getQuestionId(), answerDTOS.get(1).getQuestionId());
                    assertEquals(list.blockLast().getUserId(), answerDTOS.get(1).getUserId());
                    assertEquals(list.blockLast().getAnswer(), answerDTOS.get(1).getAnswer());
                    assertEquals(list.blockLast().getPosition(), answerDTOS.get(1).getPosition());
                });
    }

    @Test
    @DisplayName("Conexion a /answer/{id}")
    void findById() {
        //Arrange
        Mono<AnswerDTO> answer = getAnswer().map(mapper.mapperAnswerToDTO());
        //Act
        when(service.findById("1111")).thenReturn(answer);
        //Assert
        webTestClient.get()
                .uri("/answer/{id}", "1111")
                .exchange()
                .expectStatus().isOk()
                .expectBody(AnswerDTO.class)
                .value(answerDTOS -> {
                    //Answer
                    assertEquals(answer.block().getId(), answerDTOS.getId());
                    assertEquals(answer.block().getQuestionId(), answerDTOS.getQuestionId());
                    assertEquals(answer.block().getUserId(), answerDTOS.getUserId());
                    assertEquals(answer.block().getAnswer(), answerDTOS.getAnswer());
                    assertEquals(answer.block().getPosition(), answerDTOS.getPosition());
                });
    }

    @Test
    void findByQuestionId() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteByQuestionId() {
    }

    public Mono<Answer> getAnswer(){
        Answer answer1 = new Answer();
        answer1.setId("1111");
        answer1.setQuestionId("1111");
        answer1.setUserId("1111");
        answer1.setAnswer("Answer 1");
        answer1.setPosition(1);

        return Mono.just(answer1);
    }

    public Flux<Answer> getAnswerList(){
        Answer answer1 = new Answer();
        answer1.setId("1111");
        answer1.setQuestionId("1111");
        answer1.setUserId("1111");
        answer1.setAnswer("Answer 1");
        answer1.setPosition(1);

        Answer answer2 = new Answer();
        answer2.setId("2222");
        answer2.setQuestionId("2222");
        answer2.setUserId("2222");
        answer2.setAnswer("Answer 2");
        answer2.setPosition(2);

        return Flux.just(answer1, answer2);

    }
}