package EjericioTCPMultiMensaje;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main (String [] args){
        final String HOST = "127.0.0.1";
        final int PORT = 5001;

        Scanner sc  = new Scanner(System.in);
        boolean stop = true;

        try(Socket socket = new Socket(HOST,PORT);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        ){
            while(stop){
                System.out.println("[CLIENTE] Introduce el mensaje del cliente:  ");
                String clientMessage= sc.nextLine();
                out.writeUTF(clientMessage);

                if("salir".equalsIgnoreCase(clientMessage)){
                    stop=false;
                    System.out.println("[CLIENTE] Cerrando  el programa..");
                }else{
                    String serverMessage = in.readUTF();
                    System.out.println("[CLIENTE] Respuesta del servidor: " + serverMessage);
                }

              /*  if("salir".equalsIgnoreCase(serverMessage)){
                    stop= false;
                    System.out.println("Cerrando el programa");
                }*/
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
