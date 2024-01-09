package com.task.controller;

import com.task.service.GithubService;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

import java.util.Map;


@Controller
public class MainController {

    private final GithubService service;

    public MainController(GithubService service) {
        this.service = service;
    }

    @Get("/{value}")
    Map<String, Integer> fetchMembers(@Nullable String value,
                                      @QueryValue(value= "limit", defaultValue = "3") int limit) {
        return service.findPopularWords(value, limit);
    }
}
