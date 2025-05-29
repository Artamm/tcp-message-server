package com.artamm;

import com.artamm.server.MessageServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class ServerStart {
    private static final Logger logger = LoggerFactory.getLogger(ServerStart.class);

    public static void main(String[] args) {
        int port = 9090;
        try (MessageServer serverSocket = new MessageServer()) {
            logger.info("TCP server established");
            serverSocket.start(port, 30000);
        } catch (IOException e) {
            logger.error("I/O Exception in MessageServer: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error in MessageServer: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
