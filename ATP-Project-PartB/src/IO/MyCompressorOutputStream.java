package IO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.*;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;


    public MyCompressorOutputStream(OutputStream os){
        this.out = os;
        }
    @Override
    public void write(int b) throws IOException {
            out.write(b);
    }
    @Override
    public void write(byte[] b) throws IOException {
        //take b and compress
        byte[] compressedB=Maze_1d_Compressor(b);
        // send compressedB
        try{
        if (this.out instanceof ObjectOutputStream){
            ((ObjectOutputStream)this.out).writeObject(compressedB);
        }
        else
            out.write(compressedB);
    }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public byte[] Maze_1d_Compressor(byte[] b)
    {// function gets full length maze in 1D, and compress its mazeData
        ArrayList<Byte> tmpList = new ArrayList<Byte>();
        int idx=12;
        int cnt=1;


        // first 12 bytes is meta data send it as is
        for (int i=0; i<12;i++) {
            tmpList.add(b[i]);
        }
        // check if data start with 0. if not send 0
        if(b[12]!=0) {
            tmpList.add((byte) 0);
        }
        //if yes. count the times 0 repeat
        for (;idx<b.length;idx++)
        {
            if(idx+1<b.length && b[idx]==b[idx+1])
            { // next one is same like current one.
                cnt++;

                if(cnt==256)
                { // counted more times than able
                    tmpList.add((byte)255);
                    tmpList.add((byte)0);
                    cnt=1;
                }
            }
            else
            {// next one is different for current
                tmpList.add((byte)cnt);
                cnt=1;
            }
        }
        // at this point tmpList holds the compressed maze.
        // create array with size needed and copy all elements from list to arr
        byte[] compressedMaze1d = new byte[tmpList.size()];
        int i=0;
        for (Byte element:tmpList) {
            compressedMaze1d[i]=element;
            i++;
        }
        return compressedMaze1d;
    }

}
