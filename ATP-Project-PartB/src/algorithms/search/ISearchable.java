package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;

public interface ISearchable {


    public ArrayList<AState> getAllPossibleStates(AState curState);
    public AState getStartState();
    public AState getGoalState();

}