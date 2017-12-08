package org.jorlib.frameworks.columngeneration.colgenmain;

import org.jorlib.frameworks.columngeneration.model.ModelInterface;
import org.jorlib.frameworks.columngeneration.pricing.AbstractPricingProblem;

/**
 * Created by rowan on 14-8-17.
 *
 * @author Rowan Hoogervorst
 */
public class BoundExceedsCutoff<T extends ModelInterface, U extends AbstractColumn<T, V>,
        V extends AbstractPricingProblem<T, U>> implements StoppingCondition<T,U,V>
{

    private final ColGen<T, U, V> colGen;

    public BoundExceedsCutoff(ColGen<T,U,V> colGen) {
        this.colGen = colGen;
    }
   
    @Override
    public boolean isStopColumnGeneration()
    {
        return colGen.boundOnMasterExceedsCutoffValue();
    }

    @Override
    public boolean stillCheckForInequalities()
    {
        return false;
    }
}
