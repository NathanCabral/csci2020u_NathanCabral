package sample;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TreeItem;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;
import java.util.stream.Stream;


public class fileServerClient extends Frame
{
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter networkOut = null;
    private BufferedReader networkIn = null;

    //we can read this from the user too
    public  static String SERVER_ADDRESS = "localhost";
    public  static int    SERVER_PORT = 16789;

    public fileServerClient()
    {
        try
        {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        }
        catch(UnknownHostException e)
        {
            System.err.println("Unknown host: "+SERVER_ADDRESS);
        }
        catch (IOException e)
        {
            System.err.println("IOEXception while connecting to server: "+SERVER_ADDRESS);
        }

        if (socket == null) {
            System.err.println("socket is null");
        }

        try
        {
            networkOut = new PrintWriter(socket.getOutputStream(), true);
            networkIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e)
        {
            System.err.println("IOEXception while opening a read/write connection");
        }

    }

    public String[] DIR()
    {
        networkOut.println("DIR");
        String[] output = null;
        try
        {
            String message = networkIn.readLine();
            output = message.split(",");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return output;
    }
    public void UPLOAD(String fileName)
    {
        networkOut.println("UPLOAD "+fileName);
    }
    public void DOWNLOAD(String fileName)
    {
        networkOut.println("DOWNLOAD "+fileName);
    }
    public String DISPLAY(String fileName,int num)
    {
        networkOut.println("DISPLAY "+fileName+"|"+ Integer.toString(num));
        String value = "";
        try
        {
            value = networkIn.readLine();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return value;
    }
    public void OpenFile(String fileName, TreeItem<String> items)
    {
        if(items.getValue()=="Shared Folder")
        {
            networkOut.println("DOWNLOAD "+fileName);
        }
        try
        {
            ProcessBuilder pb = new ProcessBuilder("Notepad.exe",fileName);
            pb.start();
        }
        catch(IOException e)
        {
            System.err.println("FILE NOT FOUND");
        }
    }
}
