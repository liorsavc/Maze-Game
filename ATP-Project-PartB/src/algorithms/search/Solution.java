package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable {
    private ArrayList<AState> solutionPath;

    public Solution(){
        this.solutionPath = new ArrayList<AState>();
    }

    public ArrayList<AState> getSolutionPath() {
        return solutionPath;
    }

    public void addToSolutionPath(AState state) {
        //
        this.solutionPath.add(0,state);
    }
}
