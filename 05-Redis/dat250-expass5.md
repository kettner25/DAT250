

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


