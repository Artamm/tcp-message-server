package com.artamm.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CommandUtils {
    private static final Logger logger = LoggerFactory.getLogger(CommandUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private CommandUtils() {
    }

    public static Command createCommand(String text) {
        String[] parts = text.split(" ", 3);
        if (parts.length < 1) {
            return null;
        }

        Command.Task task = Command.Task.find(parts[0]);
        String key = parts.length > 1 ? parts[1] : null;
        String value = parts.length > 2 ? parts[2] : null;
        if (task == null || !isValidCommand(task, key, value)) {
            return null;
        }

        return new Command(task, key, value);
    }

    private static boolean isValidCommand(Command.Task task, String key, String value) {
        return switch (task) {
            case ADD, SET -> key != null && value != null;
            case DELETE -> key != null;
            case LIST, END -> key == null && value == null;
        };
    }

    public static String generateDataJson(Map<String, Object> data) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            logger.error("Unable to parse JSON response: {}", e.getMessage());
            return "{}";
        }
    }
}
