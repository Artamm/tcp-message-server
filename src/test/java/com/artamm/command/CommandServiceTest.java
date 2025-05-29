package com.artamm.command;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommandServiceTest {
    private final ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<>();
    private final PrintWriter out = new PrintWriter(System.out, true);

    @Test
    void execute_addCommand_createsEntry() {
        CommandService.execute("ADD name TEST", data, out);
        assertEquals("TEST", data.get("name"));
    }

    @Test
    void execute_setCommand_updatesValue() {
        data.put("number", 897);
        CommandService.execute("set number 324", data, out);
        assertEquals("324", data.get("number"));
    }

    @Test
    void execute_deleteCommand_deletesEntry() {
        data.put("error", "Test");
        CommandService.execute("DELETE error", data, out);
        assertNull(data.get("name"));
    }
}
