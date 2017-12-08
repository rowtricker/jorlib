package org.jorlib.frameworks.columngeneration.colgenmain;

import org.jorlib.frameworks.columngeneration.model.ModelInterface;
import org.jorlib.frameworks.columngeneration.pricing.AbstractPricingProblem;

/**
 *  A stopping condition for 
 *
 * @author Rowan Hoogervorst
 */
public interface StoppingCondition<T extends ModelInterface, U extends AbstractColumn<T, V>,
        V extends AbstractPricingProblem<T, U>>
{
    
    boolean isStopColumnGeneration();
    
    boolean stillCheckForInequalities();
}
