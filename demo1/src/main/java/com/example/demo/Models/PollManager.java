package com.example.demo.Models;

import lombok.Data;

import java.util.HashSet;

@Data
public class PollManager {
    public HashSet<User> users = new HashSet<>();
    public HashSet<Poll> polls = new HashSet<>();
    public HashSet<VoteOption> voteOpts = new HashSet<>();
}