package Mex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ViewMex implements Runnable {
    Socket socket;
    BufferedReader buffer;
    String username;
    int var = 0;
    int indicesocket;

    public ViewMex(Socket socket, int indicesocket) throws IOException {
        this.socket = socket;
        buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.indicesocket=indicesocket;
    }

    public void run() {
        while (true) {
            try {
                if (buffer.ready()) {
                    if (var == 0)
                        username(buffer.readLine());
                    else {
                        inviaMex(buffer.readLine());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void username(String message) throws IOException {

        App.listaSocketUsername.set(indicesocket,message);
        username=message;
        var = 1;
    }

    public void inviaMex(String message) throws IOException {

        PrintWriter printWriter;
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        int indice=sceltanome();

        if(indice!=-1) {
            Socket ricevente = App.listaSocket.get(indice);
            printWriter = new PrintWriter(ricevente.getOutputStream(), true);
            printWriter.println(username+ " scrive " + message + '\n');
            printWriter.flush();
        }else
        {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("Impossibile mandare messaggio" );
            printWriter.flush();
        }

    }

    public int sceltanome() throws IOException {
        String name;
        int scelta;
        while (true) {
            if (buffer.ready()) {
                name = buffer.readLine();
                System.out.println(name);
                int indice = -1;
                int i = 0;
                for (int j=0; j<App.listaSocketUsername.size();j++)
                {
                    if (name.equals(App.listaSocketUsername.get(j))) {
                        System.out.println("nome" + App.listaSocketUsername.get(j));
                        indice=j;
                    }
                }
                System.out.println("indice è" + indice);
                 return indice;
                }

            }
        }

}
