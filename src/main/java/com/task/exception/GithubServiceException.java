package com.task.exception;

public class GithubServiceException extends RuntimeException {

    public GithubServiceException(String message) {
        super(message);
    }
}
