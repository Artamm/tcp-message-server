package com.artamm.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentMap;

import static com.artamm.command.CommandUtils.generateDataJson;

public class CommandService {
    private static final Logger logger = LoggerFactory.getLogger(CommandService.class);

    public static Status execute(String text, ConcurrentMap<String, Object> data, PrintWriter out) {
        if (text == null || text.isBlank()) {
            logger.warn("Empty command");
            return Status.FAILED;
        }
        Command command = CommandUtils.createCommand(text);
        if (command == null) {
            logger.error("Invalid command format: {} ", text);
            return Status.FAILED;
        }
        return execute(command, data, out);
    }

    private static Status execute(Command command, ConcurrentMap<String, Object> data, PrintWriter out) {
        return switch (command.task()) {
            case END -> Status.END;
            case LIST -> {
                out.println(generateDataJson(data));
                yield Status.COMPLETED;
            }
            case ADD -> {
                data.putIfAbsent(command.key(), command.value());
                yield Status.COMPLETED;
            }
            case SET -> {
                data.replace(command.key(), command.value());
                yield Status.COMPLETED;
            }
            case DELETE -> {
                data.remove(command.key());
                yield Status.COMPLETED;
            }
        };
    }
}
