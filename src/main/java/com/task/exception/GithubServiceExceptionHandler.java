package com.task.exception;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Requires(classes = {GithubServiceException.class, ExceptionHandler.class})
public class GithubServiceExceptionHandler implements ExceptionHandler<GithubServiceException, HttpResponse> {
    @Override
    public HttpResponse handle(HttpRequest request, GithubServiceException exception) {
        return HttpResponse.badRequest(exception.getMessage());
    }
}
