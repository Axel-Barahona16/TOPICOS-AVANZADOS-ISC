

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class VentanaCliente extends JFrame{

    public String mensaje;
    private final String DEFAULT_PORT="1024"; 
    private final String DEFAULT_IP="10.0.0.4";
    private final Cliente cliente;
    private javax.swing.JPanel mypanel1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblPerfil;
    private javax.swing.JPanel mypanel2;
    private javax.swing.JPanel mypanel3;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtHistorial;
    private javax.swing.JTextField txtMensaje;
    private javax.swing.JLabel lblText1;

    VentanaCliente(){
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String ip_puerto_nombre[]=getIP_Puerto_Nombre();
        String ip=ip_puerto_nombre[0];
        String puerto=ip_puerto_nombre[1];
        String nombre=ip_puerto_nombre[2];
        cliente =new Cliente(this, ip, Integer.valueOf(puerto), nombre);
    }

    private void initComponents() {

        this.setLocation(500,20);
        this.setSize(400, 700);
        this.setLayout(new BorderLayout());

        mypanel1 = new JPanel();
        mypanel1.setBackground(Color.RED);
        mypanel1.setLayout(new BorderLayout());

        mypanel2 = new JPanel();
        mypanel2.setBackground(Color.RED);
        mypanel2.setLayout(new GridLayout(1,2));

        lblPerfil = new JLabel();
        lblPerfil.setIcon(new ImageIcon("C:/Users/Jonat/Dropbox/PC/Documents/Cliente-Servidor/Cliente/clases/iconopersonas.png"));
        lblPerfil.setHorizontalAlignment(SwingConstants.CENTER);

        lblTitulo = new JLabel("Chat Grupal", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));

        mypanel2.add(lblPerfil);
        mypanel2.add(lblTitulo);

        jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(txtHistorial);

        txtHistorial = new JTextArea();
        txtHistorial.setBackground(Color.WHITE);
        txtHistorial.setForeground(Color.BLACK);
        txtHistorial.setFont(new Font("Segoe UI", 0, 14));
        txtHistorial.setEditable(false);

        mypanel3 = new JPanel();
        mypanel3.setBackground(Color.RED);
        mypanel3.setLayout(new BorderLayout());

        lblText1 = new JLabel();
        lblText1.setText("Escribe un mensaje para enviar:");
        lblText1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblText1.setBorder(new EmptyBorder(10,10,10,10));
        
        txtMensaje = new JTextField();
        txtMensaje.setColumns(25);

        btnEnviar = new JButton();
        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        mypanel3.add(lblText1, BorderLayout.NORTH);
        mypanel3.add(txtMensaje, BorderLayout.WEST);
        mypanel3.add(btnEnviar, BorderLayout.EAST);

        mypanel1.add(mypanel2, BorderLayout.NORTH);
        mypanel1.add(txtHistorial, BorderLayout.CENTER);
        mypanel1.add(mypanel3, BorderLayout.SOUTH);
        mypanel1.setBorder(new EmptyBorder(10,10,10,10));

        this.add(mypanel1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
    }

    private void formWindowClosed(java.awt.event.WindowEvent evt) {
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        cliente.confirmarDesconexion();
    }

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {
        if(txtMensaje.getText().equals("1")||txtMensaje.getText().equals("0")){
            txtHistorial.append("Yo: \n"+txtMensaje.getText()+"?\n");
            txtHistorial.append("El factorial es 1\n");
            txtMensaje.setText("");
        }else{
            mensaje = txtMensaje.getText();
            cliente.enviarFactorial(mensaje);
            txtHistorial.append("Yo: \n"+txtMensaje.getText()+"?\n");
            txtMensaje.setText("");
        }
    }

    private String[] getIP_Puerto_Nombre() {
        String s[]=new String[3];
        s[0]=DEFAULT_IP;
        s[1]=DEFAULT_PORT;
        JTextField ip = new JTextField(16);
        JTextField puerto = new JTextField(16);
        JTextField usuario = new JTextField(16);
        ip.setText(DEFAULT_IP);
        puerto.setText(DEFAULT_PORT);
        usuario.setText("Nombre de usuario");
        JPanel mypanelfont = new JPanel();
        JPanel myPanel = new JPanel();
        mypanelfont.setLayout(null);
        mypanelfont.add(myPanel);
        mypanelfont.setSize(300, 250);
        myPanel.setLayout(new GridLayout(3, 2));
        myPanel.add(new JLabel("IP del Servidor:"));
        myPanel.add(ip);
        myPanel.add(new JLabel("Puerto de la conexión:"));
        myPanel.add(puerto);
        myPanel.add(new JLabel("Escriba su nombre:"));
        myPanel.add(usuario);        
        int result = JOptionPane.showConfirmDialog(null, myPanel, 
                 "Inicia sesion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
                s[0]=ip.getText();
                s[1]=puerto.getText();
                s[2]=usuario.getText();
        }else{
            System.exit(0);
        }
        return s;
    } 

    void sesionIniciada(String identificador) {
        this.setTitle("Estas en linea "+identificador);
    }

    void addContacto(String contacto) {
    }

    void addMensaje(String emisor, String mensaje) {
        txtHistorial.append(emisor + ": " + mensaje+"\n");
    }

    void eliminarContacto(String identificador) {
        System.out.println("");
        return;
    }

    private void txtMesajeKeyTyped(java.awt.event.KeyEvent evt){
        char validar = evt.getKeyChar();
        if(txtMensaje.getText().length()>=5){
            evt.consume();
        }else{
            if(!Character.isDigit(validar));
        }
    }

    public static void main(String[] args) {
        Color color = new Color(169,46,34);
        UIManager.put("nimbusBase", color);
        UIManager.put("color", color.RED);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaCliente().setVisible(true);
            }
        });
    }
}