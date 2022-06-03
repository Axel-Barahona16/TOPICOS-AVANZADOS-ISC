package EJERCICIO2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket c = new Socket("192.168.43.23", 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
        BufferedReader internal_read = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(c.getOutputStream());
        String answer = "";
        do {
            System.out.println("Sending message from localhost");
            String line = internal_read.readLine();

            out.println(line);
            out.flush();
            answer = in.readLine();
            System.out.print("Ya me contestaron: " + answer);
        } while (!answer.equals("Bye"));
        
        c.close();
    }
}