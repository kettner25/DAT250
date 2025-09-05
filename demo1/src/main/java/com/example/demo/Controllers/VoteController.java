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
     * Get all votes of user by its name
     * */
    @GetMapping("/vote/{name}/{id}")
    public Vote FetchOne(@PathVariable String name, @PathVariable int id) {
        var user = data.getUserByName(name);

        if (user == null) return null;

        var opt = data.getVoteOptById(id);

        if (opt == null) return null;

        return user.getVoted().stream().filter(f -> f.getOption().getId() == id).findFirst().orElse(null);
    }

    /**
     * Create vote by user
     * */
    @PostMapping("/vote/")
    public boolean Create(@RequestBody Vote vote) {
        if (!vote.Validate()) return false;

        var user = data.getUserByName(vote.getUser().getUsername());

        if (user == null) return false;

        var opt = data.getVoteOptById(vote.getOption().getId());

        if (opt == null) return false;

        if (user.getVoted().stream().filter(f -> f.getOption().getId() == opt.getId()).findFirst().orElse(null) != null) return false;

        vote.setOption(opt);
        vote.setUser(user);
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
        if (!user.getUsername().equals(vote.getUser().getUsername())) return false;

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

            if (user.getVoted().stream().anyMatch(f -> f.getOption().getId() == tmp.getId())) return false;

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
