

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

//import java.util.LinkedList;
import javax.swing.JOptionPane;

public class Cliente extends Thread {
    private Socket socket;   
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;      
    private final VentanaCliente ventana;
    private String identificador;
    private boolean escuchando;
    private final String host;
    private final int puerto;
    
    Cliente(VentanaCliente ventana, String host, Integer puerto, String nombre) {
        this.ventana=ventana;        
        this.host=host;
        this.puerto=puerto;
        this.identificador=nombre;
        escuchando=true;
        this.start();
    }

    public void run(){
        try {
            socket=new Socket(host, puerto);
            objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectInputStream=new ObjectInputStream(socket.getInputStream());
            System.out.println("Conexion exitosa!!!!");
            this.enviarSolicitudConexion(identificador);
            this.escuchar();
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(ventana, "Conexión rehusada, servidor desconocido,\n"
                                                 + "puede que haya ingresado una ip incorrecta\n"
                                                 + "o que el servidor no este corriendo.\n"
                                                 + "Esta aplicación se cerrará.");
            System.exit(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(ventana, "Conexión rehusada, error de Entrada/Salida,\n"
                                                 + "puede que haya ingresado una ip o un puerto\n"
                                                 + "incorrecto, o que el servidor no este corriendo.\n"
                                                 + "Esta aplicación se cerrará.");
            System.exit(0);
        }
    }

    public void desconectar(){
        try {
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();  
            escuchando=false;
        } catch (Exception e) {
            System.err.println("Error al cerrar los elementos de comunicación del cliente.");
        }
    }
 
    public void enviarMensaje(String mensaje){
        ArrayList<String> lista=new ArrayList<>();
        lista.add("MENSAJE");
        lista.add(identificador);
        lista.add(mensaje);
        try {
            objectOutputStream.writeObject(lista);
        } catch (IOException ex) {
            System.out.println("Error de lectura y escritura al enviar mensaje al servidor.");
        }
    }

    public void enviarFactorial(String mensaje){
        ArrayList<String> lista = new ArrayList<>();
        lista.add("FACTORIAL");
        lista.add(identificador);
        lista.add(mensaje);
        try {
            objectOutputStream.writeObject(lista);
        } catch (IOException ex) {
            System.out.println("Error de lectura y escritura al enviar el mensaje al servidor");
        }
    }
  
    public void escuchar() {
        try {
            while (escuchando) {
                Object aux = objectInputStream.readObject();
                if (aux != null) {
                    if (aux instanceof ArrayList) {
                        ejecutar((ArrayList)aux);
                    } else {
                        System.err.println("Se recibió un Objeto desconocido a través del socket");
                    }
                } else {
                    System.err.println("Se recibió un null a través del socket");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana, "La comunicación con el servidor se ha\n"
                                                 + "perdido, este chat tendrá que finalizar.\n"
                                                 + "Esta aplicación se cerrará.");
            System.exit(0);
        }
    }

    public void ejecutar(ArrayList<String> lista){
        String tipo=lista.get(0);
        switch (tipo) {
            case "CONEXION_ACEPTADA":
                identificador=lista.get(1);
                ventana.sesionIniciada(identificador);
                for(int i=2;i<lista.size();i++){
                ventana.addContacto(lista.get(i));
                }
                break;
            case "NUEVO_USUARIO_CONECTADO":
                ventana.addContacto(lista.get(1));
                break;
            case "USUARIO_DESCONECTADO":
                ventana.eliminarContacto(lista.get(1));
                break;   
            case "MENSAJE":
                ventana.addMensaje(lista.get(1), lista.get(2));
                break;
            case "OPERACION":
                multiplicar(lista);
                break;
            case "RESULTADO":
                ventana.addMensaje("El factorial es: ", ""+lista.get(1));
                break;
            default:
                break;
        }
    }
    
    private void enviarSolicitudConexion(String identificador) {
        ArrayList<String> lista=new ArrayList<>();
        lista.add("SOLICITUD_CONEXION");
        lista.add(identificador);
        try {
            objectOutputStream.writeObject(lista);
        } catch (IOException ex) {
            System.out.println("Error de lectura y escritura al enviar mensaje al servidor.");
        }
    }

    void confirmarDesconexion() {
        ArrayList<String> lista=new ArrayList<>();
        lista.add("SOLICITUD_DESCONEXION");
        lista.add(identificador);
        try {
            objectOutputStream.writeObject(lista);
        } catch (IOException ex) {
            System.out.println("Error de lectura y escritura al enviar mensaje al servidor.");
        }
    }

    String getIdentificador() {
        return identificador;
    }

    private void regresarOperacion(BigInteger m){
        ArrayList<String>lista = new ArrayList<>();
        lista.add("OPERACION DEVUELTA");
        lista.add(String.valueOf(m));
        try{
            objectOutputStream.writeObject(lista);
        }catch(IOException ex){
            System.out.println("Error de lectura y escritura");
        }
    }

    private void multiplicar(ArrayList<String>numeros){
        BigInteger multiplicacion = BigInteger.ONE;
        for(int i = 1; i<numeros.size();i++){
            multiplicacion = multiplicacion.multiply(new BigInteger(numeros.get(i)));
        }
        ventana.addMensaje("Mi operacion", multiplicacion.toString());
        regresarOperacion(multiplicacion);
    }
}