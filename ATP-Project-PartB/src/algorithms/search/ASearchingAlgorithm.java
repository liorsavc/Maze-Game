package algorithms.search;

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    private int visitedStates;
    protected Collection<AState> collection;

    public ASearchingAlgorithm(){
        visitedStates = 0;
    }


    @Override
    ///// Astate ?!?!?!?!??!
    public abstract AState search(ISearchable s);

    public void increaseVisitedStates() {
        this.visitedStates++;
    }

    @Override
    public Solution solve(ISearchable domain) {
        // create new solution
        Solution solution = new Solution();
        // check if solution exist
        AState curV = this.search(domain);

        if (curV==null){
            // no solution
            return solution;
        }
        // have solution
        // recover the path from start state to goal state
        while(curV.getFather()!=null){
            solution.addToSolutionPath(curV);
            curV = curV.getFather();
        }
        solution.addToSolutionPath(curV);
        return solution;

    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return visitedStates;
    }

}
