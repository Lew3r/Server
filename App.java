package Mex;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class App
{
    public static ArrayList<Socket> listaSocket;
    public static ArrayList<String> listaSocketUsername;
    public static void main( String[] args ) throws Exception
    {
        PrintWriter printWriter;
        Socket socket;
        ServerSocket server = new ServerSocket(2000);
        listaSocket=new ArrayList<Socket>();
        listaSocketUsername=new ArrayList<String>();
        int indicesocket =-1;

        while(true)
        {
            socket= server.accept();
            System.out.println("si è appena connesso"+socket.getInetAddress());
            listaSocket.add(socket);
            listaSocketUsername.add("utentenoninserito");
            indicesocket++;
            new Thread(new ViewMex(socket,indicesocket)).start();

        }
    }
}
