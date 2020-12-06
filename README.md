Install custom-oauthbearer (https://github.com/dumindarw/kafka-oauthbearer-keycloak) jar file (kafka-oauth-1.0.0.jar) locally and add it to your pom file first

```bash
mvn install:install-file -Dfile=/home/duminda/IdeaProjects/kafka-oauthbearer-keycloak/kafka-oauth-1.0.0.jar -DartifactId=kafka-oauth -Dversion=1.0.0 -DgroupId=drw -Dpackaging=jar
```

Run boot server

View auto generating weather data:

```bash
docker exec -it kafka-oauthbearer-keycloak_kafka_1 bin/kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic weather --from-beginning --consumer.config=config/consumer.properties --consumer-property group.id=group_id
```
or use /weather endpoint

Payload : 
```json
{ "stationId": 1 , "temperature": 10 }
```

