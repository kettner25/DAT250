## Redis

- Created by Lubos Kettner
- In course DAT250

### Intro & description

Subject of this exercise was to try using redis NO-SQL database.
I chosed to work with redis in combination with docker. 

```
    docker run -d --name redis -p 6379:6379 redis:8.2.0
    docker exec -it redis redis-cli
```

The work-flow there was quite easy and smooth. I was working 
in despite of tutorial on Docker webpages.

Once redis was running it was time to try first cmds with it.
I worked through Use cases as descripted below.

### Use case 1

##### Initial state: no user is logged in

When set is empty we does not have to do anything
Just name ourselfs a name for it as **logged**

We can check our set by typing 

```redis
    smembers logged
```

##### User "alice" logs in

```redis
    sadd logged alice
```

##### User "bob" logs in

```redis
    sadd logged bob
```

##### User "alice" logs off

```redis
    srem logged alice
```

##### User "eve" logs in

```redis
    sadd logged eve
```

##### Whole queue

```
    127.0.0.1:6379> SMembers logged
    (empty array)
    127.0.0.1:6379> sadd logged alice
    (integer) 1
    127.0.0.1:6379> sadd logged bob
    (integer) 1
    127.0.0.1:6379> srem logged alice
    (integer) 1
    127.0.0.1:6379> smembers logged
    1) "bob"
    127.0.0.1:6379> sadd logged eve
    (integer) 1
    127.0.0.1:6379> smembers logged
    1) "bob"
    2) "eve"
    127.0.0.1:6379> srem logged bob eve
    (integer) 2
    127.0.0.1:6379> smembers logged
    (empty array)
    127.0.0.1:6379>
```

### Use case 2

Since a json datatype is presented in recent version of Redis we can use
it for storing our data.

We will use an ID of poll (that is unique by def.) as a **key** and poll **value** stored in json.

To do so we will use thoose commands

```
    JSON.SET poll:{id} $ '{json}'

    JSON.GET poll:{id}

    JSON.DEL poll:{id}
```

We would store our poll with command

```
    json.set poll:03ebcb7b-bd69-440b-924e-f5b7d664af7b $ '{ "id": "03ebcb7b-bd69-440b-924e-f5b7d664af7b", "title": "Pineapple on Pizza?", "options": [ { "caption": "Yes, yammy!", "voteCount": 269 }, { "caption": "Mamma mia, nooooo!", "voteCount": 268 }, { "caption": "I do not really care ...", "voteCount": 42 } ] }'
```

Now we can access individual fields with command 

```
    JSON.GET poll:03ebcb7b-bd69-440b-924e-f5b7d664af7b $.{field}
```

For example for getting first option

```
    JSON.GET poll:03ebcb7b-bd69-440b-924e-f5b7d664af7b $.options[0]
```

Now we are ready for incrementing by using command **JSON.NUMINCRBY**

```
    JSON.NUMINCRBY poll:03ebcb7b-bd69-440b-924e-f5b7d664af7b $.options[0].voteCount 1
```

### Jedis

When using Jedis this is code considering redo Use Case 01 and 02:

I created easy spring app, and used main func to run my jedis code.

```java
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
```

This is what I got in output.

```java
    //Out as followed
    //[bob, eve]
    //[269]
    //[270]
```

### Cache

For cache I created service class that would manage redis (resp. Jedis).

Into cache I do store json Poll object as presented above.

I chosed that TTL for items in my cache would be 60 sec. Ideal for dynamic
work with data with smaller percentage of collisions ... and invalidating votes.

My cache can do 3 main funcs. Adding poll, finding poll by its ID and Invalidating poll.

[Link](https://github.com/kettner25/DAT250/blob/main/05-Redis/demo/src/main/java/com/example/demo/VoteCntCache.java)

I also created a tests that test a redis connection in described logic.

```
1- The client checks whether the poll is cached
2. If "yes", the numbers are returned at once and a response is send
3. otherwise, the client has to contact the database (probably via JPA) and aggregate the numbers himself
4. before returning, the client can put the current state of the poll into the cache such that subsequent calls my be faster.
```

### Problems

I was not facing any significant technical problems. The only think
I am not sure about is if I understood assignment with cache properly.

Hopefully I did and presented work will be to your satisfaction.


