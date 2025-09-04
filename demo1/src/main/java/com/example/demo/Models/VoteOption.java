package com.example.demo.Models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VoteOption {
    public String caption;
    public int presentationOrder;

    public List<Vote> votes = new ArrayList<>();
}