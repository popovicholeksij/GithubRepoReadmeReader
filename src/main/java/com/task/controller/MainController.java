package com.task.controller;

import com.task.dto.WordQuantity;
import com.task.service.impl.GithubServiceImpl;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

import java.util.List;


@Controller
public class MainController {

    private final GithubServiceImpl service;

    public MainController(GithubServiceImpl service) {
        this.service = service;
    }

    @Get("/{value}")
    HttpResponse<List<WordQuantity>> fetchMembers(String value, @QueryValue(value = "limit", defaultValue = "3") int limit) {
        return HttpResponse.ok(service.findPopularWords(value, limit));
    }
}
