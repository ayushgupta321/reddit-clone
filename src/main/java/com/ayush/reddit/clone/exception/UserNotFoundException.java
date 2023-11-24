package com.ayush.reddit.clone.exception;

public class UserNotFoundException extends  RuntimeException{
    public UserNotFoundException(String noUserFound) {
        super(noUserFound);
    }
}
