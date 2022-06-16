package co.com.sofka.questions.services;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.mappers.QuestionMapper;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository repository;
    private QuestionMapper mapper = new QuestionMapper();

    public Flux<QuestionDTO> findAll(){
        return repository.findAll()
                .map(mapper.mapperQuestionToDTO());
    }

    public Mono<QuestionDTO> findById(String id){
        return repository.findById(id)
                .map(mapper.mapperQuestionToDTO());
    }

    public Flux<QuestionDTO> findByUserId(String id){
        return repository.findByUserId(id)
                .map(mapper.mapperQuestionToDTO());
    }

    public Mono<String> save(QuestionDTO questionDTO){
        return repository.save(mapper.mapperToQuestion(null).apply(questionDTO))
                .map(Question::getId);
    }

    public Mono<String> update(QuestionDTO questionDTO){
        return repository.save(mapper.mapperToQuestion(questionDTO.getId()).apply(questionDTO))
                .map(question -> "Updated question: " + question.getId());
    }


    public Mono<String> setAnswer(QuestionDTO questionDTO, AnswerDTO answerDTO){
        questionDTO.addAnswer(answerDTO);
        System.out.println(questionDTO.getAnswers());
        return repository
                .save(mapper.mapperToQuestion(questionDTO.getId()).apply(questionDTO))
                .flatMap(question -> Mono.just("Add answer: " + answerDTO.getId()));
    }

    public Mono<String> addAnswer(AnswerDTO answerDTO){
        return findById(answerDTO.getQuestionId())
                .flatMap(questionDTO -> setAnswer(questionDTO, answerDTO));
    }

    public Mono<Void> delete(String id){
        return repository.deleteById(id);
    }

}
