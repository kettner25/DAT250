package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    //Unique
    private String username;
    private String email;

    private List<Poll> created = new ArrayList<>();
    private List<Vote> voted = new ArrayList<>();

    public boolean Validate() {
        if (username == null || username.isEmpty() || email == null || email.isEmpty()) return false;

        return true;
    }
}