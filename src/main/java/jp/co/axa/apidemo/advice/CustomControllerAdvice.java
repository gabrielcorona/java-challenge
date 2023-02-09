package jp.co.axa.apidemo.advice;

import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jp.co.axa.apidemo.entities.CustomMessage;
import jp.co.axa.apidemo.exception.BadRequestException;

@ControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<CustomMessage> handleNotFound(NoSuchElementException noSuchElementException){
        return new ResponseEntity<CustomMessage>(new CustomMessage(HttpStatus.NOT_FOUND.value(),"Data not found.",noSuchElementException.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomMessage> handleNotFound(IllegalArgumentException illegalArgumentException){
        return new ResponseEntity<CustomMessage>(new CustomMessage(HttpStatus.BAD_REQUEST.value(),"Bad Request.",illegalArgumentException.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<CustomMessage> handleNull(NullPointerException nullPointerException){
        return new ResponseEntity<CustomMessage>(new CustomMessage(HttpStatus.BAD_REQUEST.value(),"Bad Request.",nullPointerException.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomMessage> handleConstrainViolation(ConstraintViolationException constraintViolationException){
        return new ResponseEntity<CustomMessage>(new CustomMessage(HttpStatus.BAD_REQUEST.value(),"Bad Request.",constraintViolationException.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomMessage> handleBadRequest(BadRequestException badRequestException){
        return new ResponseEntity<CustomMessage>(new CustomMessage(HttpStatus.BAD_REQUEST.value(),"Bad Request.",badRequestException.getMessage()),HttpStatus.NOT_FOUND);
    }
}
