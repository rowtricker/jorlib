package org.jorlib.frameworks.columngeneration.branchandprice.branchingstrategies;

import org.jorlib.frameworks.columngeneration.branchandprice.branchingdecisions.BranchingDecision;
import org.jorlib.frameworks.columngeneration.colgenmain.AbstractColumn;
import org.jorlib.frameworks.columngeneration.master.cutGeneration.AbstractInequality;
import org.jorlib.frameworks.columngeneration.model.ModelInterface;
import org.jorlib.frameworks.columngeneration.pricing.AbstractPricingProblem;

/**
 * Created by rowan on 22-4-17.
 *
 * @author Rowan Hoogervorst
 */
public class BranchOnVariable<T extends ModelInterface,U extends AbstractColumn<T,? extends AbstractPricingProblem<T,U>>> implements BranchingDecision<T,U>
{
    private final U column;
    private final Direction direction;
    private final int bound;

    public BranchOnVariable(U column, Direction direction, int bound) {
        this.column = column;
        this.direction = direction;
        this.bound = bound;
    }
    
    
    /**
     * Determine whether a particular column from the parent node is feasible for the child node
     * resulting from the Branching Decision and hence can be transferred.
     *
     * @param column column
     * @return true if the column is feasible, false otherwise
     */
    @Override
    public boolean columnIsCompatibleWithBranchingDecision(U column)
    {
        return false;
    }

    /**
     * Determine whether a particular inequality from the parent node is feasible for the child node
     * resulting from the Branching Decision and hence can be transferred.
     *
     * @param inequality inequality
     * @return true if the inequality is feasible, false otherwise
     */
    @Override
    public boolean inEqualityIsCompatibleWithBranchingDecision(AbstractInequality inequality)
    {
        return false;
    }

    public U getColumn()
    {
        return column;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public int getBound()
    {
        return bound;
    }

    public enum Direction {
        GEQ,
        LEQ
    }
}

