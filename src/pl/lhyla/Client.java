package pl.lhyla;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {

        try {
            String toSend = "I`m learning Java!";
            Socket socket = new Socket("127.0.0.1", 9000);
            final InputStream inputStream = socket.getInputStream();
            final OutputStream outputStream = socket.getOutputStream();

            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes("Hi\r");
            String s = null;

            int state = 1;
            while ((s = bufferedReader.readLine()) != null) {
                System.out.println("State: " + state);
                System.out.println(s);

                if (state == 1 && s.equalsIgnoreCase("Hi")) {
                    dataOutputStream.writeBytes("Send\r");
                    state++;
                } else if (state == 2 && s.equalsIgnoreCase("OK")) {
                    dataOutputStream.writeBytes(toSend.getBytes().length + "\r");
                    state++;
                } else if (state == 3 && s.equalsIgnoreCase("Ok")) {
                    dataOutputStream.writeBytes(toSend);
                    state++;
                } else if (state == 4 && s.equalsIgnoreCase("Ok")) {
                    dataOutputStream.writeBytes("\r\r");
                    state++;
                } else {
                    bufferedReader.close();
                    dataOutputStream.close();
                    break;
                }
            }
            //    dataOutputStream.flush();
            //            Path path = Paths.get(new File("hibernate.cfg.xml").toURI());
            //            final byte[] bytes = Files.readAllBytes(path);
            //
            //            dataOutputStream.write(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
