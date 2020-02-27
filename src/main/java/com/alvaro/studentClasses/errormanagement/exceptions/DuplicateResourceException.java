package com.alvaro.studentClasses.errormanagement.exceptions;

public class DuplicateResourceException extends Exception{
    private static final long serialVersionUID = 1L;
    public DuplicateResourceException(String message){
        super(message);
    }
}