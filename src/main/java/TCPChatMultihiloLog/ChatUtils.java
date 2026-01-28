package TCPChatMultihiloLog;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatUtils {
    private ChatUtils(){}

    //objeto Path para reprensetar la ruta. No se crea con esta lénea
    static final Path PATH = Paths.get("Data","Log_Chat_Tcp.txt");

    //constante con el modelo de formateo de la fecha
    static final DateTimeFormatter FORMATTER  = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Metodo estatico sincronizado. Solo un hilo podrá utilizarlo  la vez*/

    public  static synchronized void write (String message) throws IOException {

        //Si el directorio no existe, se crea
        if(!Files.exists(PATH.getParent())){
            Files.createDirectories(PATH.getParent());
        }

        try(
                BufferedWriter br = Files.newBufferedWriter(
                        PATH,
                        StandardCharsets.UTF_8,
                        //si el archivo existe, se usa. Si no, se crea.
                        // Es con esta línea con la que se crea el fichero, en caso de que no exista
                        StandardOpenOption.CREATE,
                        // No sobreescribe el conteido
                        StandardOpenOption.APPEND)
            ){

            br.write(message);
            br.newLine();
        }

    }

    /**
     * Usa las la clase LocalDateTime para obtener la fecha y hora actual
     * Devuelve un String con la fecha y hora
     * @return String  Devuelve un string con la fecha formateada
     * */
    public static String buildFormattedDate(){
        LocalDateTime date = LocalDateTime.now();
        String dateFormat = date.format(FORMATTER);

        return "[" + dateFormat + "] ";
    }

    /**Crea un StringBuilder para construir el mensaje a escribir en el log.
     *  @para role del emisor del mensaje (por ejemplo: CLIENTE o SERVIDOR)
     *  @param direction  Dirección del mensaje (ENVIA o RECIBE)
     *  @param message    Contenido del mensaje intercambiado
     *  @return String mensaje construido listo para ser escrito en el log
     *  */
    public static String buildMessage(String role, String direction, String message){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(role);
        sb.append(" ");
        sb.append(direction);
        sb.append("] ");
        sb.append(buildFormattedDate());
        sb.append(message);
        return sb.toString();

    }



}
