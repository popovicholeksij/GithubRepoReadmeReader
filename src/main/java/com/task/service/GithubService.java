package com.task.service;

import com.task.client.GithubApiClient;
import com.task.dto.Member;
import com.task.dto.Readme;
import com.task.dto.Repository;
import jakarta.inject.Singleton;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class GithubService {

    private final GithubApiClient client;
    private final int REPO_MEMBERS_PER_PAGE = 100;
    private final int MIN_WORD_LENGTH = 4;
    private final String WORDS_REGEX = "[^a-zA-Z]+";

    public GithubService(GithubApiClient client) {
        this.client = client;
    }

    public Map<String, Integer> findPopularWords(String repo, int limit) {
        List<String> allReadmeContents = getAllReadmeContents(repo);

        List<String> words = allReadmeContents.stream()
                .map(a -> Arrays.asList(a.split(WORDS_REGEX)))
                .flatMap(Collection::stream)
                .filter(word -> word.length() > MIN_WORD_LENGTH)
                .toList();

        HashMap<String, Integer> wordFrequencyMap = new HashMap<>();
        for (String word : words) {
            Integer freq = wordFrequencyMap.getOrDefault(word, 0);
            wordFrequencyMap.put(word, freq + 1);
        }

        return wordFrequencyMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private List<String> getAllReadmeContents(String repo) {
        List<String> decodedContents = new ArrayList<>();

        List<Member> repoMembers = getRepoMembers(repo);

        Map<String, List<Repository>> userRepositoryMap = repoMembers.stream()
                .collect(Collectors.toMap(Member::login, user -> getUserRepos(user.login())));

        for (Map.Entry<String, List<Repository>> entry : userRepositoryMap.entrySet()) {
            for (Repository repository : entry.getValue()) {
                String decodedReadmeContent = getDecodedReadmeContent(entry.getKey(), repository.name());
                if (decodedReadmeContent != null) {
                    decodedContents.add(decodedReadmeContent);
                }
            }
        }
        return decodedContents;
    }

    private List<Member> getRepoMembers(String repo) {
        return client.getRepoMembers(repo, REPO_MEMBERS_PER_PAGE)
                .orElseThrow(() -> new RuntimeException("Users not found"));
    }

    private List<Repository> getUserRepos(String login) {
        return client.getUserRepos(login)
                .orElseThrow(() -> new RuntimeException("User repositories not found"));
    }

    private String getDecodedReadmeContent(String login, String repo) {
        Readme readme = client.getUserRepoReadme(login, repo).orElse(null);
        if (readme != null) {
            return decode(readme.content());
        }
        return null;
    }

    private String decode(String encoded) {
        String s = encoded.replace("\n", "");
        return new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)));
    }
}
