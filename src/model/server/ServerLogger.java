package model.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ServerLogger {

    private static ArrayList<LogMessages> logList = new ArrayList<LogMessages>();
    private final String fileName = "files/server_log.txt";
    private static PrintWriter writer;
//    private ServerUI ui;

    public ServerLogger() {
        try {
            writer = new PrintWriter((fileName), "UTF-8");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public static String getLog(LocalDateTime to, LocalDateTime from) {
        String tempMessage = "";
        for (LogMessages messages : logList) {
            LocalDateTime messageDate = messages.getDateTime();
            if ((messageDate.isAfter(from) || messageDate.isEqual(from)) && (messageDate.isBefore(to) || messageDate.isEqual(to))) {
                tempMessage = "(" + messageDate + ")" + messages.getMessage() + "\n";

            }
        }
        return tempMessage;
    }

    public void log(String message) {

        LogMessages logMessage = new LogMessages(message);
        logList.add(logMessage);
        saveToLogFile();
    }

    private void saveToLogFile() {
        for (LogMessages message : logList) {
            writer.write(message.toString() + "\n");
        }
        writer.flush();
    }


}

