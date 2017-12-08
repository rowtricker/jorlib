package org.jorlib.frameworks.columngeneration.colgenmain;

import org.jorlib.frameworks.columngeneration.model.ModelInterface;
import org.jorlib.frameworks.columngeneration.pricing.AbstractPricingProblem;

import java.time.Duration;

/**
 * Created by rowan on 14-8-17.
 *
 * @author Rowan Hoogervorst
 */
public class TimeLimit<T extends ModelInterface, U extends AbstractColumn<T, V>,
        V extends AbstractPricingProblem<T, U>> implements StoppingCondition<T,U,V>
{

    private final long allowedTime;
    private final ColGen<T, U, V> colGen;

    public TimeLimit(ColGen<T, U, V> colGen, Duration duration)
    {
        this.colGen = colGen;
        this.allowedTime = duration.toMillis();
    }


    @Override
    public boolean isStopColumnGeneration()
    {
        return colGen.getMasterSolveTime() + colGen.getPricingSolveTime() >= allowedTime;
    }

    @Override
    public boolean stillCheckForInequalities()
    {
        return false;
    }

    public long getTimeLimit() {
        return allowedTime;
    }
}
