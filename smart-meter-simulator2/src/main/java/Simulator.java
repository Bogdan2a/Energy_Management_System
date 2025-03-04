import com.google.gson.Gson;
import com.rabbitmq.client.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Simulator {

    private static final String QUEUE_NAME = "energy_readings_queue"; // RabbitMQ queue name
    private static final String RABBITMQ_URI = "amqps://ftbjhwux:8-N_fP7bHQnViz7F7Z_jmUUbXuj73j0A@kangaroo.rmq.cloudamqp.com/ftbjhwux"; // CloudAMQP URI
    private static final String CONFIG_FILE = "config.json"; // Configuration file with device_id
    private static final String SENSOR_FILE = "sensor.csv"; // File with sensor readings (CSV)

    private static String deviceId;

    public static void main(String[] args) {
        deviceId = loadConfig();

        if (deviceId == null) {
            System.err.println("Device ID not found in config.json.");
            return;
        }

        // init RabbitMQ connection and channel
        try (Connection connection = establishRabbitMQConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null); // Declare the queue

            // process sensor readings and send to RabbitMQ
            processSensorReadings(channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String loadConfig() {
        try (InputStream inputStream = Simulator.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                System.err.println("Config file not found in resources folder.");
                return null;
            }

            // use Gson to parse the JSON into an object
            Gson gson = new Gson();
            Config config = gson.fromJson(new InputStreamReader(inputStream), Config.class);
            return config.device_id;
        } catch (Exception e) {
            System.err.println("Error reading config file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static Connection establishRabbitMQConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(RABBITMQ_URI);
        return factory.newConnection();
    }

    private static void processSensorReadings(Channel channel) throws IOException, InterruptedException {
        // load sensor data from CSV located in resources folder
        try (InputStream inputStream = Simulator.class.getClassLoader().getResourceAsStream(SENSOR_FILE)) {
            if (inputStream == null) {
                System.err.println("Sensor file not found in resources folder.");
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 1) continue; // skip invalid lines

                double measurementValue = Double.parseDouble(data[0]);
                long timestamp = System.currentTimeMillis();

                // create JSON message
                String message = createMessage(measurementValue, timestamp);

                // send the message to RabbitMQ queue
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println("Sent message: " + message);

                // wait for x seconds before sending the next reading
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }

    private static String createMessage(double measurementValue, long timestamp) {
        // create JSON object
        Measurement measurement = new Measurement(timestamp, deviceId, measurementValue);
        Gson gson = new Gson();
        return gson.toJson(measurement);
    }

    // Inner class to represent the config.json
    private static class Config {
        String device_id;
    }

    // Inner class to represent the measurement data
    private static class Measurement {
        long timestamp;
        String device_id;
        double measurement_value;

        public Measurement(long timestamp, String device_id, double measurement_value) {
            this.timestamp = timestamp;
            this.device_id = device_id;
            this.measurement_value = measurement_value;
        }
    }
}
