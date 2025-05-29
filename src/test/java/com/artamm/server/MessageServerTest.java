package com.artamm.server;

import org.junit.jupiter.api.Test;

import java.net.SocketTimeoutException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MessageServerTest {
    @Test
    void verify_messageServerTimesOut_throwsException() {
        assertThrows(SocketTimeoutException.class,() -> new MessageServer().start(9090, 3000));
    }
}
