package com.example.demo;

import com.example.demo.Models.Poll;
import com.google.gson.JsonParser;
import redis.clients.jedis.UnifiedJedis;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.json.Path2;


public class VoteCntCache implements AutoCloseable {
    private UnifiedJedis jedis;

    public VoteCntCache() {
        this.jedis = new UnifiedJedis("redis://localhost:6379");
    }

    public boolean AddPollToCache(Poll poll) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            jedis.jsonSet("poll:"+poll.getId(), mapper.writeValueAsString(poll));

            jedis.expire("poll:"+poll.getId(), 60);
        }
        catch (Exception e) { return false; }

        return  true;
    }

    public boolean InvalidatePollFromCache(Poll poll) {
        try {
            jedis.jsonDel("poll:"+poll.getId());
        } catch (Exception e) { return false; }

        return  true;
    }

    public Poll GetPollFromCache(String id) {
        Poll poll = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            var a = jedis.jsonGet("poll:"+id);
            String json = mapper.writeValueAsString(a);

            poll = mapper.readValue(json, Poll.class);
        }
        catch (Exception e) { return null; }

        return poll;
    }

    @Override
    public void close() throws Exception {
        jedis.close();
    }
}
