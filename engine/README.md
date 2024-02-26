# Docker

## Create local image:
1. Build project
2. Run command: 
```
    docker build -t calculator-engine:<<version>> .
```

## Run container (background and expose port 8080)
```
    docker run -d -p 8080:8080 calculator-engine:<<version>>
```

## Check logs
1. Get container id:
```
    docker ps
```
2. Logs:
```
    docker logs <<CONTAINER ID>>
```

## Remove container
```
    docker rm <<CONTAINER ID>>
```