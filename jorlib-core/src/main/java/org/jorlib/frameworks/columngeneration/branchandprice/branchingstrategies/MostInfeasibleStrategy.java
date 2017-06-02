package org.jorlib.frameworks.columngeneration.branchandprice.branchingstrategies;

import org.jorlib.frameworks.columngeneration.colgenmain.AbstractColumn;
import org.jorlib.frameworks.columngeneration.model.ModelInterface;
import org.jorlib.frameworks.columngeneration.pricing.AbstractPricingProblem;

import java.util.List;

/**
 * Created by rowan on 22-4-17.
 *
 * @author Rowan Hoogervorst
 */
public class MostInfeasibleStrategy<T extends ModelInterface,U extends AbstractColumn<T,V>,V extends AbstractPricingProblem<T,U>> extends ScoreBasedVariableBranching<T,U,V>
{
    /**
     * Creates a new BranchCreator
     *
     * @param dataModel        data model
     * @param pricingProblem   pricing problem
     * @param candidateColumns
     */
    public MostInfeasibleStrategy(T dataModel, V pricingProblem, List<U> candidateColumns)
    {
        super(dataModel, pricingProblem, candidateColumns);
    }

    /**
     * Creates a new BranchCreator
     *
     * @param dataModel        data model
     * @param pricingProblems  pricing problems
     * @param candidateColumns
     */
    public MostInfeasibleStrategy(T dataModel, List<V> pricingProblems, List<U> candidateColumns)
    {
        super(dataModel, pricingProblems, candidateColumns);
    }

    @Override
    protected double getScoreForVariable(U candidate)
    {
        return 0;
    }
}
