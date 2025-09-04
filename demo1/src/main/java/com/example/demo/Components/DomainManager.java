package com.example.demo.Components;

import com.example.demo.Models.PollManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class DomainManager {
    @Getter
    @Setter
    public PollManager data;
}