# Using Kafka for processing a newly created Order

## Requirements

- You need to implement a POST API with endpoint `/orders` in OrderController
   - This API will take request in form of `OrderEvent` and return `ResponseEntity<String>`
   - You need to throw IllegalArgumentException with message `Price must be greater than zero` if price <= 0
   - You need to throw IllegalArgumentException with message `Quantity must be greater than zero` if quantity <= 0
   - Finally Call OrderService to createOrder and return Http Status 200 with message `Order placed successfully`
- You also need to add ExceptionHandler for IllegalArgumentException which will return HTTP Status - BAD_REQUEST along with Exception Message.
- OrderService will act like KafkaProducer in this case. Implement `createOrder` method present inside OrderService
  - Set Order status as `CREATED` and set order id as some randomly generated UUID.
  - After that add message at topic `order-events` in Kafka.
- You need to add logic in OrderConsumer which is kafkaConsumer
  - `consume` method will be subscribed to kafka at topic `order-events`.
  - GroupId for consumer will be `order-group`
  - Implement `processOrder` method which is called by consume and contains core logic for what need to be done.
     - Check for orderId, if its null, set order status as `FAILED` and throw IllegalArgumentException with message `Received OrderId is null`
     - Change order status to `PROCESSED`
     - Check if order quantity is more than 100, then throw RuntimeException with message `Insufficient inventory`

## Hints
- Nothing is needed from your side in pom.xml or application.properties. Dependencies are already added.
- No new file need to be created.
- If you will try to run testcases without providing solution, all Testcases will fail.
- Please refer KafkaProducerConfig and KafkaConsumerConfig for help if needed.
