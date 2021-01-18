# Docker commands
## Pull Zookeeper and Kafka images
docker pull confluentinc/cp-zookeeper
docker pull confluentinc/cp-server

## Create docker network
docker network create mynet

## Run Zookeeper in docker 
docker run -d \
--net=mynet \
--name=zookeeper \
-e ZOOKEEPER_CLIENT_PORT=2181 \
-e ZOOKEEPER_TICK_TIME=2000 \
-e ZOOKEEPER_SYNC_LIMIT=2 \
confluentinc/cp-zookeeper
## Run Kafka Broker in docker 
docker run -d \
 --net=mynet \
 --name=broker \
 -p 9092:9092 \
 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
 -e KAFKA_BROKER_ID=1 \
  -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
 confluentinc/cp-kafka
# Run Kafka in CLI       
## List all topics:
docker exec -it broker kafka-topics --list --bootstrap-server localhost:9092

## Create first topic:
docker exec -it broker kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic firstTopic


## Consume messages
docker exec -it broker kafka-console-consumer --bootstrap-server localhost:9092 --topic firstTopic --from-beginning

## Produce messages
docker exec -it broker kafka-console-producer --bootstrap-server localhost:9092 --topic firstTopic

# Example application with Apache Kafka
## Create second topic:
docker exec -it broker kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic secondTopic