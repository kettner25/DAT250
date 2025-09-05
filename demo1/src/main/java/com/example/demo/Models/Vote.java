package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.Instant;

@Data
public class Vote {
    private Instant publishedAt;

    private VoteOption option;

    @JsonIgnore
    private User user;

    public boolean Validate() {
        if (publishedAt == null) return false;
        if (option == null) return false;

        return true;
    }
}