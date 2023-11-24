package com.ayush.reddit.clone.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String postNotFound) {
        super(postNotFound);
    }
}
