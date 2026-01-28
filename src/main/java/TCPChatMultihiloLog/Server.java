package TCPChatMultihiloLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {

        //Puerto en el que escucha el servidor
        final int PORT = 5000;
        //variable de control del bucle del servidor
        boolean active = true;

            //socket para el servidor
        try(ServerSocket server = new ServerSocket(PORT);

            //se espera  a la conexión del cliente. Accept bloquea hasta que eso pase
            Socket clientSocket = server.accept();

            //inputStream lee bytes
            //InputStreamReader convierte bytes a cracteres
            // bufferReader lee caracteres
            BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));

            //flujo de salida
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);

            Scanner sc = new Scanner(System.in);
            ){

            // si se llega a esta línea, es porqueel cliente se ha conectado
            System.out.println("[SERVIDOR] Cliente conectado correctamente");

            // hilo para recibir el mensaje del cliente
            Thread receptor = new Thread (()->{

                 boolean listening = true;

                 try{
                     String message="";

                     while (listening && (message = in.readLine()) !=null){
                         System.out.println("[SERVIDOR] Mensaje del cliente recibido: " + message);

                         // hilo receptor escribe en el log
                         try{
                             // utiliza el metodo buildMessage para construir el mensaje a escribir en el log
                             String msg = ChatUtils.buildMessage("SERVIDOR", "RECIBE", message);

                             // utiliza el método synchronized para escribir en el log
                             ChatUtils.write(msg);

                         }catch(IOException ioe){
                             System.out.println("[SERVIDOR RECIBE] Error de escritura en el log: " + ioe.getMessage());
                             ioe.printStackTrace();
                         }

                         // si el mensaje es exit, no se recibirán más mensajes
                         if (message.equalsIgnoreCase("exit")){
                             listening= false;
                         }
                     }

                 } catch(IOException ioe){
                     System.out.println("[SERVIDOR] Error leyendo el mensaje del cliente"+ ioe.getMessage());
                 }
            });
            receptor.start();


            // HILO main  para escribir el mensaje pro teclado y mandarlo
            while(active){

                System.out.println("[SERVIDOR] Escribe el mensaje: ");
                String message = sc.nextLine();

                System.out.println("[SERVIDOR] " + message);

                //enviamos el mensaje al cliente
                out.println(message);

                // hilo principal escribe en el log
                try{
                    // utiliza el metodo buildMessage para construir el mensaje a escribir en el log
                    String msg = ChatUtils.buildMessage("SERVIDOR", "ENVIA", message);

                    // utiliza el método synchronized para escribir en el log
                    ChatUtils.write(msg);

                }catch(IOException ioe){
                    System.out.println("[SERVIDOR ENVIA] Error de escritura en el log: " + ioe.getMessage());
                    ioe.printStackTrace();
                }

                //si el mensaje es exit, no se enviaran más mensajes
                if("exit".equalsIgnoreCase(message)){
                    active= false;
                }
            }
            System.out.println("[SERVIDOR] Chat finalizado");

        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }
}
