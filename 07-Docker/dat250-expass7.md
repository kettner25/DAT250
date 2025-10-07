## Docker

- Created by Lubos Kettner
- In course DAT250

### Intro

In this excercise we were supposed to create docker package from our poll application.

I chosed to work with application version from assignment 2 ... compleat REACT and REST
API before experiments with other technilogies (JPA, Redis, RabbbitMQ, ...)

### Work

First I chosed a gradle base image (jdk24-noble-graal).

```
docker build -t demo:latest .
```

```
docker run -it --rm -p 8080:8080 demo:latest
```

```
docker ps
docker stop demo:latest
```