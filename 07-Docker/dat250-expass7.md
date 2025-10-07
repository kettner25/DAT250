## Docker

- Created by Lubos Kettner
- In course DAT250

### Intro

In this excercise we were supposed to create docker package from our poll application.

I chosed to work with application version from assignment 2 ... compleat REACT and REST
API before experiments with other technilogies (JPA, Redis, RabbbitMQ, ...)

### Work

First I chosed a eclipse-temurin:24 base image.

Then I followed lectures and asignment description and composed following dockerfile.

```DOCKERFILE
# My base image for java 24
FROM eclipse-temurin:24 AS builder

COPY . .

# Build app and create .jar
RUN ./gradlew clean bootJar --no-daemon

# My lightweight image
FROM openjdk:24-jdk-slim

# Change user rights
RUN useradd -m appuser
USER appuser

# Copy builded app here 
COPY --from=builder ./build/libs/*.jar ./app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

This is my second and lates version, that basically extended the simple one by requirements
- make sure that the app is not run as root user inside the container
- use a multi-stage build to keep your image "slim" and without extra vulnerabilities

Then comes a testing part

#### Build

```Bash
docker build -t demo:latest .
```

#### Run

```Bash
docker run -it --rm -p 8080:8080 demo:latest
```

- -it as interaktive
- --rm for removig and no need for calling every time
    ```Bash
    docker ps
    docker stop {id}
    docker rm {id}
    ```
- -p connecting 8080 port from container to my pc

### Test

I also wanted to make sure everything is running perfectly as expected also inside container.

So I changed my build dockerfile script and added 

```DOCKERFILE
...
# My lightweight image
FROM openjdk:24-jdk-slim

# Only for debbuging
# Switch to root to install debugging packages (curl and net-tools)
USER root
# We are using the Debian package manager (apt-get) for the slim image.
# We run update and install in one layer to minimize final image size.
RUN apt-get update && \
    apt-get install -y curl net-tools iproute2 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*
#-------------------------------------------------------------------------

# Change user rights
RUN useradd -m appuser
USER appuser
...
```

Then builded once again and runned docker containter with flag **-d** for detached mode.

Then typed

```Bash
docker exec -it {container_id} /bin/bash
```

To get into linux image running inside container. 

1. First I figgured I am not root, since my username was like

```BASH
appuser@796b475068fc:/$
```

Then I tested inside connection and network stuff


```BASH
curl http://localhost:8080/
```

```HTML
<!doctype html>

<html lang="en">

  <head>

    <meta charset="UTF-8" />

    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <title>Demo1-SPA</title>

    <script type="module" crossorigin src="/assets/index-CAiwSTWG.js"></script>

    <link rel="stylesheet" crossorigin href="/assets/index-9TNaDxLQ.css">

  </head>

  <body>

    <div id="root"></div>

  </body>

</html>
```

Everything working as it should.