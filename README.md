## HOW TO RUN

Run docker-compose file, the application will started at `http://localhost:8080/`

```bash
docker compose up -d
```

Create the topic that you want to consume messages using the Kafka UI in `http://localhost:28080/`

After that, you can produce some messages to this topic using the dashboard of the Kafka UI or you can implement yourself a kafka producer.

Now you can consume the messages using the cURL below:

```bash
curl -X POST 'localhost:8080/consume?topicName=PUT_YOUR_TOPIC_NAME_HERE'
```

