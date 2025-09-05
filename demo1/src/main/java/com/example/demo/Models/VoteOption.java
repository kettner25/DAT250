package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VoteOption {
    private int id = -1;

    private String caption;
    private int presentationOrder;

    @JsonIgnore
    private List<Vote> votes = new ArrayList<>();

    public boolean Validate() {
        if (caption == null || caption.isEmpty()) return false;
        if (presentationOrder < 1) return false;

        return true;
    }
}