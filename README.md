## HOW TO RUN

Run docker-compose file, the application will start at `http://localhost:8080/`

```bash
docker compose up -d
```

If you want to create only the containers your application needs, without creating an application container,  
you can run the command:

```bash
docker compose up -d --scale app=0
```

Now you need to create the topic that you want to consume messages using the Kafka UI in `http://localhost:28080/`

After that, you can produce some messages to this topic using the dashboard of the Kafka UI or you can 
implement yourself a kafka producer.

## Consuming messages partially

In this endpoint it's possible to consume messages in batches, you just need to input the messages quantity
and topic name. 

Execute the cURL:

```bash
curl -X POST 'localhost:8080/consume?topicName=TOPIC_NAME?qtd=QUANTITY'
```

## Consuming all messages

Here you need to remember the default config of `max.poll.records`. If you not configure this in the
consumer configuration or in the .yaml file, the value will be **500**. 

Execute the cURL:

```bash
curl -X POST 'localhost:8080/consume/all?topicName=PUT_THE_TOPIC_NAME_HERE'
```

