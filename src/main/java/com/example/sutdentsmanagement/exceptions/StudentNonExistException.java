package com.example.sutdentsmanagement.exceptions;

public class StudentNonExistException extends RuntimeException{

    public StudentNonExistException(String message) {
        super(message);
    }
}
