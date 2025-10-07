package com.example.demo.Controllers;

import com.example.demo.Components.DomainManager;
import com.example.demo.Conf.RabbitConfiguration;
import com.example.demo.Models.PollTopicVoteModel;
import com.example.demo.Models.User;
import com.example.demo.Models.Vote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
class VoteController {
    private final DomainManager data;

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper mapper;

    VoteController(DomainManager _data, RabbitTemplate rabbitTemplate, ObjectMapper mapper) {
        this.data = _data;
        this.rabbitTemplate = rabbitTemplate;
        this.mapper = mapper;
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
    @PostMapping("/vote/{name}")
    public boolean Create(@PathVariable String name, @RequestBody Vote vote) throws JsonProcessingException {
        if (!vote.Validate()) return false;

        Integer pollID = data.getPollIdByOption(vote.getOption());

        if  (pollID == null) return false;

        PollTopicVoteModel model = new PollTopicVoteModel(name, pollID, vote.getOption().getId(), PollTopicVoteModel.VoteType.Vote);

        String routingKey = "poll." + pollID;
        try {
            rabbitTemplate.convertAndSend(RabbitConfiguration.POLL_EXCHANGE, routingKey, mapper.writeValueAsString(model));
        } catch (Exception e) { return false; }

        return true;
        //return data.addVote(name, vote.getOption().getId());
    }

    /**
     * Update vote by user and opt id
     * */
    @PutMapping("/vote/{name}/{id}")
    public boolean Update(@PathVariable String name, @PathVariable int id, @RequestBody Vote vote) {
        var user =  data.getUserByName(name);

        if (user == null) return false;

        //this is now not possible
        //if (!user.getUsername().equals(vote.getUser().getUsername())) return false;

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
        var opt = data.getVoteOptById(id);

        Integer pollID = data.getPollIdByOption(opt);

        if  (pollID == null) return false;

        PollTopicVoteModel model = new PollTopicVoteModel(name, pollID, id, PollTopicVoteModel.VoteType.UnVote);

        String routingKey = "poll." + pollID;
        try {
            rabbitTemplate.convertAndSend(RabbitConfiguration.POLL_EXCHANGE, routingKey, mapper.writeValueAsString(model));
        } catch (Exception e) { return false; }

        return true;

        //return data.removeVote(name, id);
    }
}
