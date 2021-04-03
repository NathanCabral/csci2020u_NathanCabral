package sample;
import javafx.scene.control.PasswordField;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Pattern;

public class ClientConnectionHandler extends Thread
{
    protected Socket socket       = null;
    protected PrintWriter out     = null;
    protected BufferedReader in   = null;


    public ClientConnectionHandler(Socket socket)
    {
        super();
        this.socket = socket;
        try
        {
            out = new PrintWriter(socket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(IOException e)
        {
            System.err.println("IOEXception while opening a read/write connection");
        }
    }
    public void run()
    {

        boolean endOfSession = false;
        while(!endOfSession) {
            endOfSession = processCommand();
        }
        try {
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    protected boolean processCommand() {
        String message = null;
        try {
            message = in.readLine();
        } catch (IOException e) {
            System.err.println("Error reading command from socket.");
            return true;
        }
        if (message == null) {
            return true;
        }
        StringTokenizer st = new StringTokenizer(message);
        String command = st.nextToken();
        String args = null;
        if (st.hasMoreTokens()) {
            args = message.substring(command.length()+1, message.length());
        }
        return processCommand(command, args);
    }
    protected boolean processCommand(String command, String arguments) {
        // these are the other possible commands
        if (command.equalsIgnoreCase("DIR"))
        {
            File sharedFiles = new File("src\\Shared_Folder");
            String[] files;
            String output = "";
            files = sharedFiles.list();
            for(String file: files)
            {
                output = output + "," + file;
            }
            out.println(output);
            return true;
        }
        else if (command.equalsIgnoreCase("UPLOAD"))
        {
            File newFile = new File(arguments);
            String fileContents ="";
            try
            {
                FileReader reader = new FileReader(newFile);
                int character;

                while ((character = reader.read()) != -1)
                {
                    fileContents += (char) character;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            File destination = new File("src\\Shared_Folder\\"+newFile.getName());
            try
            {
                byte[] b = fileContents.getBytes();
                FileOutputStream outStream = new FileOutputStream(destination);
                outStream.write(b);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

            return true;
        }
        else if (command.equalsIgnoreCase("DOWNLOAD"))
        {
            File destination = new File(arguments);

            File newFile = new File("src\\Shared_Folder\\"+destination.getName());
            String fileContents ="";
            try
            {
                FileReader reader = new FileReader(newFile);
                int character;

                while ((character = reader.read()) != -1)
                {
                    fileContents += (char) character;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                byte[] b = fileContents.getBytes();
                FileOutputStream outStream = new FileOutputStream(destination);
                outStream.write(b);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

            return true;
        }
        else if (command.equalsIgnoreCase("DISPLAY"))
        {
            String input = arguments;
            String[] delim = arguments.split(Pattern.quote("|"));

            File destination = new File(delim[0]);
            File newFile = null;
            if(delim[1].equals("1"))
            {
                newFile = new File("src\\Shared_Folder\\"+destination.getName());
            }
            else if(delim[1].equals("2"))
            {
                newFile = new File("Local_Shared_Folder\\"+destination.getName());
            }

            String line="";
            try
            {
                FileReader reader = new FileReader(newFile);
                BufferedReader br = new BufferedReader(reader);
                StringBuffer sb = new StringBuffer();

                while((line=br.readLine())!= null)
                {
                    sb.append(line);
                    sb.append("\n");
                }
                out.println(sb.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
        else
        {
            out.println("400 Unrecognized Command: "+command);
            return true;
        }
    }

}
