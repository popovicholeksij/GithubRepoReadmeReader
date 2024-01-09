package com.task.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Repository(String name) {
}
