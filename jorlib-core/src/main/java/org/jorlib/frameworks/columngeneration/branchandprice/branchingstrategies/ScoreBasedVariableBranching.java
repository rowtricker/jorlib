package org.jorlib.frameworks.columngeneration.branchandprice.branchingstrategies;

import org.jorlib.frameworks.columngeneration.branchandprice.AbstractBranchCreator;
import org.jorlib.frameworks.columngeneration.branchandprice.BAPNode;
import org.jorlib.frameworks.columngeneration.colgenmain.AbstractColumn;
import org.jorlib.frameworks.columngeneration.model.ModelInterface;
import org.jorlib.frameworks.columngeneration.pricing.AbstractPricingProblem;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by rowan on 22-4-17.
 *
 * @author Rowan Hoogervorst
 */
public abstract class ScoreBasedVariableBranching<T extends ModelInterface, U extends AbstractColumn<T, V>, V extends AbstractPricingProblem<T, U>> extends AbstractBranchCreator<T, U, V>
{
    /** The set of variables to evaluate. */
    protected final List<U> candidateColumns;

    /**
     * Creates a new BranchCreator
     *
     * @param dataModel      data model
     * @param pricingProblem pricing problem
     */
    public ScoreBasedVariableBranching(T dataModel, V pricingProblem, List<U> candidateColumns)
    {
        super(dataModel, pricingProblem);
        this.candidateColumns = candidateColumns;
    }

    /**
     * Creates a new BranchCreator
     *
     * @param dataModel       data model
     * @param pricingProblems pricing problems
     */
    public ScoreBasedVariableBranching(T dataModel, List<V> pricingProblems, List<U> candidateColumns)
    {
        super(dataModel, pricingProblems);
        this.candidateColumns = candidateColumns;
    }

    /**
     * This method decides whether the branching can be performed. To reduce overhead, this method
     * should also store on which aspect of the problem it should branch, e.g. an edge or a
     * variable.
     *
     * @param solution Fractional column generation solution
     * @return Returns true if the branching can be performed, false otherwise
     */
    @Override
    protected boolean canPerformBranching(List<U> solution)
    {
        return candidateColumns.size() > 0;
    }

    /**
     * Method which returns a list of child nodes after branching on the parentNode
     *
     * @param parentNode Fractional node on which we branch
     * @return List of child nodes
     */
    @Override
    protected List<BAPNode<T, U>> getBranches(BAPNode<T, U> parentNode)
    {
        // Find column with best score
        U bestColumn = candidateColumns.stream().max(Comparator.comparing(this::getScoreForVariable)).get();

        // Create branches
        BranchOnVariable<T, U> lowerBranchDecision = new BranchOnVariable<>(bestColumn, BranchOnVariable.Direction.LEQ, (int) Math.floor(bestColumn.value));
        BAPNode<T, U> lowerBranch = createBranch(parentNode, lowerBranchDecision, parentNode.getSolution(), parentNode.getInequalities());

        BranchOnVariable<T, U> upperBranchDecision = new BranchOnVariable<>(bestColumn, BranchOnVariable.Direction.GEQ, (int) Math.ceil(bestColumn.value));
        BAPNode<T, U> upperBranch = createBranch(parentNode, upperBranchDecision, parentNode.getSolution(), parentNode.getInequalities());

        return Arrays.asList(lowerBranch,upperBranch);
    }

    protected abstract double getScoreForVariable(U candidate);
}
