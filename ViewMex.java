package Mex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ViewMex implements Runnable {
    Socket socket;
    BufferedReader buffer;
    String username;
    String tuttiUsername;
    int var = 0;
    int indicesocket;
    String primomessaggio;
    int primo=0;
    public ViewMex(Socket socket, int indicesocket) throws IOException {
        this.socket = socket;
        buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.indicesocket=indicesocket;
    }

    public void run() {
        while (true) {

                if(primo==0&&var==0) {
                    if (var == 0) {
                        try {
                            if (buffer.ready()) {
                                username(buffer.readLine());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    if (primo != 1) {
                        primomessaggio = tuttiUsername;
                        try {
                            inviaMex(primomessaggio);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            if (buffer.ready())
                                inviaMex(buffer.readLine());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

        }
    }

    public void username(String message) throws IOException {

        App.listaSocketUsername.set(indicesocket,message);
        tuttiUsername=vediarrayList(App.listaSocketUsername);
        username=message;
        var = 1;
    }

    public void inviaMex(String message) throws IOException {

        if(message.equals("$richiestausername$"))
        {
            inviausername("$");

        }
        else {
            if (primo != 0) {
                PrintWriter printWriter;
                printWriter = new PrintWriter(socket.getOutputStream(), true);
                int indice = sceltanome();

                if (indice != -1) {
                    Socket ricevente = App.listaSocket.get(indice);
                    printWriter = new PrintWriter(ricevente.getOutputStream(), true);
                    printWriter.println(message + "%" + username + "$" + tuttiUsername);
                    printWriter.flush();

                } else {
                    printWriter = new PrintWriter(socket.getOutputStream(), true);
                    printWriter.println("Impossibile mandare messaggio" + "%" + "nessun unsername" + "$" + tuttiUsername);
                    printWriter.flush();

                }
            } else {
                inviausername("");

            }
        }
    }
    public void inviausername(String controllo) throws IOException {
        PrintWriter printWriter;
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        tuttiUsername=vediarrayList(App.listaSocketUsername);
        printWriter.println("£"+controllo + tuttiUsername);
        primo = 1;
    }
    public String vediarrayList(ArrayList<String> listaSocketUsername)
    {
        String tuttiUsername="";
        for(int i=0;i<listaSocketUsername.size();i++)
        {
           tuttiUsername= tuttiUsername+listaSocketUsername.get(i)+"$";
        }
        return tuttiUsername;
    }
    public int sceltanome() throws IOException {
        String name;
        int scelta;
        while (true) {
            if (buffer.ready()) {
                name = buffer.readLine();
                int indice = -1;
                int i = 0;
                for (int j=0; j<App.listaSocketUsername.size();j++)
                {
                    if (name.equals(App.listaSocketUsername.get(j))) {
                        indice=j;
                    }
                }
                return indice;
                }

            }
        }

}
