package EjercicioTCPBásico;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {
    public static void main(String[] args) {
        final int PORT = 5001;
        ServerSocket serverSocket= null;
        Socket  clientSocket= null;
        DataInputStream in;
        DataOutputStream out;

        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("[SERVIDOR] Servidor Iniciado");

            while(true){
                //servidor espera que el cliente se conecte
                clientSocket = serverSocket.accept();
                System.out.println("[SERVIDOR] Cliente conectado");

                out = new DataOutputStream(clientSocket.getOutputStream());
                in = new DataInputStream(clientSocket.getInputStream());

                //RECIBE -> servidor espera a recibir el mensaje del cliente
                String message = in.readUTF();
                System.out.println(" [SERVIDOR] Mensaje recibido: " + message);

                //ENVIA
                out.writeUTF(message);

                //cerrar conextion  con el cliente
                in.close();
                out.close();
                clientSocket.close();
                System.out.println(" [SERVIDOR] Cliente desconectado");
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }
}
