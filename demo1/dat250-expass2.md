# Mandatory assigment 01 REST Api

- Created by Luboš Kettner
- Course DAT250

---
### What was done

The main goal of project was to create simple REST API
application such that potential user could connect their 
Voting app to it.

It contains of 4 main models, User, Vote, Poll and VoteOption.

When considering task description we were supposed to save Users and
Polls to HashSet. This was a point where I found myself facing
first problem, since VoteOptions was not depending on Vote neither
Poll and Polls and Votes was depending on it, It would not make
a sense not to store it as well. So I did exactly that and added
one more hashset for it.

Then came a part of IDE setup, since I did that before in previous
assigment I thought there will not be any problems with that, but 
they were. I found that project was not imported well and after
a long 2 hours I decided to create it again, with java 24.
- _Note (for future me): Be careful of dependencies you choose. 
Compiler does not say it to you that you chose poorly!_

Apart from that I decided to use MVC directory struct, since I am
used to it.

There were no big problems with git and implementation. Only problem I
was yet to face was that JsonConverter I used
was not handeling Instant type well. That I solved globally by adding
configuration for it. It was partly suggested to me by compiler and
partly found on internet. But from what I understand it register
JavaTimeModule in converter, so it can be used later by it. And since
we don't want it to convert to timestamp .D we add that other conf.

The last thing that cost me a lots of time was figure why recursion
is used in hashcode D:. Well this I am not used from C# and surprised
me a lot, but apparently you can create inf. recursion only by storing
data to HashSet with loop "pointers".

---

### Last

I hope that the report is to your satisfaction. I dit learned a lot
about java and spring by working on it, even tho it was too much work
for me as a bachelor student for only having one week for it, if I 
can also say some negatives.

I hope I did follow task description as it was designed. I hope it
fits all requirements. 