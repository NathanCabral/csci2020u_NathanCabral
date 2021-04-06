package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageClient
{
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    private MessageClient(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(this.socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public static MessageClient connect(String host, int port) {
        try {
            return new MessageClient(new Socket(host, port));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public boolean isAlive() {
        return socket.isConnected();
    }

    public void sendMessage(String username, String content)
    {
        out.println(username + ": " + content);
    }
    public void exit()
    {
        out.println("Exit");
        close();
    }


    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        out.close();

        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        System.exit(0);
    }
}
