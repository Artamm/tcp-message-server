package com.artamm.command;

public record Command(Task task, String key, Object value) {
    public enum Task {
        SET, ADD, DELETE, LIST, END;

        public static Task find(String text) {
            if (text == null) return null;
            String task = text.toUpperCase();
            for (Task t : Task.values()) {
                if (t.name().equals(task))
                    return t;
            }
            return null;
        }
    }
}
