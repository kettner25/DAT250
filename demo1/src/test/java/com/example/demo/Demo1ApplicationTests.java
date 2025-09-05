package com.example.demo;

import com.example.demo.Components.DomainManager;
import com.example.demo.Controllers.VoteOptionController;
import com.example.demo.Models.Poll;
import com.example.demo.Models.User;
import com.example.demo.Models.VoteOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.collection.ArrayAsIterableMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class Demo1ApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void VoteOptionTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        //Adding
        mockMvc.perform(get("/opt/"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        var opt = new VoteOption();
        opt.setId(0);
        opt.setPresentationOrder(1);
        opt.setCaption("Vote ME!!!");

        List<VoteOption> voteOptions = new ArrayList<>();
        voteOptions.add(opt);

        mockMvc.perform(post("/opt/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(opt)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        mockMvc.perform(get("/opt/0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(opt)));

        mockMvc.perform(get("/opt/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(voteOptions)));

        opt = new VoteOption();
        opt.setId(1);
        opt.setPresentationOrder(3);
        opt.setCaption("DOONT vote HIM!");
        voteOptions.add(opt);

        mockMvc.perform(post("/opt/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(opt)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        mockMvc.perform(get("/opt/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(voteOptions)));


        //Wrong one
        opt = new VoteOption();
        opt.setId(2);
        opt.setPresentationOrder(-1);
        opt.setCaption("DOONT vote HIM!");

        mockMvc.perform(post("/opt/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(opt)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));

        //Check if was not added
        mockMvc.perform(get("/opt/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(voteOptions)));


        //Changing
        voteOptions.get(0).setPresentationOrder(10);
        voteOptions.get(1).setCaption("Never mind!");
        mockMvc.perform(put("/opt/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(voteOptions.get(0))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        mockMvc.perform(put("/opt/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(voteOptions.get(1))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        //Check if were edited
        mockMvc.perform(get("/opt/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(voteOptions)));


        //Delete
        mockMvc.perform(delete("/opt/0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        voteOptions.removeFirst();

        mockMvc.perform(delete("/opt/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));

        //Check if was deleted
        mockMvc.perform(get("/opt/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(voteOptions)));

        mockMvc.perform(get("/opt/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

    }

    @Test
    void UserTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        //Adding
        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        var user = new User();
        user.setUsername("test01");
        user.setEmail("test01@email.com");

        List<User> users = new ArrayList<>();
        users.add(user);

        mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(users)));

        mockMvc.perform(get("/user/test01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(user)));

        user = new User();
        user.setUsername("test02");
        user.setEmail("test02@email.com");
        users.add(user);

        mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(users)));


        //Wrong one
        user = new User();
        user.setUsername("test02");
        user.setEmail("test02@email.com");

        mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));

        //Check if was not added
        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(users)));


        //Changing
        users.get(0).setEmail("test01@gmail.com");
        users.get(1).setUsername("test03");
        mockMvc.perform(put("/user/test01")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(users.get(0))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        mockMvc.perform(put("/user/test02")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(users.get(1))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        //Check if were edited
        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(users)));

        //Wrong one (duplicit change)
        users.get(1).setUsername("test01");
        mockMvc.perform(put("/user/test03")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(users.get(1))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));
        users.get(1).setUsername("test03");

        //Delete
        mockMvc.perform(delete("/user/test03"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        mockMvc.perform(delete("/user/test02"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));

        users.removeLast();

        //Check if was deleted
        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(users)));

        mockMvc.perform(get("/user/test03"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void PollTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        //Settup
        var user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@gmail.com");

        var user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@gmail.com");

        mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        var opt = new VoteOption();
        opt.setId(0);
        opt.setPresentationOrder(1);
        opt.setCaption("Vote ME!!!");

        List<VoteOption> voteOptions = new ArrayList<>();
        voteOptions.add(opt);

        mockMvc.perform(post("/opt/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(opt)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        opt = new VoteOption();
        opt.setId(1);
        opt.setPresentationOrder(3);
        opt.setCaption("NOO, Vote MEEEE!!!");
        voteOptions.add(opt);

        mockMvc.perform(post("/opt/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(opt)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        //-----------------------------------------------------------------------
        //Adding
        mockMvc.perform(get("/poll/user1"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        mockMvc.perform(get("/poll/user2"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        var poll = new Poll();
        poll.setId(0);
        poll.setCreator(user1);
        poll.setPublishedAt(Instant.now());
        poll.setQuestion("Question 1");
        poll.setValidUntil(Instant.now().plusSeconds(1600));
        poll.setVoteOpts(voteOptions);

        List<Poll> polls = new ArrayList<>();
        polls.add(poll);

        mockMvc.perform(post("/poll/user1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(poll)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        mockMvc.perform(get("/poll/user1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(polls)));

        mockMvc.perform(get("/poll/0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(poll)));

        poll = new Poll();
        poll.setId(1);
        poll.setCreator(user1);
        poll.setPublishedAt(Instant.now());
        poll.setQuestion("Question 2");
        poll.setValidUntil(Instant.now().plusSeconds(160));
        poll.setVoteOpts(voteOptions);
        polls.add(poll);

        mockMvc.perform(post("/poll/user1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(poll)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        mockMvc.perform(get("/poll/user1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(polls)));

        mockMvc.perform(get("/poll/user2"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        //Cross add

        poll = new Poll();
        poll.setId(2);
        poll.setCreator(user2);
        poll.setPublishedAt(Instant.now());
        poll.setQuestion("Question 1");
        poll.setValidUntil(Instant.now().plusSeconds(160));
        poll.setVoteOpts(voteOptions);

        List<Poll> polls2 = new ArrayList<>();
        polls2.add(poll);

        mockMvc.perform(post("/poll/user2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(poll)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        mockMvc.perform(get("/poll/user2"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(polls2)));

        mockMvc.perform(get("/poll/user1"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(polls)));

        //Wrong one
        poll = new Poll();
        poll.setId(3);
        poll.setCreator(null);
        poll.setPublishedAt(null);
        poll.setQuestion(null);
        poll.setValidUntil(null);

        mockMvc.perform(post("/poll/user1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(poll)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));

        poll.setCreator(user1);
        mockMvc.perform(post("/poll/user1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(poll)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));

        poll.setPublishedAt(Instant.now());
        mockMvc.perform(post("/poll/user1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(poll)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));

        poll.setQuestion("Question NEW!");
        mockMvc.perform(post("/poll/user1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(poll)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));

        //Check if was not added
        mockMvc.perform(get("/poll/user1/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(polls)));

        poll.setValidUntil(Instant.now().plusSeconds(1000));

        mockMvc.perform(get("/poll/user1/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(polls)));

        poll.setVoteOpts(voteOptions);
        polls.add(poll);

        mockMvc.perform(post("/poll/user1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(poll)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        //Changing
        polls.get(0).setQuestion("About Question 02.");
        polls.get(1).setCreator(user1);
        mockMvc.perform(put("/poll/user1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(polls.get(0))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        mockMvc.perform(put("/poll/user1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(polls.get(1))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));

        //Check if were edited
        mockMvc.perform(get("/poll/user1/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(polls)));

        //Check if were edited
        mockMvc.perform(get("/poll/user2/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(polls2)));

        //Delete
        mockMvc.perform(delete("/poll/0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        mockMvc.perform(delete("/poll/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));

        polls.removeFirst();

        //Check if was deleted
        mockMvc.perform(get("/poll/user1/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(polls)));

        mockMvc.perform(get("/poll/user2"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(polls2)));
    }
}
