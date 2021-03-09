package model.server;

import java.time.LocalDateTime;

public class LogMessages {

    private LocalDateTime dateTime;
    private String message;

    public LogMessages(String message) {
        this.message = message;
        LocalDateTime date = LocalDateTime.now();
        this.dateTime = date;
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public String toString() {
        return  dateTime.toString() + " - " + message;
    }
}
