package EjercicioTCPInteractivo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {
    public static void main(String[] args) {

        final int PORT = 5000;
        Scanner sc = new Scanner(System.in);

        try(ServerSocket server = new ServerSocket(PORT)){
            System.out.println("Servidor iniciado");

            while(true){
                //en try/catch with resources-> declaración e inicialización dentro de lso paréntesis de recursos
                try( Socket clientSocket = server.accept();
                     //flujo de entrada para recibir datos del cliente
                    DataInputStream  in = new DataInputStream(clientSocket.getInputStream());
                    //flujo de salida para enviar datos al cliente
                    DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                ) {
                    System.out.println("Cliente conectado");

                    //RECIBE-> recibe mensaje del cliente
                    String clientMessage = in.readUTF();
                    System.out.println("Mensaje del cliente recibido: " + clientMessage);

                    System.out.println("Escribe el mensaje que manda el servidor: ");
                    String message = sc.nextLine();

                    out.writeUTF(message);

                }catch(IOException ioe){
                    System.err.println("Error en la conexión del cliente " + ioe.getMessage());
                }
            }

        }catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
