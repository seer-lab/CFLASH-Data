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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Server {

    // Port to connect to
    private static int port = 8080;

    // The lobbies containing string arrays
    public static Map <String, ArrayList <String>> lobbies;

    // The mutexes for the lobbies
    public static Map <String, Semaphore> lobby_mutexes;

    public static void main (String [] args) {

        try {

            // Host on the port
            ServerSocket serverSocket = new ServerSocket (port);

            // Init all the servers and the lobbies
            ArrayList <ChatServer> servers = new ArrayList <> ();
            lobbies = new HashMap <> ();
            lobby_mutexes = new HashMap <> ();

            while (true) {

                // Remove any servers that have lost their clients
                freeClients (servers);

                // Wait for a new client and start up 
                ChatServer connection = new ChatServer (serverSocket.accept ());
                connection.start ();

                // Add the server to the arrays
                servers.add (connection);
            }

        } catch (Exception e) {
            System.err.println (e);
        }
    }

    // Check if a server is dead, if it is remove it from the array
    public static void freeClients (ArrayList <ChatServer> clients) {
        
        for (int i = 0; i < clients.size ();) {
            
            ChatServer client = clients.get (i);
            
            if (client.isClosed ()) {
                clients.remove (i);
            } else {
                i ++;
            }
        }
    }
}