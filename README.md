# Coupon Generation System

## Installation

### Regular installation
```shell
$ mvn clean install
```

### Installation skipping tests
```shell
$ mvn clean install -DskipTests=true
```

## Running

### With Docker
```shell
$ docker buildx build --platform linux/amd64 -t coupon-generation-system:latest .
```

### With Docker Compose
```shell
$ docker compose up -d
```