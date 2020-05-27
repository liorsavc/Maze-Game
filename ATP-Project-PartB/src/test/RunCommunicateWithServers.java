package test;

import Client.Client;
import IO.MyDecompressorInputStream;
import Server.*;
import Client.*;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by Aviadjo on 3/27/2017.
 */
public class RunCommunicateWithServers {
    public static void main(String[] args) throws FileNotFoundException {
        Configurations.setConfig(10,"BestFS");


        //Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1, new ServerStrategySolveSearchProblem());
        //Server stringReverserServer = new Server(5402, 1000, new ServerStrategyStringReverser());

        //Starting  servers
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
        //stringReverserServer.start();

        //Communicating with servers
        //CommunicateWithServer_MazeGenerating();
        CommunicateWithServer_SolveSearchProblem();
        CommunicateWithServer_SolveSearchProblem();
        CommunicateWithServer_SolveSearchProblem();

        //CommunicateWithServer_StringReverser();

        //Stopping all servers
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
        //stringReverserServer.stop();
    }

    private static void CommunicateWithServer_MazeGenerating() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{2, 2};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
//                        System.out.println( "TEST:MazeGenerating: start readObject from server");
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
//                        System.out.println( "TEST:MazeGenerating: finish readObject from server");

                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[500000 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        Maze maze = new Maze(decompressedMaze);
                        System.out.println( "TEST:MazeGenerating: start printing maze");

                        maze.print();
//                        System.out.println( "TEST:MazeGenerating: finished printing maze. finished all mazeGenerating part");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });//end of constructor
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        MyMazeGenerator mg = new MyMazeGenerator();
                        Maze maze = mg.generate(5, 5);
                        System.out.println( "solver printing maze");
                        maze.print();
//                        System.out.println( "TEST:MazeSolver: start writeObject to server (maze)");

                        toServer.writeObject(maze); //send maze to server
//                        System.out.println( "TEST:MazeSolver: finish writeObject to server");

                        toServer.flush();
//                        System.out.println( "TEST:MazeSolver: start readObject from server (solution)");

                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
//                        System.out.println( "TEST:MazeSolver: finish readObject from server (solution)");

                        //Print Maze Solution retrieved from the server
                        System.out.println(String.format("Solution steps: %s", mazeSolution));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                            System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

//    private static void CommunicateWithServer_StringReverser() {
//        try {
//            Client client = new Client(InetAddress.getLocalHost(), 5402, new IClientStrategy() {
//                @Override
//                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
//                    try {
//                        BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
//                        PrintWriter toServer = new PrintWriter(outToServer);
//
//                        String message = "Client Message";
//                        String serverResponse;
//                        toServer.write(message + "\n");
//                        toServer.flush();
//                        serverResponse = fromServer.readLine();
//                        System.out.println(String.format("Server response: %s", serverResponse));
//                        toServer.flush();
//                        fromServer.close();
//                        toServer.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            client.communicateWithServer();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }
}
