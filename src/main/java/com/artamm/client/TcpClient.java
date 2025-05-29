package com.artamm.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClient {
    private static final Logger logger = LoggerFactory.getLogger(TcpClient.class);

    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 9090;
        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        ) {
            logger.info("TCP client established");
            String userInput;
            while ((userInput = console.readLine()) != null) {
                out.println(userInput);
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    if ("END".equals(line)) break;
                    response.append(line).append("\n");
                }
                logger.info("Response from server: \n{}", response);
            }

        } catch (UnknownHostException e) {
            logger.error("Host unknown: {} ", e.getMessage());
        } catch (IOException e) {
            logger.error("I/O Error: {} ", e.getMessage());
            throw new RuntimeException("Connection refused: connect.");
        }
        logger.info("Disconnecting client");

    }
}