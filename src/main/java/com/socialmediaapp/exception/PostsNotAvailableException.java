package com.socialmediaapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostsNotAvailableException extends RuntimeException {

    public PostsNotAvailableException(String message) {
        super(message);
    }
}
