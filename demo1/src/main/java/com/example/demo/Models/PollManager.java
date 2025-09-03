package com.example.demo.Models;

import lombok.Data;

import java.util.HashSet;

@Data
public class PollManager {
    public static HashSet<User> users;
    public static HashSet<Poll> polls;
}