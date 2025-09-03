package com.example.demo.Models;

import lombok.Data;

@Data
public class Vote {
    public Instant publishedAt;
    public VoteOption option;
}