import java.io.*;
import java.net.*;

/**
 * Simple server class.
 * 
 * @author Bryce Tayler
 */
public class SimpleServer {
    private ServerSocket server;
    private DataInputStream in;
    private DataOutputStream out;
    private static final int PORT = 1234;
    private static final String STOP_STRING = "##";
    // private String serverLog;

    public SimpleServer() {
        try {
            serverMessage("Attempting Server Start...");
            server = new ServerSocket(PORT);

            serverMessage("-----------------\n\tSUCCESSFUL START!\n\t-----------------");

        } catch (Exception e) {
            serverError("FAILED TO START SERVER!");

            e.printStackTrace();

            System.exit(0);

        }

        initConnections();

    }

    /**
     * Get clients to join the server.
     */
    private void initConnections() {
        serverMessage("Waiting for client to connect...");

        try {
            Socket clientSocket = server.accept();

            in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            serverMessage("Client Successfully connected!\n");

        } catch (Exception e) {
            // TODO: handle exception
            serverError("Failed to connect to client!");

            e.printStackTrace();

            System.exit(0);
        }

        // Read messages from client
        readMessages();

        // Close the server
        close();

    }

    /**
     * Close the server.
     */
    private void close() {
        try {
            in.close();
            server.close();

        } catch (Exception e) {
            // TODO: handle exception
            serverError("Failed closing server!\n");
            e.printStackTrace();
        }
    }

    /**
     * Reads messages from client;
     */
    private void readMessages() {
        try {
            String line = "";
            serverMessage("Beginning to read messages from client:\n");

            while (!line.equals(STOP_STRING)) {

                line = in.readUTF();
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints general server messages to console.
     * 
     * @param message - Server message.
     */
    private void serverMessage(String message) {
        System.out.println("[SERVER MESSAGE]:\n\t"+ message);
    }

    /**
     * Prints server error messages to console.
     * 
     * @param message - Error message.
     */
    private void serverError(String message) {
        System.err.println("[SERVER ERROR]:\n\t\""+ message);
    }

    public static void main(String[] args) {
        new SimpleServer();
    }

}
