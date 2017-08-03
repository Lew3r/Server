package Mex;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class App
{
    public static ArrayList<Socket> listaSocket;
    public static ArrayList<String> listaSocketUsername;
    static int indicesocket;
    public static void main( String[] args ) throws Exception {
        Socket socket;
        ServerSocket server = new ServerSocket(2000);
        listaSocket = new ArrayList<Socket>();
        listaSocketUsername = new ArrayList<String>();
        indicesocket = -1;

        while (true) {
            socket = server.accept();
            System.out.println("si è appena connesso" + socket.getInetAddress());
            listaSocket.add(socket);
            listaSocketUsername.add("utentenoninserito");
            indicesocket++;
            new Thread(new ViewMex(socket)).start();

        }
    }
   public static int returnindicesocket()
   {
    return indicesocket;
   }

        public static void cercaConnDelete(String utente)
        {
            for(int i=0;i< listaSocketUsername.size();i++) {
                System.out.println("contenuto" + listaSocketUsername.get(i));
            }

            for(int i=0;i< listaSocketUsername.size();i++) {
                if(listaSocketUsername.get(i).equalsIgnoreCase(utente))
                {
                    listaSocketUsername.remove(i);
                    listaSocket.remove(i);
                    System.out.println("si è appena disconnesso" + utente);
                    indicesocket--;
                    break;
                }
            }
            for(int i=0;i< listaSocketUsername.size();i++) {
                System.out.println("contenuto" + listaSocketUsername.get(i));
            }
        }
        public static ArrayList<Socket> returnListaSocket()
        {
            return listaSocket;
        }

}
