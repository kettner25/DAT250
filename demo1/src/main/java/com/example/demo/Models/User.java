package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    //Unique
    private String username;
    private String email;

    private List<Poll> created = new ArrayList<>();
    private List<Vote> voted = new ArrayList<>();

    public boolean Validate() {
        if (username == null || username.isEmpty() || email == null || email.isEmpty()) return false;

        return true;
    }

    /**
     * Creates a new User object with given username and email.
     * The id of a new user object gets determined by the database.
     */
    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.created = new ArrayList<>();
    }

    /**
     * Creates a new Poll object for this user
     * with the given poll question
     * and returns it.
     */
    public Poll createPoll(String question) {
        var poll =  new Poll();
        poll.setQuestion(question);

        poll.setCreator(this);
        poll.setPublishedAt(Instant.now());

        created.add(poll);

        return poll;
    }

    /**
     * Creates a new Vote for a given VoteOption in a Poll
     * and returns the Vote as an object.
     */
    public Vote voteFor(VoteOption option) {
        var vote = new Vote();

        vote.setOption(option);
        vote.setUser(this);

        vote.setPublishedAt(Instant.now());

        voted.add(vote);

        return vote;
    }
}