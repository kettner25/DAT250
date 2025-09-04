package com.example.demo.Controllers;

import com.example.demo.Components.DomainManager;
import com.example.demo.Models.Vote;
import com.example.demo.Models.VoteOption;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VoteOptionController {
    private final DomainManager data;

    VoteOptionController(DomainManager _data) {
        this.data = _data;
    }

    /**
     * Get all votesOptions
     * */
    @GetMapping("/opt/")
    public List<VoteOption> FetchAll() {
        return data.getData().getVoteOpts().stream().toList();
    }

    /**
     * Get votesOptions by its id
     * */
    @GetMapping("/opt/{id}")
    public VoteOption Fetch(@PathVariable int id) {
        return data.getVoteOptById(id);
    }

    /**
     * Create vote option
     * */
    @PostMapping("/opt")
    public boolean Create(@RequestBody VoteOption opt) {
        if (!opt.Validate()) return false;

        var maxID = data.getData().getVoteOpts().stream().toList()
                .stream().max((p1, p2) -> Integer.compare(p1.getId(), p2.getId()))
                .orElse(null);

        opt.setId(maxID==null ? 0 : maxID.getId() + 1);
        data.getData().getVoteOpts().add(opt);

        return true;
    }

    /**
     * Update voteOpt by opt id
     * */
    @PutMapping("/opt/{id}")
    public boolean Update(@PathVariable int id, @RequestBody VoteOption opt) {
        var _opt = data.getVoteOptById(id);

        if (_opt == null) return false;

        if (!_opt.Validate()) return false;

        _opt.setCaption(opt.getCaption());
        _opt.setPresentationOrder(opt.getPresentationOrder());

        return true;
    }

    /**
     * Delete voteOpt by voteOpt id
     * */
    @DeleteMapping("/opt/{id}")
    public boolean Delete(@PathVariable int id) {
        var opt = data.getVoteOptById(id);

        if (opt == null) return false;

        data.getData().getVoteOpts().remove(opt);

        return true;
    }
}
