package Client;

import java.io.InputStream;
import java.io.OutputStream;

public interface IClientStrategy {
    // args changed !!!!
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);

}
