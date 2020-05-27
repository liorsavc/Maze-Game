package algorithms.search;

public interface ISearchingAlgorithm {

    public AState search(ISearchable s);

    Solution solve(ISearchable domain);

    public String getName();

    public int getNumberOfNodesEvaluated();
}
