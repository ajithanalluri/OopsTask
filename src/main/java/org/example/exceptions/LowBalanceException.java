package org.example.exceptions;

public class LowBalanceException extends RuntimeException{

    public LowBalanceException(String msg) {
        super(msg);
    }
}
