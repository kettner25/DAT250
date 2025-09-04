package com.example.demo.Models;

import lombok.Data;

import java.time.Instant;

@Data
public class Vote {
    public Instant publishedAt;

    public VoteOption option;
    public User user;

    //TODO
    public boolean Validate() {
        return true;
    }
}