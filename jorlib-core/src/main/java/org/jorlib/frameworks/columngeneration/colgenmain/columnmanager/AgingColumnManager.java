package org.jorlib.frameworks.columngeneration.colgenmain.columnmanager;

import org.jorlib.frameworks.columngeneration.colgenmain.AbstractColumn;
import org.jorlib.frameworks.columngeneration.model.ModelInterface;
import org.jorlib.frameworks.columngeneration.pricing.AbstractPricingProblem;

import java.util.*;

/**
 * A column columnManager which is based on the concept of aging.
 * <p>
 * The aging concept implies that an age is associated to each column, which corresponds to the amount of iterations in
 * which the column has not been part of the basis. Hence, when a column is added to the column columnManager, it gets an age
 * of 0. When a column has not been part of the basis for an iteration its age is increased with 1. Moreover, when a
 * column is part of the basis, its age is reinitialized at 0. Only columns with an age below a certain threshold are
 * used in the next Master problem.
 * <p>
 * This class provides a parameter to adjust the current aging threshold. While the default value is generally expected
 * to perform well, some specfic problems may benefit substantially from adjusting this parameter. Clearly, this is
 * dependent on the time that is spent in solving LP problems opposed to time invested in the pricing problems.
 *
 * @author Rowan Hoogervorst
 */
public class AgingColumnManager<T extends ModelInterface, U extends AbstractColumn<T, V>, V extends AbstractPricingProblem<T, U>> implements ColumnManager<T, U, V>
{

    /** A hasmap giving the age of the columns. */
    private Map<U, Integer> ages = new HashMap<>() ;

    /** The threshold used to determine if a column should be used in the master problem. */
    private Integer threshold;

    /**
     * Consructs the aging column manager with a custom threshold value.
     *
     * @param threshold the threshold used to determine if a column of certain age should be considered
     */
    public AgingColumnManager(int threshold)
    {
        this.threshold = threshold;
    }

    @Override
    public void addColumns(List<U> newColumns)
    {
        for (U column : newColumns)
        {
            ages.putIfAbsent(column, 0);
        }
    }

    @Override
    public void updateColumns()
    {
        for (Map.Entry<U, Integer> entry : ages.entrySet())
        {
            if (entry.getKey().value > 0 && entry.getValue() > 0)
            {
                entry.setValue(0);
            } else
            {
                entry.setValue(entry.getValue() + 1);
            }
        }
    }

    @Override
    public Set<U> getCurrentColumns()
    {
        Set<U> currentColumns = new HashSet<>();
        for (Map.Entry<U, Integer> entry : ages.entrySet())
        {
            if (entry.getValue() <= threshold)
            {
                currentColumns.add(entry.getKey());
            }
        }
        return currentColumns;
    }
}
