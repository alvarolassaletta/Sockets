package TCPChatMultihiloLog;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        final String HOST= "127.0.0.1"; // Dirección Ip del servidor -> localhost
        final int PORT = 5000;  // puerto en el que escucha el servidor

        boolean active= true;
        Scanner  sc = new Scanner(System.in);

        try(Socket socket =new Socket(HOST,PORT);

            // InputStream -> flujo de bytes
            //InputStreamReader -> bytes a caracteres
            // BufferedReader -> Caracteres
            BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter( socket.getOutputStream(),true);

        ){
            System.out.println("[CLIENTE] Cliente conectado correctamente");

            //Hilo receptor para recibir los mensajes del cliente
            Thread receptor = new Thread(()->{

                boolean listening = true;
                String message="";

                try{
                    //readline bloquea
                    // devuelve null cuando no hay lineas
                    while(listening && (message= in.readLine())!=null){
                        System.out.println("[CLIENTE] Mensaje del servidor recibido: "+ message);

                        // hilo receptor escribe en el log
                        try{
                            // utiliza el metodo buildMessage para construir el mensaje a escribir en el log
                            String msg = ChatUtils.buildMessage("CLIENTE", "RECIBE", message);

                            // utiliza el método synchronized para escribir en el log
                            ChatUtils.write(msg);


                        }catch(IOException ioe){
                            System.out.println("[CLIENTE RECIBE] Error de escritura en el log: " + ioe.getMessage());
                            ioe.printStackTrace();
                        }

                        if("exit".equalsIgnoreCase(message)){
                            listening=false;
                        }
                    }
                }catch (IOException ioe){
                    System.out.println("[CLIENTE] Error de lectura en el Cliente");
                }
            });
            //Ejecutamos el hilo receptor
            receptor.start();


            while(active){
                System.out.println("[CLIENTE] Escribe el mensaje para el servidor");
                // nextLine bloquea  hasta que se pulse enter
                String message = sc.nextLine();

                out.println(message);
                System.out.println("[CLIENTE] Mensage enviado al servidor: " + message);

                //Hilo principal escribe en el log
                try{
                    // utiliza el metodo buildMessage para construir el mensaje a escribir en el log
                    String msg = ChatUtils.buildMessage("CLIENTE", "ENVIA", message);

                    // utiliza el método synchronized para escribir en el log
                    ChatUtils.write(msg);

                }catch(IOException ioe){
                    System.out.println("[CLIENTE ENVIA] Error de escritura en el log: " + ioe.getMessage());
                    ioe.printStackTrace();
                }

                if("exit".equalsIgnoreCase(message)){
                    active = false;
                }
            }
            System.out.println("[CLIENTE] Chat finalizado");

        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
