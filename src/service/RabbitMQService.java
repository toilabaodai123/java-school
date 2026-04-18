package service;

import app.Main;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dto.TaskDTO;
import exception.QueueJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.QueueJob;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class RabbitMQService implements QueueJob {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQService.class);
    private static final String EXCHANGE_NAME = "send_added_class_email";
    private static int instanceCounter = 0;

    private Connection connection;
    private Channel channel;

    public RabbitMQService(){
        ++instanceCounter;
        logger.info("RabbitMQService Service Constructor  {}",instanceCounter);
    }

    public void init() throws QueueJobException {
        HashMap<String, String> config = Main.getConfig();
        try {
            String hostString = config.get("task.queue.host");
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(hostString);
            factory.setAutomaticRecoveryEnabled(true);
            factory.setNetworkRecoveryInterval(5000);
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            channel.queueDeclare("email_tasks", true, false, false, null);
            channel.queueBind("email_tasks", EXCHANGE_NAME, "user.#");
            logger.info("RabbitMQ connection established");
        } catch (IOException | TimeoutException e) {
            throw new QueueJobException("Failed to initialize RabbitMQ", e);
        }
    }

    public void shutdown() throws QueueJobException {
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
            logger.info("RabbitMQ connection closed");
        } catch (IOException | TimeoutException e) {
            throw new QueueJobException("Failed to shutdown RabbitMQ", e);
        }
    }

    public void dispatchTask(String taskUUID, TaskDTO taskDTO) throws QueueJobException {
        try {
            String routingKey = "user.send_added_class_email";
            String message = new Gson().toJson(taskDTO);

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            logger.info("Dispatched task {} to RabbitMQ", taskUUID);
        } catch (IOException e) {
            throw new QueueJobException("Failed to dispatch task to RabbitMQ", e);
        }
    }
}

