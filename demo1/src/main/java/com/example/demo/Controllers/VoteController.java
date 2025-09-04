package com.example.demo.Controllers;

import com.example.demo.Components.DomainManager;
import com.example.demo.Models.User;
import com.example.demo.Models.Vote;
import jakarta.servlet.Filter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
class VoteController {
    private final DomainManager data;

    VoteController(DomainManager _data) {
        this.data = _data;
    }

    /**
     * Get all votes of user by its name
     * */
    @GetMapping("/vote/{name}")
    public List<Vote> Fetch(@PathVariable String name) {
        var user = data.getUserByName(name);

        if (user == null) return null;

        return user.getVoted();
    }

    /**
     * Create vote by user
     * */
    @PostMapping("/vote/{name}")
    public boolean Create(@PathVariable String name, @RequestBody Vote vote) {
        var user = data.getUserByName(name);

        if (user == null) return false;

        if (!vote.Validate()) return false;

        var opt = data.getVoteOptById(vote.getOption().getId());

        if (opt == null) return false;

        vote.setOption(opt);
        user.getVoted().add(vote);

        return true;
    }

    /**
     * Update vote by user and opt id
     * */
    @PutMapping("/vote/{name}/{id}")
    public boolean Update(@PathVariable String name, @PathVariable int id, @RequestBody Vote vote) {
        var user =  data.getUserByName(name);

        if (user == null) return false;
        if (!user.getVoted().equals(vote.getUser().getVoted())) return false;

        if (!vote.Validate()) return false;

        var opt = data.getVoteOptById(id);

        if (opt == null) return false;

        var _vote =  user.getVoted().stream()
                .filter(f -> f.getOption().getId() == id)
                .findFirst().orElse(null);

        if (_vote == null) return false;

        /*Setting*/
        if (id != vote.getOption().getId()) {
            var tmp = data.getVoteOptById(vote.getOption().getId());

            if (tmp == null) return false;

            _vote.setOption(tmp);
        }

        _vote.setPublishedAt(vote.getPublishedAt());

        return true;
    }

    /**
     * Delete vote by user and voteOpt id
     * */
    @DeleteMapping("/vote/{name}/{id}")
    public boolean Delete(@PathVariable String name, @PathVariable int id) {
        var user = data.getUserByName(name);

        if (user == null) return false;

        return user.getVoted().removeIf(f  -> f.getOption().getId() == id);
    }

}
