package co.com.sofka.questions.controllers;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.services.AnswerService;
import co.com.sofka.questions.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    AnswerService service;

    @GetMapping()
    public ResponseEntity<Flux<AnswerDTO>> findAll(){
        Flux<AnswerDTO> list = service.findAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Mono<AnswerDTO>> findById(@PathVariable("id") String id){
        return new ResponseEntity(service.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Mono<String>> save(@RequestBody AnswerDTO answerDTO){
        return new ResponseEntity(service.save(answerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Mono<String>> update(@RequestBody AnswerDTO answerDTO){
        return new ResponseEntity(service.update(answerDTO), HttpStatus.OK);
    }

    @DeleteMapping({"delete/{id}"})
    public ResponseEntity<Mono<Void>> delete(@PathVariable("id") String id){
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }
}
