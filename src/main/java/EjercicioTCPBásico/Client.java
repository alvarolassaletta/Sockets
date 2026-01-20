package EjercicioTCPBásico;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {

        //direccion ip del servidor
        final String HOST= "127.0.0.1";
        final int PORT = 5001;

        DataInputStream in;
        DataOutputStream out;

        try{
            Socket socket = new Socket(HOST,PORT);
            in  = new DataInputStream(socket.getInputStream());
            out  = new DataOutputStream(socket.getOutputStream());

            //ENVIA -> envía el mensaje al servidor
            out.writeUTF("Eco");

            // RECIBE ->el cliente queda bloquedo esperando la respuesta del servidor
            String respond = in.readUTF();
            System.out.println("[CLIENTE] Respuesta del servidor recibida: " + respond);

            socket.close();

        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
