package com.example.demo.Components;

import com.example.demo.Models.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DomainManager {
    @Getter
    @Setter
    public PollManager data = new PollManager();

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

    public boolean addVote(String name, Integer optID) {
        var user = getUserByName(name);

        if (user == null) return false;

        var opt = getVoteOptById(optID);

        if (opt == null) return false;

        //if (user.getVoted().stream().filter(f -> f.getOption().getId() == opt.getId()).findFirst().orElse(null) != null) return false;

        var vote = new Vote();
        vote.setPublishedAt(Instant.now());
        vote.setOption(opt);
        vote.setUser(user);
        user.getVoted().add(vote);

        return true;
    }

    public boolean removeVote(String name, Integer optID) {
        var user = getUserByName(name);
        if (user == null) return false;

        return user.getVoted().removeIf(f -> f.getOption().getId().equals(optID));
    }

    /**
     * @param id id of option
     * */
    public long getCntOfVoted(Integer id) {
        return data.getUsers().stream().filter(f -> f.getVoted().stream().anyMatch(v -> v.getOption().getId().equals(id))).count();
    }

    public Integer getPollIdByOption(VoteOption option) {
        var poll = data.getPolls().stream().filter(p -> p.getVoteOpts().stream().anyMatch(o -> o.getId().equals(option.getId()))).findFirst().orElse(null);

        if (poll == null) return null;
        return poll.getId();
    }
}