/* ==========================================
 * jORLib : a free Java OR library
 * ==========================================
 *
 * Project Info:  https://github.com/jkinable/jorlib
 * Project Creator:  Joris Kinable (https://github.com/jkinable)
 *
 * (C) Copyright 2015, by Joris Kinable and Contributors.
 *
 * This program and the accompanying materials are licensed under GPLv3
 *
 */
/* -----------------
 * SubtourInequality.java
 * -----------------
 * (C) Copyright 2015, by Joris Kinable and Contributors.
 *
 * Original Author:  Joris Kinable
 * Contributor(s):   -
 *
 * $Id$
 *
 * Changes
 * -------
 *
 */
package org.jorlib.demo.frameworks.columnGeneration.branchAndPriceExample.cg.master.cuts;

import java.util.Set;

import org.jorlib.frameworks.columnGeneration.master.cutGeneration.CutGenerator;
import org.jorlib.frameworks.columnGeneration.master.cutGeneration.Inequality;

/**
 * Class representing a subtour inequality: The number of edges entering/leaving the
 * cutSet must at least 2, otherwise there is a subtour within the cutSet
 *
 * @author Joris Kinable
 * @version 13-4-2015
 *
 */
public class SubtourInequality extends Inequality{

	/** Vertices in the cut set **/
	public final Set<Integer> cutSet;
	
	public SubtourInequality(CutGenerator maintainingGenerator, Set<Integer> cutSet) {
		super(maintainingGenerator);
		this.cutSet=cutSet;
	}

	@Override
	public boolean equals(Object o) {
		if(this==o)
			return true;
		else if(!(o instanceof SubtourInequality))
			return false;
		SubtourInequality other=(SubtourInequality)o;
		return this.cutSet.equals(other.cutSet);
	}

	@Override
	public int hashCode() {
		return cutSet.hashCode();
	}

}
