

    1) TCP Básico: “Eco” (cliente → servidor → cliente)

    Objetivo: el cliente manda un texto y el servidor responde exactamente el mismo texto.

    Requisitos

    Usar ServerSocket y Socket

    Usar DataInputStream/DataOutputStream o BufferedReader/PrintWriter

    Checklist

     Servidor escucha en un puerto fijo (ej. 5000)

     Servidor acepta conexión (accept())

     Cliente se conecta al servidor

     Cliente envía una cadena

     Servidor recibe y reenvía la misma cadena

     Cliente imprime la respuesta

     Cerrar sockets