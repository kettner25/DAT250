## Rabbit MQ

- Created by Lubos Kettner
- In course DAT250

### Intro

In this assignment we were supposed to edit our poll app. such that individual voting
event is proceeded by Message protocol.

### Desc.

I decided to solve the problem like this:

1. When new poll is created I add new toppic and queue for it and let my poll app
subscribe to this newly created queue.

2. When voting or unvoting event is send to REST API I post it into RabbitMQ

    - Each Queue for spec. poll is expected to have routing key: poll.{id}
    - Each consumer is expected to create it's own individual queue for each poll
    he want to subscribe to be listening with routing information provided above
    - For communication with server he need to use spec. json struct
    ```java
        {
            "PollID": int,
            "OptID": int,
            "Username": String,
            "Type": Vote/UnVote
        }
    ```

3. Server share all it's own vote events and listen for clients one's

### Testing

Since I updated actions for Voting and Unvoting to be also using RabbitMQ,
my first set of tests was to test basic functionality. That went quite well.

Then I connected to RabbitMQ via web browser.

```
http://localhost:15672/
```

I added another queue with respective routing information simulating another
application connected to the message protocol. I wanted to test my design with
multiple queues. It worked as expected, and my messages for spec. topic
was mirrored to this queue.

Lastly I tried to post a vote and also unvote.
I was sending data below with routing **poll.0** to my excange-topic.

data:
```Json
{
  "pollID": 1,
  "optID": 4,
  "username": "lubos",
  "type" : "Vote"
}
```

My data was updated and after interacting with my frontend REACT app
it was updated also there.

So I feel satisfied with my solution.

### Problems

When working with RabbitMQ there were not any technical issues.

Because my app. was previously designed with different idea in mind, I faced multiple
conceptual challanges. For example since vote id is formed of user and voteopt it would
not be an easy option for me to allow anonymous voting. So I did hold to my original idea
and introduces internal structure you need to fulfill.

Also when designing a message structure I was thinking about using fanout, but since
it dont respect routing keys I decided that my current state with a lots of queues will
be more suitable option for working with spec. topics.

I hope my solution was done to your satisfaction.