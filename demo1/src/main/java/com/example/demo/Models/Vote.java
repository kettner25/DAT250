package com.example.demo.Models;

import lombok.Data;

import java.time.Instant;

@Data
public class Vote {
    private Instant publishedAt;

    private VoteOption option;
    private User user;

    public boolean Validate() {
        if (publishedAt == null) return false;
        if (option == null) return false;
        if (user == null) return false;

        return true;
    }
}