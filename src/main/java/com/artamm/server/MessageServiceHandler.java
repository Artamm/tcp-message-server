package com.artamm.server;

import com.artamm.command.CommandService;
import com.artamm.command.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentMap;

public class MessageServiceHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceHandler.class);
    private final Socket clientSocket;
    private final ConcurrentMap<String, Object> data;

    public MessageServiceHandler(Socket socket, ConcurrentMap<String, Object> data) {
        this.clientSocket = socket;
        this.data = data;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String inputLine;
            loop:
            while ((inputLine = in.readLine()) != null) {
                Status result = CommandService.execute(inputLine, data, out);
                switch (result) {
                    case FAILED -> out.println("Invalid or illegal command.");
                    case COMPLETED -> out.println("Executed.");
                    case END -> {
                        out.println("Bye.");
                        out.println("END");
                        break loop;
                    }
                }
                out.println("END");
            }
        } catch (IOException e) {
            logger.error("I/O exception in MessageServiceHandler: {}", e.getMessage());
        }

        closeSocket();
    }

    private void closeSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            logger.error("Unable to close socket: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
