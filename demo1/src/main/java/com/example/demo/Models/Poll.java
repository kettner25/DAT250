package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
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

    /**
     *
     * Adds a new option to this Poll and returns the respective
     * VoteOption object with the given caption.
     * The value of the presentationOrder field gets determined
     * by the size of the currently existing VoteOptions for this Poll.
     * I.e. the first added VoteOption has presentationOrder=0, the secondly
     * registered VoteOption has presentationOrder=1 ans so on.
     */
    public VoteOption addVoteOption(String caption) {
        var voteOption = new VoteOption();

        voteOption.setCaption(caption);

        int order = 0;
        var a = voteOpts.stream().max(Comparator.comparing(s -> s.getPresentationOrder())).orElse(null);
        if (a != null) order = a.getPresentationOrder() + 1;

        voteOption.setPresentationOrder(order);

        voteOpts.add(voteOption);

        return voteOption;
    }
}