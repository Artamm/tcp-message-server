package com.artamm.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TcpClientTest {

    @Test
    void testTcpClient_withoutServer_ThrowsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            TcpClient.main(new String[]{});
        });
    }
}
