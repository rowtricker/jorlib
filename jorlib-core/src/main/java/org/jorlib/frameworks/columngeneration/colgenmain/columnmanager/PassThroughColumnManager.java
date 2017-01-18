package org.jorlib.frameworks.columngeneration.colgenmain.columnmanager;

import org.jorlib.frameworks.columngeneration.colgenmain.AbstractColumn;
import org.jorlib.frameworks.columngeneration.model.ModelInterface;
import org.jorlib.frameworks.columngeneration.pricing.AbstractPricingProblem;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This represents a very basic column columnManager, in which all columns are used in each iteration.
 * <p>
 * Note that this entails that there is no selection effect on the used columns and that this column columnManager essentially
 * just passes through all available columns. This might potentially lead to a very large amount of columns.
 *
 * @author Rowan Hoogervorst
 */
public class PassThroughColumnManager<T extends ModelInterface, V extends AbstractPricingProblem<T, ? extends AbstractColumn<T, V>>, W extends AbstractColumn<T, V>> implements ColumnManager<T, W, V>
{

    private final Set<W> columns = new HashSet<>();

    @Override
    public void addColumns(Collection<W> newColumns)
    {
        columns.addAll(newColumns);
    }

    @Override
    public void updateColumns()
    {
        //Nothing to execute
    }

    @Override
    public Set<W> getCurrentColumns()
    {
        return columns;
    }
}
