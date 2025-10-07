package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "votes")
public class Vote {
    private Instant publishedAt;

    @EmbeddedId
    private VoteId id = new VoteId();

    @MapsId("optionID")
    @JoinColumn(name = "opt_id")
    @ManyToOne
    private VoteOption option;

    @JsonIgnore

    @MapsId("userID")
    @JoinColumn(name = "username")
    @ManyToOne
    private User user;

    public boolean Validate() {
        if (publishedAt == null) return false;
        if (option == null) return false;

        return true;
    }
}