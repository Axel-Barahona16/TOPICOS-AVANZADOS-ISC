package clases;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import javax.swing.JOptionPane;

//Clase en la que se maneja la comunicación del lado del servidor.
public class Servidor extends Thread{
    
    // Socket servidor para escuchar cuando los clientes se conectan e incluirlos en el chat
    private ServerSocket serverSocket;
    
    /*
     * Lista de todos los hilos de comunicación, para cada cliente se instancia uno 
     * de estos hilos ya que cada hilo esta escuchando permanentemente lo que dicho 
     * cliente envía al servidor. 
     */
    LinkedList<HiloCliente> clientes;
    
    // Variable que almacena la ventana que gestiona la interfaz gráfica del servidor.
    private final VentanaS ventana;
    
    // Variable que almacena el puerto que el servidor usará para escuchar. 
    private final String puerto;
    
    //Correlativo para diferenciar a los múltiples clientes que se conectan
    static int correlativo;
    
    public Servidor(String puerto, VentanaS ventana) {
        correlativo=0;
        this.puerto=puerto;
        this.ventana=ventana;
        clientes=new LinkedList<>();
        this.start();
    }
    
    /* Método sobre el que corre el bucle infinito que tiene como función escuchar
     * permenentemente en espera de conexiones de nuevos clientes. */    
    public void run() {
        try {
            serverSocket = new ServerSocket(Integer.valueOf(puerto));
            ventana.addServidorIniciado();
            
            while (true) {
                HiloCliente h;
                Socket socket;
                socket = serverSocket.accept();
                
                agregarLog("------------------------------------------------------------------------ \n");
                h = new HiloCliente(socket, this);               
                h.start();
                agregarLog("Conexion: " + socket + "\n");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana, "El servidor no se ha podido iniciar,\n"
                                                 + "puede que haya ingresado un puerto incorrecto.\n"
                                                 + "Esta aplicación se cerrará.");
            System.exit(0);
        }                
    }        
    
    //Ciclo que devuelve una lista con los identificadores de todos los clientes conectados.
    LinkedList<String> getUsuariosConectados() {
        LinkedList<String>usuariosConectados=new LinkedList<>();
        clientes.stream().forEach(c -> usuariosConectados.add(c.getIdentificador()));
        return usuariosConectados;
    }
    
    // Método que agrega una linea al log de la interfaz gráfica del servidor.
    void agregarLog(String texto) {
        ventana.agregarLinea(texto);
    }
}