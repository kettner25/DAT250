package com.example.demo.Models;

import lombok.Data;

@Data
public class Poll {
    public String question;
    public Instant publishedAt;
    public Instant validUntil;
}