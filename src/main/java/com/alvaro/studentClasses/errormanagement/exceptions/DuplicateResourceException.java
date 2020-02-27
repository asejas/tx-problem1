package com.alvaro.studentClasses.errormanagement.exceptions;
/**
 * This class implements a Duplicate Resource Exception
 */
public class DuplicateResourceException extends Exception{
    private static final long serialVersionUID = 1L;
    public DuplicateResourceException(String message){
        super(message);
    }
}