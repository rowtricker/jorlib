/* ==========================================
 * jORLib : Java Operations Research Library
 * ==========================================
 *
 * Project Info:  http://www.coin-or.org/projects/jORLib.xml
 * Project Creator:  Joris Kinable (https://github.com/jkinable)
 *
 * (C) Copyright 2015-2016, by Joris Kinable and Contributors.
 *
 * This program and the accompanying materials are licensed under LGPLv2.1
 * as published by the Free Software Foundation.
 */
package org.jorlib.frameworks.columngeneration.colgenmain.columnmanager;

import org.jorlib.frameworks.columngeneration.colgenmain.AbstractColumn;
import org.jorlib.frameworks.columngeneration.model.ModelInterface;
import org.jorlib.frameworks.columngeneration.pricing.AbstractPricingProblem;

import java.util.Collection;
import java.util.Set;

/**
 * Defines a column columnManager, used to control the columns that are used in the restricted master
 * problem (RMP) over the course of the column generation.
 *
 * @author Rowan Hoogervorst
 */
public interface ColumnManager<T extends ModelInterface, U extends AbstractColumn<T, V>, V extends AbstractPricingProblem<T, ? extends AbstractColumn<T, V>>> {

    void addColumns(Collection<U> newColumns);

    void updateColumns();

    Set<U> getCurrentColumns();
}
