package br.com.catalisa.stockz.exception;
import javax.validation.ConstraintViolation;

import br.com.catalisa.stockz.exception.error.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler  {
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErrorMessage> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException exception) {
        ErrorMessage errorResponse = new ErrorMessage(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AtributoIncorretoException.class)
    public ResponseEntity<ErrorMessage> handleTransacaoInvalidaException(AtributoIncorretoException exception) {
        ErrorMessage errorResponse = new ErrorMessage(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<ErrorMessage> handleEmailDuplicadoException(EmailDuplicadoException exception) {
        ErrorMessage errorResponse = new ErrorMessage(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CategoriaJaCadastradaException.class)
    public ResponseEntity<ErrorMessage> handleCategoriaJaCadastradaException(CategoriaJaCadastradaException exception) {
        ErrorMessage errorResponse = new ErrorMessage(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected  ResponseEntity<ErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorMessage apiErrorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errors);

        return new ResponseEntity<>(apiErrorMessage, apiErrorMessage.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> message = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ErrorMessage errorResponse = new ErrorMessage(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
