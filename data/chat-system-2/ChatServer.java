import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class ChatServer extends Thread {

    // The connection to the client
    private Socket socket;

    // Input/Output streams
    public BufferedReader input;
    public PrintWriter output;

    // The current chat lobby
    public String currLobby;

    // Used to determine if the server is closed
    private boolean closed;
    
    // Init the server
    public ChatServer (Socket socket) {
    
        this.socket = socket;
    
        try {
    
            input = new BufferedReader (new InputStreamReader (socket.getInputStream ()));
            output = new PrintWriter (new OutputStreamWriter (socket.getOutputStream ()), true);
            closed = false;
    
        } catch (Exception e) {
    
            System.err.println (e);
            closed = true;
    
        }
    }

    // Main execution
    public void run () {
        
        try {
        
            // Get the user input
            String userInput = "";
        
            // Display any errors that the user names
            String error = "";
        
            while (! userInput.equals ("exit")) {
        
                // Send the list of commands over
                sendMessage (error + "The available commands are:\n" + "- join *servername*\n" + "- create *servername*\n" + "- exit\n" + "Please enter a command:");
        
                // Wait for the user's response
                userInput = getMessage ();
                System.out.println (userInput);
        
                // Get what the commmand was
                String command = userInput.split (" ") [0];
                if (userInput.split (" ") [0].length () > 1) {
        
                    switch (command) {
        
                        case "create" :
        
                            // Get the requested lobby name
                            String reqServerName = userInput.split (" ") [1];
            
                            // Check if the lobby exists already
                            if (! Server.lobbies.keySet ().contains (reqServerName)) {
            
                                // Create the chat array and the lock to it
                                ArrayList < String > tmp = new ArrayList < > ();
                                Semaphore lock = new Semaphore (1);
            
                                // Place the chat array and the mutex in their respective arrays
                                Server.lobbies.put (reqServerName, tmp);
                                Server.lobby_mutexes.put (reqServerName, lock);
            
                            } else {
            
                                error = "ERR: Lobby already exists\n";
                                break;
            
                            }
            
                        case "join" :
        
                        // Get the requested lobby name
                            if (Server.lobbies.keySet ().contains (userInput.split (" ") [1])) {
        
                                // Set as the current lobby, and join it
                                currLobby = userInput.split (" ") [1];
                                joinLobby ();
        
                            } else {
                                error = "ERR: Lobby doesn't exist\n";
                            }
        
                            break;
                    }
        
                } else {
                    error = "ERR: Invalid Command\n";
                }
            }
        
            // Close the socket on end
            socket.close ();
        
        } catch (Exception e) {
        
            closed = true;
            System.err.println (e);
        
        }
    }

    // Joins a specific lobby
    public void joinLobby () throws Exception {
    
        // Get the input from the user
        String userInput = "";
    
        while (! userInput.equals ("/exit")) {
    
            // Get the mutex and display the current chat
            Server.lobby_mutexes.get (currLobby).acquire ();
            String chat = "";
            Integer startPosition = Server.lobbies.get (currLobby).size () - 39;
    
            if (startPosition < 0) {
                startPosition = 0;
            }

            for (int i = startPosition; i < Server.lobbies.get (currLobby).size (); i ++) {
                chat += Server.lobbies.get (currLobby).get (i) + '\n';
            }

            // Release the mutex and send the message
            Server.lobby_mutexes.get (currLobby).release ();
            
            if (output != null) {
                sendMessage (chat);
            }
            
            // Get the users message
            // TODO (Probably wont do it for a lab): Allow new messages to come in while waiting for input
            userInput = getMessage ();
            
            // Get the mutex and post the message
            if (! userInput.equals ("/exit")) {
            
                Server.lobby_mutexes.get (currLobby).acquire ();
                Server.lobbies.get (currLobby).add (userInput);
                Server.lobby_mutexes.get (currLobby).release ();
            
            }
        }
    }

    // Send the message and the end string
    public void sendMessage (String message) {

        output.println (message);
        output.println ("!~~END~~!");

    }

    // Read all lines from the client, stopping at the "!~~END~~!" string
    public String getMessage () {

        String message = "";
        
        try {
        
            String line;
        
            while ((line = input.readLine ()) != null) {
        
                if (line.equals ("!~~END~~!")) {
                    break;
                }
        
                message += line;
        
            }
        
        } catch (Exception e) {
        
            closed = true;
            System.err.println (e);
        
        }
        
        return message;
    }

    // Check if the client disconnected
    public boolean isClosed () {
        
        if (closed == true) {
            return closed;
        } else {
            return socket.isClosed ();
        }
    }
}