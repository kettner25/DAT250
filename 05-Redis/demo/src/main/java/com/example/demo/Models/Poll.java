package com.example.demo.Models;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Poll {
    private String id;

    private String title;

    private List<Option> options;

    public  Poll() {}
    public Poll(String title, List<Option> options, String id) {
        this.title = title;
        this.options = options;
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Poll)) return false;
        Poll poll = (Poll) o;
        return Objects.equals(id, poll.id) && Objects.equals(title, poll.title) && Objects.equals(options, poll.options);
    }
}
