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
        if (task == null) {
            return null;
        }

        String key = parts.length > 1 ? parts[1] : null;
        String value = parts.length > 2 ? parts[2] : null;

        return new Command(task, key, value);
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
