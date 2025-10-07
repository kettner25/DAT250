package com.example.demo.Models;

import lombok.Data;

@Data
public class PollTopicVoteModel {
    public enum VoteType {
        Vote,
        UnVote
    }

    public PollTopicVoteModel(String name, Integer pollID, Integer optID, VoteType type) {
        Username = name;
        PollID = pollID;
        OptID = optID;
        Type = type;
    }

    private Integer PollID;

    private Integer OptID;

    private String Username;

    private VoteType Type;
}


