package com.example.demo;

import com.example.demo.Components.DomainManager;
import com.example.demo.Controllers.VoteOptionController;
import com.example.demo.Models.VoteOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

        //Check if was deleted
        mockMvc.perform(get("/opt/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(voteOptions)));

    }

}
