package com.clothing.inventory.exception;

public class InvalidPurchaseStateException extends RuntimeException{

    public InvalidPurchaseStateException(String message) {

        super(message);
    }
}
