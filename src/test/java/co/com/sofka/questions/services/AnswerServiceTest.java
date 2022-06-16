package co.com.sofka.questions.services;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.mappers.AnswerMapper;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Answer.class, AnswerDTO.class, AnswerService.class})
class AnswerServiceTest {

    @MockBean
    private AnswerRepository repository;

    @Autowired
    private AnswerService service;

    AnswerMapper mapper = new AnswerMapper();

    @Test
    @DisplayName("Buscar todos los answers")
    void findAllTest() {
        //Arrange
        Flux<Answer> list = getAnswerList();
        //Act
        when(repository.findAll()).thenReturn(list);
        //Assert
        //Answer1
        Flux<AnswerDTO> result = service.findAll();
        assertEquals(list.blockFirst().getId(), result.blockFirst().getId());
        assertEquals(list.blockFirst().getQuestionId(), result.blockFirst().getQuestionId());
        assertEquals(list.blockFirst().getUserId(), result.blockFirst().getUserId());
        assertEquals(list.blockFirst().getAnswer(), result.blockFirst().getAnswer());
        assertEquals(list.blockFirst().getPosition(), result.blockFirst().getPosition());
        //Answer2
        assertEquals(list.blockLast().getId(), result.blockLast().getId());
        assertEquals(list.blockLast().getQuestionId(), result.blockLast().getQuestionId());
        assertEquals(list.blockLast().getUserId(), result.blockLast().getUserId());
        assertEquals(list.blockLast().getAnswer(), result.blockLast().getAnswer());
        assertEquals(list.blockLast().getPosition(), result.blockLast().getPosition());
    }

    @Test
    @DisplayName("Buscar answer by id")
    void findById() {
        //Arrange
        Mono<Answer> answer = getAnswer();
        //Act
        when(repository.findById("i1111")).thenReturn(answer);
        //Assert
        Mono<AnswerDTO> result = service.findById("i1111");
        assertEquals(answer.block().getId(), result.block().getId());
        assertEquals(answer.block().getQuestionId(), result.block().getQuestionId());
        assertEquals(answer.block().getUserId(), result.block().getUserId());
        assertEquals(answer.block().getAnswer(), result.block().getAnswer());
        assertEquals(answer.block().getPosition(), result.block().getPosition());
    }

    @Test
    @DisplayName("Buscar answer by QuestionId")
    void findByQuestionIdTest() {
        //Arrange
        Flux<Answer> list = getAnswerList();
        //Act
        when(repository.findByQuestionId("q1111")).thenReturn(list);
        //Assert
        //Answer1
        Flux<AnswerDTO> result = service.findByQuestionId("q1111");
        assertEquals(list.blockFirst().getId(), result.blockFirst().getId());
        assertEquals(list.blockFirst().getQuestionId(), result.blockFirst().getQuestionId());
        assertEquals(list.blockFirst().getUserId(), result.blockFirst().getUserId());
        assertEquals(list.blockFirst().getAnswer(), result.blockFirst().getAnswer());
        assertEquals(list.blockFirst().getPosition(), result.blockFirst().getPosition());

    }

    public Mono<Answer> getAnswer(){
        Answer answer1 = new Answer();
        answer1.setId("i1111");
        answer1.setQuestionId("q1111");
        answer1.setUserId("u1111");
        answer1.setAnswer("Answer 1");
        answer1.setPosition(1);

        return Mono.just(answer1);
    }

    public Flux<Answer> getAnswerList(){
        Answer answer1 = new Answer();
        answer1.setId("i1111");
        answer1.setQuestionId("q1111");
        answer1.setUserId("u1111");
        answer1.setAnswer("Answer 1");
        answer1.setPosition(1);

        Answer answer2 = new Answer();
        answer2.setId("i2222");
        answer2.setQuestionId("q2222");
        answer2.setUserId("u2222");
        answer2.setAnswer("Answer 2");
        answer2.setPosition(2);

        return Flux.just(answer1, answer2);

    }
}