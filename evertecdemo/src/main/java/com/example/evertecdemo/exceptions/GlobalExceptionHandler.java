package com.example.evertecdemo.exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
 // Manejador de excepciones para RecursoNoEncontradoException
 @ExceptionHandler(RecursoNoEncontradoException.class)
 public ResponseEntity<Map<String, String>> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex) {
     Map<String, String> response = Map.of("error", ex.getMessage());
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
 }

 // Manejador de excepciones para IllegalArgumentException
 @ExceptionHandler(IllegalArgumentException.class)
 public ResponseEntity<Map<String, String>> manejarArgumentoIlegal(IllegalArgumentException ex) {
     Map<String, String> response = Map.of("error", ex.getMessage());
     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
 }
}
