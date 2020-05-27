package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ThreadFromServer implements Runnable {

    private InputStream inputStream;
    private OutputStream outputStream;
    private IServerStrategy serverStrategy;

    public ThreadFromServer(InputStream inputStream, OutputStream outputStream,IServerStrategy strategy) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.serverStrategy = strategy;
    }

    @Override
    public void run() {
//        System.out.println(String.format("Startegy %s started!!",serverStrategy.toString()));
        try{
            serverStrategy.HandleClient(this.inputStream,this.outputStream);
        }catch (  IOException | ClassNotFoundException e){
//            System.out.println("problem at Theard from server");
        }
    }
}
