package com.task.client;

import com.task.dto.Member;
import com.task.dto.Readme;
import com.task.dto.Repository;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Optional;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

@Client(id = "github")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = "application/vnd.github.v3+json, application/json")
public interface GithubApiClient {

    @Get("/orgs/{repo}/members")
    Optional<List<Member>> getRepoMembers(String repo, @QueryValue(value= "per_page") int perPage);

    @Get("/users/{user}/repos")
    Optional<List<Repository>> getUserRepos(String user);

    @Get("/repos/{user}/{repo}/readme")
    Optional<Readme> getUserRepoReadme(String user, String repo);

}
