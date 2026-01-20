package EjercicioTCPInteractivo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        final String HOST = "127.0.0.1";
        final int PORT = 5000;

        Scanner sc = new Scanner(System.in);

        //en try/catch with resources-> declaración e inicialización dentro de lso paréntesis de recursos
        try ( Socket  socket = new Socket(HOST,PORT);

              //flujo de entrada para recibir datos del servidor
              DataInputStream in  = new DataInputStream(socket.getInputStream());

              //flujo de salida para recibir datos desde el servidor
              DataOutputStream out= new DataOutputStream(socket.getOutputStream())
            ){

            System.out.println("Escribe el mensaje que manda el cliente: ");
            String  message= sc.nextLine();
            out.writeUTF(message);

            String serverRespond =  in.readUTF();
            System.out.println("Respuesta del servidor: "+ serverRespond);

        } catch(IOException ioe){
            ioe.printStackTrace();

        }
    }
}
