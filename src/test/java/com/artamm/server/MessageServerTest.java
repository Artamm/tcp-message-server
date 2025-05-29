package com.artamm.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MessageServerTest {
    @Test
    void testServerStartup() throws IOException {
        assertDoesNotThrow(() -> new MessageServer().start(9090));
    }
}
