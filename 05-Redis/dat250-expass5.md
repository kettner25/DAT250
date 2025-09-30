

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