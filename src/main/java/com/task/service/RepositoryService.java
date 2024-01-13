package com.task.service;

import com.task.dto.WordQuantity;

import java.util.List;

public interface RepositoryService {

    List<WordQuantity> findPopularWords(String repo, int limit);
}
