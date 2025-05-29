package com.artamm.command;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class CommandUtilsTest {

    @Test
    void parseText_setCommand_ReturnsCommand() {
        Command command = CommandUtils.createCommand("SET name TCP MESSAGE TEST");
        assertNotNull(command);
        assertEquals(Command.Task.SET, command.task());
        assertEquals("name", command.key());
        assertEquals("TCP MESSAGE TEST", command.value());
    }

    @Test
    void parseText_singleArgumentCommand_ReturnsCommand() {
        Command command = CommandUtils.createCommand("list");
        assertNotNull(command);
        assertEquals(Command.Task.LIST, command.task());
        assertNull(command.key());
        assertNull(command.value());
    }

    @Test
    void parseText_invalidCommand_returnsNull() {
        Command command = CommandUtils.createCommand("TEST_INVALID");
        assertNull(command);
    }

    @Test
    void generateDataJson_severalEntries_ReturnsJson() {
        ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<>();
        data.put("name", "TCP MESSAGE TEST");
        data.put("number", 42);
        String json = CommandUtils.generateDataJson(data);
        assertTrue(json.contains("TCP MESSAGE TEST"));
        assertTrue(json.contains("\"number\""));
    }
}
