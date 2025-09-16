package com.example.demo.Models;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class VoteId implements Serializable {
    private Integer optionID;
    private String userID;
}
