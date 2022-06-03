package EJERCICIO2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class MiThread2 extends Thread{
    private String message;

    public MiThread2(String threadname)
    {
        this.message ="Hola mundo desde " + threadname;
    }

    public void run()
    {
        Random r = new Random();
        System.out.println(message);
        int timeToSleep = r.nextInt(1000);
        try {
            this.sleep(timeToSleep);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sleep (int milis) throws InterruptedException{
        Thread.sleep(milis);
    }
    
    public static void main(String[] args) throws IOException {
        //MiThread2 t1 = new MiThread2("t1");
        //MiThread2 t2 = new MiThread2("t2");
        //MiThread2[] t = new MiThread2[10];
        //for (int i = 0; i<t.length; i++){
        //    t[i] = new MiThread2("t"+i);
        //    t[i].start();
        //}

        //t1.start();
        //try {
        //    t1.sleep(500);
        //} catch (InterruptedException iex) {
        //    iex.printStackTrace();
        //}
        //t2.start();

        ServerSocket server = new ServerSocket(5000);

        Socket c = server.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
        PrintWriter out = new PrintWriter(c.getOutputStream());
        //Protocolo Comunicacion: Primero Servidor - Cliente
        String line = "";
        do {
            line = in.readLine();
            System.out.println(line);
            if (line.equals("Bye")) {
                out.println(line);
            } else {
                out.println("Ok desde el servidor: " + line);
            }
            out.flush();
            System.out.println("Ok desde el servidor: " + line);
        } while (!line.equals("Bye"));
        c.close();
        server.close();
    }
}