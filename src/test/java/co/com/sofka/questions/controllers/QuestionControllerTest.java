package co.com.sofka.questions.controllers;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.mappers.QuestionMapper;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.services.QuestionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {QuestionController.class, Question.class, QuestionDTO.class})
class QuestionControllerTest {

    @MockBean
    private QuestionService service;

    @Autowired
    private WebTestClient webTestClient;

    QuestionMapper mapper = new QuestionMapper();

    @Test
    void findByUserId() {
        //Arrange
        Flux<QuestionDTO> list = getQuestion().map(mapper.mapperQuestionToDTO());
        //Act
        Mockito.when(service.findByUserId("u1111")).thenReturn(list);
        //Assert
        webTestClient.get()
                .uri("/question/user/{userId}", "u1111")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(QuestionDTO.class)
                .value(answerDTOS -> {
                    //Answer1
                    assertEquals(list.blockFirst().getId(), answerDTOS.get(0).getId());
                    assertEquals(list.blockFirst().getCategory(), answerDTOS.get(0).getCategory());
                    assertEquals(list.blockFirst().getUserId(), answerDTOS.get(0).getUserId());
                    assertEquals(list.blockFirst().getAnswers(), answerDTOS.get(0).getAnswers());
                    assertEquals(list.blockFirst().getType(), answerDTOS.get(0).getType());
                    assertEquals(list.blockFirst().getQuestion(), answerDTOS.get(0).getQuestion());
                });
    }

    private Flux<Question> getQuestion(){
        Question question = new Question();
        question.setId("i1111");
        question.setUserId("u1111");
        question.setQuestion("Question 1");

        AnswerDTO answer1 = new AnswerDTO();
        answer1.setId("i1111");
        answer1.setQuestionId("q1111");
        answer1.setUserId("u1111");
        answer1.setAnswer("Answer 1");
        answer1.setPosition(1);
        List<AnswerDTO> list = new ArrayList<>();
        list.add(answer1);
        question.setAnswers(list);

        question.setType("Type 1");
        question.setCategory("Category 1");

        return Flux.just(question);
    }

}