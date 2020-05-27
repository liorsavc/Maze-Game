package Server;

import java.io.*;

public interface IServerStrategy {
    public void HandleClient(InputStream inFromClient , OutputStream outToClient) throws IOException, ClassNotFoundException;
}
