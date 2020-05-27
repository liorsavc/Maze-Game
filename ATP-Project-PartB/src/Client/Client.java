package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


public class Client  {
    private InetAddress host;
    private int port;
    private IClientStrategy handler;
    private Socket server;

    public Client(InetAddress host, int port, IClientStrategy handler) {
        this.host = host;
        this.port = port;
        this.handler = handler;
       // this.server=new Socket()
    }

//    @Override
//    public void run() {
//        // add thread pool
//        communicateWithServer();
//    }


    public void communicateWithServer() {
        try {
            Socket server = new Socket(host.getLocalHost(), this.port);
            //Socket server = new Socket("127.0.0.1", this.port);

//            System.out.println("Client:Connected to Server"+handler.toString());
//            ObjectInputStream clientInput = new ObjectInputStream(server.getInputStream());
//            ObjectOutputStream clientOutput = new ObjectOutputStream(server.getOutputStream());
//            System.out.println("Client: Starting clientStrategy");

            handler.clientStrategy(server.getInputStream(), server.getOutputStream());
//            System.out.println("Client: finish clientStrategy");



            server.close();


        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
