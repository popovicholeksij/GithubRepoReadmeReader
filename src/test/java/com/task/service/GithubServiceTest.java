package com.task.service;

import com.task.client.GithubApiClient;
import com.task.dto.Member;
import com.task.dto.Readme;
import com.task.dto.Repository;
import com.task.dto.WordQuantity;
import com.task.exception.GithubServiceException;
import com.task.service.impl.GithubServiceImpl;
import io.micronaut.core.io.ResourceLoader;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@MicronautTest
class GithubServiceTest {

    @Inject
    GithubServiceImpl service;

    @Inject
    GithubApiClient githubApiClient;

    @Inject
    private ResourceLoader loader;

    private final String REPO = "Spotify";
    private final int LIMIT = 5;

    @MockBean(GithubApiClient.class)
    GithubApiClient githubApiClient() {
        return Mockito.mock(GithubApiClient.class);
    }

    @Test
    void findPopularWords() throws IOException {
        String encodedText = new String(loader.getResourceAsStream("readmeFileExample")
                .orElseThrow().readAllBytes());

        when(githubApiClient.getRepoMembers(eq(REPO), anyInt()))
                .thenReturn(Optional.of(List.of(
                        new Member("user1"))
                ));
        when(githubApiClient.getUserRepos(anyString()))
                .thenReturn(Optional.of(List.of(new Repository("repository1"))));

        when(githubApiClient.getUserRepoReadme(anyString(), anyString()))
                .thenReturn(Optional.of(new Readme(encodedText)));

        List<WordQuantity> popularWordsList = service.findPopularWords(REPO, LIMIT);

        assertEquals(5, popularWordsList.size());

        assertEquals("thought", popularWordsList.get(0).word());
        assertEquals(165, popularWordsList.get(0).quantity());

        assertEquals("could", popularWordsList.get(1).word());
        assertEquals(114, popularWordsList.get(1).quantity());

        assertEquals("water", popularWordsList.get(2).word());
        assertEquals(106, popularWordsList.get(2).quantity());
    }

    @Test()
    void findPopularWordsUsersNotFound() {
        when(githubApiClient.getRepoMembers(eq(REPO), anyInt())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(GithubServiceException.class, () -> service.findPopularWords(REPO, LIMIT));
        assertEquals(exception.getMessage(), "Users not found");
    }

    @Test()
    void findPopularWordsUsersReposNotFound() {
        when(githubApiClient.getRepoMembers(eq(REPO), anyInt()))
                .thenReturn(Optional.of(List.of(new Member("user1"))));
        when(githubApiClient.getUserRepos(anyString())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(GithubServiceException.class, () -> service.findPopularWords(REPO, LIMIT));
        assertEquals(exception.getMessage(), "User repositories not found");
    }
}