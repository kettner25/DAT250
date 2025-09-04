package com.example.demo.Models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    public String username;
    public String email;

    public List<Poll> created = new ArrayList<>();
    public List<Vote> voted = new ArrayList<>();
}