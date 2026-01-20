package EjericioTCPMultiMensaje;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        final int PORT= 5001;
        Scanner sc = new Scanner(System.in);
        boolean stop = true;

        try(ServerSocket serverSocket = new ServerSocket(PORT)){

            while(true){
                try(
                    Socket clienteSocket = serverSocket.accept();
                    //flujo de entrada para recibir datos del cliente
                    DataOutputStream out = new DataOutputStream(clienteSocket.getOutputStream());
                    //flujo de salida para enviar datos al cliente
                    DataInputStream in = new DataInputStream(clienteSocket.getInputStream())
                ){
                    System.out.println("[SERVIDOR] Cliente conectado");
                    while(stop){
                        String clientMessage=  in.readUTF();
                        System.out.println("[SERVIDOR] Mensaje recibido del cliente: " + clientMessage);

                        if("salir".equalsIgnoreCase(clientMessage)){
                            stop=false;
                            System.out.println("[SERVIDOR] Cerrando el programa..");
                        }else{
                            System.out.println("[SERVIDOR] Introduce respuesta del servidor:...");
                            String serverMessage= sc.nextLine();
                            out.writeUTF(serverMessage);
                        }
                    }
                    stop = true;
                } catch(IOException ioe){
                    System.err.println("Error en la conexión del cliente " + ioe.getMessage());
                }
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }
}
