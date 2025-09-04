package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VoteOption {
    public int id = -1;

    public String caption;
    public int presentationOrder;

    @JsonIgnore
    public List<Vote> votes = new ArrayList<>();

    //TODO
    public boolean Validate() {
        return true;
    }
}