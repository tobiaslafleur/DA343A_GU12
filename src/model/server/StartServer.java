package model.server;

/**
 * Klassen StartServer används endast för att starta en ny server
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
public class StartServer {
    /**
     * Starter en server på den inmatade porten
     * @param args
     */
    public static void main(String[] args) {
        new Server(2345);
    }
}
