package model.server;

import java.time.LocalDateTime;

/**
 * Klassen LogMessages skapar objekt av text och den tid
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
public class LogMessages {

    private LocalDateTime dateTime;
    private String message;


    /**
     * LogMessage objekt som skall sparas ner på serverns log.
     * @param message Det meddelandet som skall sparas om vad som händer i systemet
     */
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
