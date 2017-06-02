package org.jorlib.frameworks.columngeneration.branchandprice;

import org.jorlib.frameworks.columngeneration.branchandprice.branchingstrategies.ScoreBasedVariableBranching;
import org.jorlib.frameworks.columngeneration.colgenmain.AbstractColumn;
import org.jorlib.frameworks.columngeneration.model.ModelInterface;
import org.jorlib.frameworks.columngeneration.pricing.AbstractPricingProblem;

import java.util.List;

/**
 * Created by rowan on 22-4-17.
 *
 * @author Rowan Hoogervorst
 */
public class StrongBranching<T extends ModelInterface,U extends AbstractColumn<T,? extends AbstractPricingProblem<T,U>>> extends ScoreBasedVariableBranching<T.U>
{
    /**
     * Creates a new BranchCreator
     *
     * @param dataModel        data model
     * @param pricingProblem   pricing problem
     * @param candidateColumns
     */
    public StrongBranching(ModelInterface dataModel, AbstractPricingProblem pricingProblem, List candidateColumns)
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
    public StrongBranching(ModelInterface dataModel, List pricingProblems, List candidateColumns)
    {
        super(dataModel, pricingProblems, candidateColumns);
    }
}


    
}
