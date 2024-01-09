package com.task.auth;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.RequestFilter;

@ClientFilter("/**")
public class GithubFilter {

    private final String username;
    private final String token;

    public GithubFilter(@Value("${github.username}") String username,
                        @Value("${github.token}") String token) {
        this.username = username;
        this.token = token;
    }

    @RequestFilter
    public void doFilter(MutableHttpRequest<?> request) {
        request.basicAuth(username, token);
    }
}
