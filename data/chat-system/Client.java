import java.io.*;
import java.net.*;
import java.util.Scanner;

/*
    Program Descrption: A simple chat system
    Date Created: March 19, 2019
*/

public class Client {

    public static void main (String [] args) {

        Socket clientSocket = null;
        Scanner userInput = new Scanner (System.in);
        String ipNumber = "127.0.0.1";
        
        System.out.print ("Enter a Socket Number you want to use->");
        
        int serverNum = userInput.nextInt ();
        
        System.out.println ("");
        
        boolean end = false;
        String value;
        
        try {
        
            //Create the sockets
            ServerSocket serverSocket = new ServerSocket (serverNum);
            ClientListener listiner = new ClientListener (serverSocket);
        
            //Spawn a new thread to listen to messages from the other person
            Thread handlerThread = new Thread (listiner);
            handlerThread.start ();
        
            System.out.print ("Enter a Socket Number to connect to->");
        
            int socketNum = userInput.nextInt ();
        
            System.out.println ("");
        
            clientSocket = new Socket (ipNumber, socketNum);
            //Create a socket to write to
        
            PrintWriter out = new PrintWriter (clientSocket.getOutputStream ());
        
            //Keep writing to the output socket until the user exits the program
            while (! end) {
        
                System.out.print ("Enter what you want to say to your friend. EXIT to quit.-->");
                value = userInput.next ();
        
                System.out.println ("");
        
                if (value.compareTo ("EXIT") == 0) {
                    end = true;
                } else {
        
                    out.println (value);
                    out.flush ();
        
                }
            }
        
            clientSocket.close ();
            serverSocket.close ();

        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}

