package com.example.sutdentsmanagement.exceptions;

public class StudentEmptyNameException extends RuntimeException {

    public StudentEmptyNameException(String message){
        super(message);
    }
}
