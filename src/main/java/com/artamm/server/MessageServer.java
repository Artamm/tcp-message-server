package com.artamm.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.*;

public class MessageServer implements AutoCloseable {
    private ServerSocket serverSocket;
    private ConcurrentMap<String, Object> data = new ConcurrentHashMap<>();
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public void start(int port, int timeOut) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(timeOut);
        prepareData();
        while (!serverSocket.isClosed()) {
            executor.execute(new MessageServiceHandler(serverSocket.accept(), data));
        }
    }

    private void prepareData() {
        data.put("name", "Message TCP server");
        data.put("key", "TCP");
        data.put("another", "TCP");
        data.put("number", 42);
    }

    @Override
    public void close() throws Exception {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }

        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
    }

}
