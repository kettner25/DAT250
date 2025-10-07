package com.example.demo.Controllers;

import com.example.demo.Components.DomainManager;
import com.example.demo.Models.Poll;
import com.example.demo.Services.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PollController {

    private final MessageManager messageManager;

    private final DomainManager data;

    public PollController(DomainManager _data, MessageManager messageManager) {
        this.data = _data;
        this.messageManager = messageManager;
    }

    /**
     * Get poll by its ID
     * */
    @GetMapping("/poll/{name}/{id}")
    public Poll Fetch(@PathVariable String name, @PathVariable int id) {
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
    public int Create(@PathVariable String name, @RequestBody Poll poll) {
        var user = data.getUserByName(name);

        if (user == null) return -1;

        if (!poll.Validate()) return -1;

        var maxID = data.getData().getPolls().stream().toList().stream().
                max((p1, p2) -> Integer.compare(p1.getId(), p2.getId()))
                .orElse(null);

        poll.setId(maxID==null ? 0 : maxID.getId() + 1);
        poll.setCreator(user);

        data.getData().getPolls().add(poll);
        user.getCreated().add(poll);

        messageManager.RegisterAndSubscribeTopic(poll);

        return poll.getId();
    }

    /**
     * Update poll by its ID
     * */
    @PutMapping("/poll/{name}")
    public boolean Update(@PathVariable String name, @RequestBody Poll poll) {
        var user = data.getUserByName(name);

        if (user == null) return false;

        var _poll = data.getPollById(poll.getId());

        if (!poll.Validate()) return false;

        //This is now not possible
        //if (!poll.getCreator().getUsername().equals(_poll.getCreator().getUsername())) return false;

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
        data.getData().getPolls().removeIf(f -> f.getId() == id);
        messageManager.deletePollTopic(poll);

        return true;
    }
}
