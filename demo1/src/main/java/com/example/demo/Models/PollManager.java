package com.example.demo.Models;

import lombok.Data;

import java.util.HashSet;

@Data
public class PollManager {
    public HashSet<User> users;
    public HashSet<Poll> polls;
    public HashSet<VoteOption> voteOpts;
}