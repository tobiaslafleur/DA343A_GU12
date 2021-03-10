package model.server;


/**
 * Startar servern
 *
 *
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
public class StartServer {
    public static void main(String[] args) {
        new Server(2345);
    }
}
