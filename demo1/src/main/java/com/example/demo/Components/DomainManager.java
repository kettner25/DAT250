package com.example.demo.Components;

import com.example.demo.Models.Poll;
import com.example.demo.Models.PollManager;
import com.example.demo.Models.User;
import com.example.demo.Models.VoteOption;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class DomainManager {
    @Getter
    @Setter
    public PollManager data;

    public Poll getPollById(int id) {
        return data.getPolls().stream()
                .filter(i -> i.getId() == id)
                .findFirst().orElse(null);
    }

    public VoteOption getVoteOptById(int id) {
        return data.getVoteOpts().stream()
                .filter(i -> i.getId() == id)
                .findFirst().orElse(null);
    }

    public User getUserByName(String name) {
        return data.getUsers().stream()
                .filter(i -> i.getUsername().equals(name))
                .findFirst().orElse(null);
    }
}