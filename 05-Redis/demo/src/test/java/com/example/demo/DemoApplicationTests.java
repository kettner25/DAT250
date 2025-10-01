package com.example.demo;

import com.example.demo.Models.Option;
import com.example.demo.Models.Poll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() throws InterruptedException {

        VoteCntCache cache = new VoteCntCache();

        Poll poll1 = new Poll();
        Poll poll2 = new Poll();

        poll1.setId(UUID.randomUUID().toString());
        poll2.setId(UUID.randomUUID().toString());

        poll1.setTitle("Poll 1");
        poll2.setTitle("Poll 2");

        poll1.setOptions(List.of(new Option("Yes", 12), new Option("No", 30)));
        poll2.setOptions(List.of(new Option("Mnam", 12), new Option("WTF", 50), new Option("Ble", 26)));

        assertThat(cache).isNotNull();
        assertThat(cache.GetPollFromCache(poll1.getId())).isNull();
        assertThat(cache.GetPollFromCache(poll2.getId())).isNull();

        // Poll is not cashed so we get it from DB there

        // Now when we have poll we cash it

        assertThat(cache.AddPollToCache(poll1)).isTrue();
        assertThat(cache.GetPollFromCache(poll1.getId())).isNotNull();
        assertThat(cache.GetPollFromCache(poll2.getId())).isNull();

        assertThat(cache.GetPollFromCache(poll1.getId())).isEqualTo(poll1);
        // 02

        //We check if poll is in cache
        assertThat(cache.GetPollFromCache(poll1.getId())).isNotNull();
        assertThat(cache.GetPollFromCache(poll1.getId())).isEqualTo(poll1);

        //It is so we can return in

        //03 Now comes another poll
        assertThat(cache.GetPollFromCache(poll2.getId())).isNull();
        assertThat(cache.AddPollToCache(poll2)).isTrue();
        assertThat(cache.GetPollFromCache(poll2.getId())).isNotNull();
        assertThat(cache.GetPollFromCache(poll1.getId())).isNotNull();

        assertThat(cache.GetPollFromCache(poll1.getId())).isEqualTo(poll1);
        assertThat(cache.GetPollFromCache(poll2.getId())).isEqualTo(poll2);
        assertThat(cache.GetPollFromCache(poll1.getId())).isNotEqualTo(poll2);


        //04 Now user vote to poll1
        //So we need to invalidate it

        assertThat(cache.InvalidatePollFromCache(poll1)).isTrue();
        assertThat(cache.GetPollFromCache(poll1.getId())).isNull();
        assertThat(cache.GetPollFromCache(poll2.getId())).isNotNull();


        TimeUnit.SECONDS.sleep(60);

        assertThat(cache.GetPollFromCache(poll1.getId())).isNull();
        assertThat(cache.GetPollFromCache(poll2.getId())).isNull();
    }

}
