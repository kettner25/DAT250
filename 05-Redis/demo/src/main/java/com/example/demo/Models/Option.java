package com.example.demo.Models;

public class Option {
    private String caption;
    private int voteCount;

    public Option() {}
    public Option(String caption, int voteCount) {
        this.caption = caption;
        this.voteCount = voteCount;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Option)) return false;
        Option opt = (Option) o;
        return caption.equals(opt.caption) && voteCount == opt.voteCount;
    }
}
