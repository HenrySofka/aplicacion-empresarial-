package co.com.sofka.questions.services;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.mappers.AnswerMapper;
import co.com.sofka.questions.mappers.QuestionMapper;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository repository;
    private AnswerMapper mapper = new AnswerMapper();

    public Flux<AnswerDTO> findAll(){
        return repository.findAll()
                .map(mapper.mapperAnswerToDTO());
    }

    public Mono<AnswerDTO> findById(String id){
        return repository.findById(id)
                .map(mapper.mapperAnswerToDTO());
    }
    public Flux<AnswerDTO> findByQuestionId(String questionId){
        return repository.findByQuestionId(questionId)
                .map(mapper.mapperAnswerToDTO());
    }

    public Mono<String> save(AnswerDTO answerDTO){
        return repository.save(mapper.mapperToAnswer(null).apply(answerDTO))
                .map(Answer::getId);
    }

    public Mono<String> update(AnswerDTO answerDTO){
        return repository.save(mapper.mapperToAnswer(answerDTO.getId()).apply(answerDTO))
                .map(answer -> "Updated question: " + answer.getId());
    }

    public Mono<Void> delete(String id){
        return repository.deleteById(id);
    }
    public Mono<Void> deleteByQuestionId(String questionId){
        return repository.deleteByQuestionId(questionId);
    }
}
