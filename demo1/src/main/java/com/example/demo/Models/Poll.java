package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data

//needs to be added because of recursion when hashcode is being calc
@EqualsAndHashCode(exclude = "creator")
public class Poll {
    //Unique
    private int id = -1;

    private String question;
    private Instant publishedAt;
    private Instant validUntil;

    private List<VoteOption> voteOpts = new ArrayList<>();

    @JsonIgnore
    private User creator;

    public boolean Validate() {
        if (question == null) return false;
        if (publishedAt == null) return false;
        if (validUntil == null) return false;
        if (voteOpts.size() < 2) return false;

        return true;
    }
}