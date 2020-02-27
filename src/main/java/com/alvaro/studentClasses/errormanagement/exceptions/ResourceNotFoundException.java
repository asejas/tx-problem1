package com.alvaro.studentClasses.errormanagement.exceptions;

/**
 * This class represents a resource not found exception
 */
public class ResourceNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;
    public ResourceNotFoundException(String message){
        super(message);
    }
}