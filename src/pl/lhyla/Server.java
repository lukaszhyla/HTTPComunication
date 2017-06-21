package pl.lhyla;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            final ServerSocket serverSocket = new ServerSocket(9000);
            while (true) {
                final Socket accept = serverSocket.accept();
                final Thread thread = new Connection(accept);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Connection extends Thread {
    private Socket socket;

    public Connection(Socket accept) {
        socket = accept;
    }

    @Override
    public void run() {
        final InputStream inputStream;
        try {
            inputStream = socket.getInputStream();
            final OutputStream outputStream = socket.getOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String s = null;
            int state = 1;

            while ((s = bufferedReader.readLine()) != null) {
                System.out.println("State: " + state);
                System.out.println(s);
                if (state == 1 && s.equalsIgnoreCase("Hi")) {
                    dataOutputStream.writeBytes("Hi\r");
                    state++;
                } else if (state == 2 && s.equalsIgnoreCase("Send")) {
                    dataOutputStream.writeBytes("Ok\r");
                    state++;
                } else if (state == 3) {
                    dataOutputStream.writeBytes("Ok\r");
                    state++;
                } else if (state == 4) {
                    dataOutputStream.writeBytes("OK\r");
                    state++;
                } else if (state == 5) {
                    bufferedReader.close();
                    dataOutputStream.close();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}