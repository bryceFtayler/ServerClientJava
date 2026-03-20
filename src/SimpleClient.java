import java.net.*;
import java.util.Scanner;
import java.io.*;

/**
 * A simple client class
 * 
 * @author Bryce Tayler
 */
public class SimpleClient {
    private static final String localhost = "127.0.0.1";
    private Socket socket;
    private Scanner in;
    private DataOutputStream out;
    private static final int PORT = 1234;
    private static final String STOP_STRING = "##";

    /**
     * Constructor
     */
    public SimpleClient() {
        clientMessage(
                "Client started!\n\tAttempting to connect to server on:\n\tIP = \"localhost\"\n\tPORT = " + PORT + "\n");
        try {
            socket = new Socket("localhost", PORT);

            out = new DataOutputStream(socket.getOutputStream());
            in = new Scanner(System.in);
        } catch (Exception e) {
            // TODO: handle exception
            clientError("Error connecting to server!");

            e.printStackTrace();

            System.exit(0);
        }

        clientMessage("-------------------------\n\tSUCCESSFULLY CONNECTED TO SERVER\n\t-------------------------");

        // Begin writing to server
        writeMessages();

    }

    /**
     * Writes messages to the server.
     */
    private void writeMessages() {
        clientMessage("Begin sending messages to the server:\n____________________________________________________________\n");

        try {
            String line = "";

            while (!line.equals(STOP_STRING)) {
                line = in.nextLine();

                out.writeUTF(line);
            }
        } catch (Exception e) {
            // TODO: handle exception
            clientError("Error writing to server!");

            e.printStackTrace();
        }

        clientMessage("Done sending messages to the server!\n____________________________________________________________\n");

        // Close client
        close();
    }

    private void close() {
        clientMessage("Closing client...");

        try {
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            // TODO: handle exception
            clientError("Error closing client!");

            e.printStackTrace();

            System.exit(0);
        }

        clientMessage("Client closed!");
    }

    /**
     * Prints general client messages to console.
     * 
     * @param message - Server message.
     */
    private void clientMessage(String message) {
        System.out.println("[CLIENT MESSAGE]:\n\t"+ message);
    }

    /**
     * Prints client error messages to console.
     * 
     * @param message - Error message.
     */
    private void clientError(String message) {
        System.err.println("[CLIENT ERROR]:\n\t"+ message);
    }

    public static void main(String[] args) {
        new SimpleClient();
    }

}
