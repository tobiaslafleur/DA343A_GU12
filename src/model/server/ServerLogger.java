package model.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ServerLogger {

    private static ArrayList<LogMessages> logList = new ArrayList<LogMessages>();
    private final String fileName = "files/server_log.txt";
    private static PrintWriter writer;


    public ServerLogger() {
        try {
            writer = new PrintWriter((fileName), "UTF-8");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public static String[] getLog(LocalDateTime to, LocalDateTime from) {
        ArrayList<String> logStrings = new ArrayList<>();
        String returnMessage[];

        for(LogMessages message : logList) {
            LocalDateTime messageTime = message.getDateTime();
            if ((messageTime.isAfter(from) || messageTime.equals(from)) && (messageTime.isBefore(to) || messageTime.equals(to))) {
                logStrings.add(message.toString());
            }
        }

        returnMessage = new String[logStrings.size()];
        for (int i = 0; i < returnMessage.length; i++) {
            returnMessage[i] = logStrings.get(i);
        }

        return returnMessage;
    }

    public void log(String message) {

        LogMessages logMessage = new LogMessages(message);
        logList.add(logMessage);
        saveToLogFile();
    }

    private void saveToLogFile() {
        writer.write(logList.get(logList.size()-1).toString() + "\n");
        writer.flush();
    }


}

