package model.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
<<<<<<< HEAD
 * Klassen ServerLogger hanterar LogMessage objekt som sparas och skrivs till en lokalfil på servern.
=======
 *
>>>>>>> ce14a2c0d5ff178a48f864e1a9333ba47d3cd208
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
public class ServerLogger{

    private static ArrayList<LogMessages> logList = new ArrayList<LogMessages>();
    private String fileName = "files/server_log.txt";
    private static PrintWriter writer;


    public ServerLogger() {
        try {
            writer = new PrintWriter((fileName), "UTF-8");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
<<<<<<< HEAD
     * Går igenom loggfilen och retunerar texten inom intervallet som användaren anger.
=======
     *
>>>>>>> ce14a2c0d5ff178a48f864e1a9333ba47d3cd208
     * @param to Slutdatumet man vill sluta söka ifrån
     * @param from Startdatumet man vill Börja söka ifrån
     * @return retunerar meddelandet från loggfilen.
     */
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

    /**
<<<<<<< HEAD
     * Sparar ner Message objekt och kallar sennare på metoden för att spara hela listen till en lokal logfil på servern.
=======
     *
>>>>>>> ce14a2c0d5ff178a48f864e1a9333ba47d3cd208
     * @param message Meddelandet som skall sparas ner till serverns loggfil.
     */
    public void log(String message) {
        LogMessages logMessage = new LogMessages(message);
        logList.add(logMessage);
        saveToLogFile();
    }

    /**
<<<<<<< HEAD
     *
=======
>>>>>>> ce14a2c0d5ff178a48f864e1a9333ba47d3cd208
     * Skriver ner till en loggfil som sparas lokalt på servern.
     */
    private void saveToLogFile() {
        writer.write(logList.get(logList.size()-1).toString() + "\n");
        writer.flush();
    }
}

