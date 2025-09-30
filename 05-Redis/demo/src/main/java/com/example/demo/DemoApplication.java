package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.json.Path;
import redis.clients.jedis.json.Path2;

import java.io.Console;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");

        //01
        jedis.sadd("logged", "alice");
        jedis.sadd("logged", "bob");
        jedis.srem("logged", "alice");
        jedis.sadd("logged", "eve");
        System.out.println(jedis.smembers("logged"));

        //02

        jedis.jsonSet("poll:03ebcb7b-bd69-440b-924e-f5b7d664af7b", "{\n" +
                "    \"id\": \"03ebcb7b-bd69-440b-924e-f5b7d664af7b\",\n" +
                "    \"title\": \"Pineapple on Pizza?\",\n" +
                "    \"options\": [\n" +
                "        {\n" +
                "            \"caption\": \"Yes, yammy!\",\n" +
                "            \"voteCount\": 269\n" +
                "        },\n" +
                "        {\n" +
                "            \"caption\": \"Mamma mia, nooooo!\",\n" +
                "            \"voteCount\": 268\n" +
                "        },\n" +
                "        {\n" +
                "            \"caption\": \"I do not really care ...\",\n" +
                "            \"voteCount\": 42\n" +
                "        }\n" +
                "    ]\n" +
                "}");

        int index = 0;
        String path = "$.options["+ index +"].voteCount";
        System.out.println(jedis.jsonGet("poll:03ebcb7b-bd69-440b-924e-f5b7d664af7b", Path2.of(path)));

        jedis.jsonNumIncrBy("poll:03ebcb7b-bd69-440b-924e-f5b7d664af7b", Path2.of(path), 1);

        System.out.println(jedis.jsonGet("poll:03ebcb7b-bd69-440b-924e-f5b7d664af7b", Path2.of(path)));
        jedis.close();


        SpringApplication.run(DemoApplication.class, args);
    }

}
