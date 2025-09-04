package com.example.demo.Controllers;

import com.example.demo.Components.DomainManager;
import com.example.demo.Models.Poll;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PollController {

    private final DomainManager data;

    public PollController(DomainManager _data) {
        this.data = _data;
    }

    /**
     * Get poll by its ID
     * */
    @GetMapping("/poll/{id}")
    public Poll Fetch(@PathVariable int id) {
        return data.getPollById(id);
    }

    /**
     * Get list of polls by username
     * */
    @GetMapping("/poll/{name}")
    public List<Poll> FetchAll(@PathVariable String name) {
        var user = data.getUserByName(name);

        if (user == null) return null;

        return user.getCreated();
    }

    /**
     * Create poll by user ... ident. by name
     * */
    @PostMapping("/poll/{name}")
    public boolean Create(@PathVariable String name, @RequestBody Poll poll) {
        var user = data.getUserByName(name);

        if (user == null) return false;

        if (!poll.Validate()) return false;

        poll.setId(data.getData().getPolls().size());
        poll.setCreator(user);

        data.getData().getPolls().add(poll);
        user.getCreated().add(poll);

        return true;
    }

    /**
     * Update poll by its ID
     * */
    @PutMapping("/poll/{id}")
    public boolean Update(@PathVariable int id, @RequestBody Poll poll) {
        var _poll = data.getPollById(id);

        if (_poll == null) return false;

        if (!poll.Validate()) return false;

        if (!poll.getCreator().getUsername().equals(_poll.getCreator().getUsername())) return false;

        _poll.setQuestion(poll.getQuestion());
        _poll.setVoteOpts(poll.getVoteOpts());
        _poll.setValidUntil(poll.getValidUntil());
        _poll.setPublishedAt(_poll.getPublishedAt());

        return true;
    }

    /**
     * Delete poll by its id
     * */
    @DeleteMapping("/poll/{id}")
    public boolean Delete(@PathVariable int id) {
        var poll = data.getPollById(id);

        if (poll == null) return false;

        poll.getCreator().getCreated().remove(poll);
        data.getData().getPolls().remove(poll);

        return true;
    }
}
