# SPA

- Solved by Lubos Kettner
- Framework **REACT**

### Introduction and simple description

In this assignment we were supposed to create single page application
connected to previously created REST API. I chose to work with REST
framework through I had never worked with frontend js framework before.

The SPA should consist of func. for creating new User, new Poll and Voting.
I managed to compleat all of it. For education purpose only the user interface
is designed very simply.

1. We need to create user/s first
2. Then we need to select user we want to be working with
3. Now we can create new poll/s
4. Before creating we need to ensure that at least 2 options were added
5. We can observe polls
6. Voting and unvoting for each option

We can change views by clicking to links in main navigation on top.

### Problems

When working on solution I encountered multiple smaller problems. First
appeared when reading description of problem and realising I understood
problem slightly different in assignment 01. So my REST API was not designed
perfectly to suit the new description. But you don't usually get to change
API, so I go with what I got. And it was not much of the problem after all.

The second problem was with states, I figured that If you want to dynamically
change content of array or object after user input you need to use ReactState.

Apart from that working with REACT was pretty straight forward. And only another
problem I faced was when fetching date from date input to server. Because
Java Instant want you to have ISO formatting.

### Links

Only thing I am not sure about now are the experiments? I don't see any
experiments in description, so I am not sure what do you want from me D:

So I did included links to Components below:

#### User

- [UserComponent](https://github.com/kettner25/DAT250/blob/main/demo1-SPA/src/components/UserComponent.jsx)

Here happens creating, displaying and selecting of Users

````js
"User": {
    "username": String,
    "email": String,

    "created": [Poll],
    "voted": [Vote]
}
````

````js
    export function NewUser({users, setUsers});

    export default function User({users, setUsers, username, setUsername});
````

NewUser is component that create new users.

User on the other hand render all users and handle selection process.

#### Poll

- [PollComponent](https://github.com/kettner25/DAT250/blob/main/demo1-SPA/src/components/PollComponent.jsx)
````js
"Poll":
{
    "question": String,
    "publishedAt": Date,
    "validUntil": Date,
    "voteOpts": [Object]
},
"VoteOpt":
{
    "caption": String,
    "presentationOrder": Number,
    "votes": [Object]
}
````

````js
export default function Polls({polls, votes, setVotes});

export function NewPoll({polls, setPolls});

export function Poll({id, polls, votes, setVotes});

export function Opts({data, votes, setVotes});

export function NewOpt({opts, setOpts});
````

Here Polls component is responsible for rendering all polls for user
and rendering VoteComponents. So you can be able to vote.

NewPoll component does create new Poll by selected user and also 
do add new Options for the Poll

Poll component render one poll in Polls

Opts render options in Polls

NewOpts add new option when creating poll

#### Vote

- [VoteComponent](https://github.com/kettner25/DAT250/blob/main/demo1-SPA/src/components/VoteComponent.jsx)

````js
"Vote": {
    "publishedAt": Date,
    "option": VoteOption
}
````

````js
export default function Vote({ opt, votes, setVotes });
````

Vote component react on user voting for spec. vote

#### App

and here is my App base component

- [App](https://github.com/kettner25/DAT250/blob/main/demo1-SPA/src/App.jsx)

### End

Hopefully I understood the assigment properly. And it is to you satisfaction.
I enjoyed trying finally REACT .D And I feel like it was actually fun.
