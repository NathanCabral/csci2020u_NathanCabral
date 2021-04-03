package sample;
import java.io.*;
import java.net.*;

public class fileServer
{
    protected Socket clientSocket = null;
    protected ServerSocket serverSocket = null;
    protected ClientConnectionHandler[] threads = null;
    protected int numClients = 0;

    public static int SERVER_PORT = 16789;
    public static int MAX_CLIENTS = 25;

    public fileServer()
    {
        try
        {
            serverSocket = new ServerSocket(SERVER_PORT);
            threads = new ClientConnectionHandler[MAX_CLIENTS];
            while(true) {
                clientSocket = serverSocket.accept();
                System.out.println("Client #"+(numClients+1)+" connected.");
                threads[numClients] = new ClientConnectionHandler(clientSocket);
                threads[numClients].start();
                numClients++;
            }
        }
        catch(IOException e)
        {
            System.err.println("IOException while creating server connection");
        }
    }
    public static void main(String[] args) {
        fileServer server = new fileServer();
    }
}
