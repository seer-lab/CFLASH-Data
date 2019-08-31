import java.io.BufferedReader;
import java.io.DataInput;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Scanner;
public class Client {

    // Address to connect to
    private static String address = "127.0.0.1";
    
    // Port to connect to
    private static int port = 8080;
    
    // The connection to the server
    private static Socket socket;
    
    // Input/Output streams
    public static BufferedReader input;
    public static PrintWriter output;

    public static void main (String [] args) {
    
        try {
            
            // Get the connection to the server
            socket = new Socket (address, port);
            
            // Set up streams including user input
            input = new BufferedReader (new InputStreamReader (socket.getInputStream ()));
            output = new PrintWriter (new OutputStreamWriter (socket.getOutputStream ()), true);
            
            Scanner userInput = new Scanner (System.in);
            String command = "";
    
            while (! command.equals ("exit")) {
    
                // Clear the screen
                System.out.println ("\033[H\033[2J");
                System.out.flush ();
    
                // Display any messages from the server
                System.out.print (getMessage ());
    
                // Get and send any commands
                command = userInput.nextLine ();
                sendMessage (command);
    
            }
    
            // Close any connections on exit
            socket.close ();
    
        } catch (Exception e) {
            System.err.println (e);
        }
    }

    // Read all lines from the client, stopping at the "!~~END~~!" string
    public static String getMessage () {
        
        String message = "";
        
        try {
        
            String line;
        
            while ((line = input.readLine ()) != null) {
        
                if (line.equals ("!~~END~~!")) {
                    break;
                }

                message += line + '\n';
            }

        } catch (Exception e) {
            System.err.println (e);
        }

        return message;
    }

    // Send the message and the end string
    public static void sendMessage (String message) {
        
        output.println (message);
        output.println ("!~~END~~!");
    
    }
}