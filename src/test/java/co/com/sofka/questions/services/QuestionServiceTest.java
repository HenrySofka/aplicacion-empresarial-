package co.com.sofka.questions.services;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.mappers.QuestionMapper;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Question.class, QuestionDTO.class, QuestionService.class})
class QuestionServiceTest {
    @MockBean
    private QuestionRepository repository;
    @Autowired
    private QuestionService service;

    private QuestionMapper mapper = new QuestionMapper();

    @Test
    @DisplayName("Buscar por UserID")
    void findByUserIdTest() {
        Flux<Question> list = getQuestion();

        when(repository.findByUserId("u1111")).thenReturn(list);

        //Assert
        //Question1
        Flux<QuestionDTO> result = service.findByUserId("u1111");
        assertEquals(list.blockFirst().getId(), result.blockFirst().getId());
        assertEquals(list.blockFirst().getQuestion(), result.blockFirst().getQuestion());
        assertEquals(list.blockFirst().getUserId(), result.blockFirst().getUserId());
        assertEquals(list.blockFirst().getAnswers(), result.blockFirst().getAnswers());
        assertEquals(list.blockFirst().getCategory(), result.blockFirst().getCategory());
        assertEquals(list.blockFirst().getType(), result.blockFirst().getType());


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