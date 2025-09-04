package com.example.demo.Models;

import lombok.Data;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class Poll {
    public String question;
    public Instant publishedAt;
    public Instant validUntil;

    public List<VoteOption> voteOpts = new ArrayList<>();
    public User creator;
}