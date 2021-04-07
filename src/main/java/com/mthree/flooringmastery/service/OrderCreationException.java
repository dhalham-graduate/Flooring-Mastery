package com.mthree.flooringmastery.service;

public class OrderCreationException extends Exception{

    public OrderCreationException(String message){
        super(message);
    }

    public OrderCreationException(String message, Throwable cause){
        super(message, cause);
    }

}
