import java.lang.Thread;
import java.net.*;
import java.io.*;

/*
    Program Descrption: Listens to input from a socket
*/

class ClientListener extends Thread {

    private BufferedReader in;
    private ServerSocket serverSocket;

    ClientListener (ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run () {

        try {
        
            System.out.println ("Listening...");
            Socket clientSocket = serverSocket.accept ();

            //blocks until it receives a connection
            System.out.println ("Connected! ");
            in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream ()));
            
            //Keep listening for a message and then print it out
            while (! clientSocket.isClosed ()) {
            
                System.out.println ("Listening for a message...");
                String output = in.readLine ();
            
                if (null != output) {
                    //Exit it the other person has ended their session
                    System.out.println (output);
                } else {
                    break;
                }
            }
            
            clientSocket.close ();
        
        } catch (IOException e) {
            e.printStackTrace ();
        }
        
    }
}