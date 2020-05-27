package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    private InputStream in;
    private byte[] metaData = new byte[12]; // [row,row,col,col,start(row),start(row),start(col),start(col)..........]


    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read(byte[] b) throws IOException {



        byte token = 1;
        int timesReplay=1;
        int idx=0;
        while(idx<12){//readd 12 byte metadata. always 12
            b[idx] = (byte)in.read();
//            System.out.println( "metadata:"+idx+" read:"+(b[idx]&0xFF));
            idx++;
        }
        // get the size of maze:
        int rows = (b[0]&0xFF)+(b[1]&0xFF);
        int cols = (b[2]&0xFF)+(b[3]&0xFF);
        int sizeOfDecompressedMaze=rows*cols;

        // first time loop as much times as read(from first cell represents times saw zero char) and insert '0' to b . switch token to 1
        timesReplay=(read()&0xFF);
//        System.out.println( "0:read : "+timesReplay);
        for (;timesReplay>0;timesReplay--) {
            b[idx] = (byte) 0;
            idx++;
        }
        // read until not last index of compressed array
        while(idx<sizeOfDecompressedMaze+12)
        {
            timesReplay=(read()&0xFF);
//            System.out.println(token+ "read : "+timesReplay);
            for (;timesReplay>0;timesReplay--) {
                b[idx] = token;
                idx++;
            }
            if(token==1)
                token=0;
            else
                token=1;
        }
        return b.length;
    }

    @Override
    public int read() throws IOException {
        try {
            return in.read();
        }
        catch (Exception e){}
        return 0;
    }
}


