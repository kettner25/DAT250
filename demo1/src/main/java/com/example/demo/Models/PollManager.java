package com.example.demo.Models;

import lombok.Data;

import java.util.HashSet;

@Data
public class PollManager {
    private HashSet<User> users = new HashSet<>();
    private HashSet<Poll> polls = new HashSet<>();
    private HashSet<VoteOption> voteOpts = new HashSet<>();
}