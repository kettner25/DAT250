package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class VoteOption {
    @Id
    @Column(name = "opt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = -1;

    private String caption;
    private Integer presentationOrder;

    @JsonIgnore
    @OneToMany(mappedBy = "option")
    private List<Vote> votes = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    private Poll poll = new Poll();

    public boolean Validate() {
        if (caption == null || caption.isEmpty()) return false;
        if (presentationOrder < 1) return false;

        return true;
    }
}