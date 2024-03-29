package co.com.sofka.questions.repositories;

import co.com.sofka.questions.collections.Answer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface AnswerRepository extends ReactiveCrudRepository<Answer, String> {
    Flux<Answer> findByQuestionId(String questionId);

    Mono<Void> deleteByQuestionId(String questionId);
}
