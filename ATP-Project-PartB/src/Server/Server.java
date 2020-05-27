package Server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private int port;
    private int socketTimeOut;
    private volatile boolean stop;
    private IServerStrategy handler;
    private ExecutorService executorService;

    public Server(int port,int socketTimeOut,IServerStrategy handler) {
        this.port = port;
        this.socketTimeOut = socketTimeOut;
        this.handler = handler;
        stop = false;
        //TODO config file need to declare about theard amounts !
//        int maxPool=Configurations.getMaxPool();
//        executorService = Executors.newFixedThreadPool(maxPool);
        executorService=Executors.newCachedThreadPool();//HIDE IF USING CONFIG FILE
    }

    public void start() {
        new Thread(()->{
            try{
                ServerSocket server = null;
                server = new ServerSocket(this.port);
                server.setSoTimeout(this.socketTimeOut);
                while (!stop)
                {
                    try {
                        Socket aClient = server.accept();
                        /// here come theard pool
//                        this.handler.HandleClient(aClient.getInputStream(),aClient.getOutputStream());
                        executorService.execute(new ThreadFromServer(aClient.getInputStream(),aClient.getOutputStream(),this.handler));
                        //Thread.sleep(360000);
                    }catch (SocketTimeoutException e){
                    }
//                    catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                }
                server.close();
                executorService.shutdown();
                // must close the pool to
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }

    public void stop() {

//        System.out.println(String.format("Server: %s has Stoped",this.handler.toString()));
        this.stop = true;
    }
}

