## JPA

- Created by Lubos Kettner
- In course DAT250

### Introduction

In this assignment we were supposed to
configure our models from previous assignment
such that we can connect it to the ORM DB
by using JPA.

The result should be correctly configured
models that can pass provided test sets.

### What was done

I managed to configure all models and
inspect the result by using build in 
DB viewer. First I did not want to change
my created model much so I decided to 
configure it as it is. That surely brought
some problems, but at the end I was able to
make it done and tried a compound key and
embedded structure (class) as well.

### Table inspection

For inspecting that database was created
while in memory I firstly wanted to
use a dedicated application for it
(such as MSSQL for MS). 

I configured a SQL H2 DB in main on server,
but it was not working as expected.
Then I figured that H2 has a web UI
that developers can use for inspection
of in memory database. So I configured it 
and inspected by typing

http://localhost:8080/h2-console

I logged in as said in config. by editing URL, and username="sa".
Opened each table and validated side by side
with my own models. It looks fine to me.

##### Configuration:

[Main](https://github.com/kettner25/DAT250/blob/main/demo1/src/main/java/com/example/demo/Demo1Application.java)
````java
EntityManagerFactory emf = new PersistenceConfiguration("polls")
            .managedClass(Poll.class)
            .managedClass(User.class)
            .managedClass(Vote.class)
            .managedClass(VoteOption.class)
            .property(PersistenceConfiguration.JDBC_URL, "jdbc:h2:mem:polls")
            .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, "drop-and-create")
            .property(PersistenceConfiguration.JDBC_USER, "sa")
            .property(PersistenceConfiguration.JDBC_PASSWORD, "")
            .property(JdbcSettings.SHOW_SQL, true)
            .property(JdbcSettings.FORMAT_SQL, true)
            .property(JdbcSettings.HIGHLIGHT_SQL, true)
            .createEntityManagerFactory();

emf.getSchemaManager().create(true);
````

##### And proof:

![alt text](https://raw.githubusercontent.com/kettner25/DAT250/refs/heads/main/04-JPA/Tables.png)
![alt text](https://raw.githubusercontent.com/kettner25/DAT250/refs/heads/main/04-JPA/View.png)

![alt text](https://raw.githubusercontent.com/kettner25/DAT250/refs/heads/main/04-JPA/Test-passing.png)

### Link

Here is a link to my 
##### Models (App):
[App](https://github.com/kettner25/DAT250/tree/main/demo1/src/main/java/com/example/demo)

##### Tests:
[Tests](https://github.com/kettner25/DAT250/tree/main/demo1/src/test/java/com/example/demo/jpa/polls)

### Issues

First issue I encountered was with dependencies
because my IDE did not load "persistence" package
properly. I was not sure why that happened, but 
I resolved by checking loaded dependencies and 
deleting cache and rebuilding dependencies tree.

After that I figured that it automatically added
old persistence package 3.1.0 not having PersistenceConfiguration
in it. So I forced it by adding into dependencies

````gradle
implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
````

Just to find out that my previous try to
render views, misunderstation in HW01
lead now to collision with packages. So I
disabled it by removing previously added conf
in gradle.build and main.


After that I spend a little bit of time
with compound ID, because I know is
quite used in DB world and I did not 
want to add Integer id (that would be too
easy solution).

And in the end I figured that my names
of some columns are named differently than
in Test cases, so I changed that. In queries
I hope I was not supposed changing column 
names by JPA attribute. Although I did that
with some just in case.

````java
//Here I do use username as ID
Integer actual = (Integer) em.createNativeQuery("select count(username) from users", Integer.class).getSingleResult();

//Here votesOn -> option
Long vimVotes = em.createQuery("select count(v) from Vote v join v.option as o join o.poll as p join p.creator u where u.email = :mail and o.presentationOrder = :order", Long.class).setParameter("mail", "alice@online.com").setParameter("order", 0).getSingleResult();
Long emacsVotes = em.createQuery("select count(v) from Vote v join v.option as o join o.poll as p join p.creator u where u.email = :mail and o.presentationOrder = :order", Long.class).setParameter("mail", "alice@online.com").setParameter("order", 1).getSingleResult();

//options -> voteOpts
List<String> poll2Options = em.createQuery("select o.caption from Poll p join p.voteOpts o join p.creator u where u.email = :mail order by o.presentationOrder", String.class).setParameter("mail", "eve@mail.org").getResultList();
````

### Last

I am actually quite happy with a result.
I hope the problem was understood fine,
and solution fits your expectations.