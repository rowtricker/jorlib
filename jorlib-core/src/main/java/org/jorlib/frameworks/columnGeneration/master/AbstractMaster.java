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
 * AbstractMaster.java
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
package org.jorlib.frameworks.columnGeneration.master;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jorlib.frameworks.columnGeneration.branchAndPrice.branchingDecisions.BranchingDecision;
import org.jorlib.frameworks.columnGeneration.branchAndPrice.branchingDecisions.BranchingDecisionListener;
import org.jorlib.frameworks.columnGeneration.colgenMain.AbstractColumn;
import org.jorlib.frameworks.columnGeneration.io.TimeLimitExceededException;
import org.jorlib.frameworks.columnGeneration.master.cutGeneration.CutHandler;
import org.jorlib.frameworks.columnGeneration.master.cutGeneration.Inequality;
import org.jorlib.frameworks.columnGeneration.model.ModelInterface;
import org.jorlib.frameworks.columnGeneration.pricing.AbstractPricingProblem;
import org.jorlib.frameworks.columnGeneration.util.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class represting the Master Problem.
 *
 * @author Joris Kinable
 * @version 13-4-2015
 *
 * @param <T> Type of data model
 * @param <V> Type of pricing problem
 * @param <U> Type of columns
 * @param <W> Type of Master Data
 */
public abstract class AbstractMaster<T extends ModelInterface, U extends AbstractColumn<T, V>, V extends AbstractPricingProblem<T>, W extends MasterData<T,U,V,?>> implements BranchingDecisionListener{

	/** Logger for this class **/
	protected final Logger logger = LoggerFactory.getLogger(AbstractMaster.class);
	/** Configuration file for this class **/
	protected final Configuration config=Configuration.getConfiguration();

	/** Data model **/
	protected final T modelData;
	/** Pricing Problems **/
	protected final List<V> pricingProblems;
	/** Data object which stores data from the Master Problem **/
	protected W masterData;
	/** Handle to a cutHandler which performs separation **/
	protected CutHandler<T,W> cutHandler;

	/**
	 * Creates a new Master Problem
	 * @param modelData data model
	 * @param pricingProblems pricing problems
	 */
	public AbstractMaster(T modelData, List<V> pricingProblems){
		this.modelData=modelData;
		this.pricingProblems=pricingProblems;
		masterData=this.buildModel();
		cutHandler=new CutHandler<>();
		cutHandler.setMasterData(masterData);
	}

	/**
	 * Creates a new Master Problem
	 * @param modelData data model
	 * @param pricingProblem pricing problem
	 */
	public AbstractMaster(T modelData, V pricingProblem){
		this(modelData, Collections.singletonList(pricingProblem));
	}

	/**
	 * Creates a new Master Problem
	 * @param modelData data model
	 * @param pricingProblems pricing problems
	 * @param cutHandler Reference to a cut handler
	 */
	public AbstractMaster(T modelData, List<V> pricingProblems, CutHandler<T,W> cutHandler){
		this.modelData=modelData;
		this.pricingProblems=pricingProblems;
		this.cutHandler=cutHandler;
		masterData=this.buildModel();
		cutHandler.setMasterData(masterData);
	}

	/**
	 * Creates a new Master Problem
	 * @param modelData data model
	 * @param pricingProblem pricing problem
	 * @param cutHandler Reference to a cut handler
	 */
	public AbstractMaster(T modelData, V pricingProblem, CutHandler<T,W> cutHandler){
		this(modelData, Collections.singletonList(pricingProblem), cutHandler);
	}

	/**
	 * Build the master problem
	 */
	protected abstract W buildModel();

	/**
	 * Solve the master problem
	 * @param timeLimit Future point in time by which this method must be finished
	 * @throws TimeLimitExceededException if time limit is exceeded
	 */
	public void solve(long timeLimit) throws TimeLimitExceededException{
		masterData.iterations++;
		masterData.optimal=this.solveMasterProblem(timeLimit);
	}
	
	/**
	 * Method implementing the solve procedure for the master problem
	 * @param timeLimit Future point in time by which this method must be finished
	 * @return Returns true if successfull (and optimal)
	 * @throws TimeLimitExceededException if time limit is exceeded
	 */
	protected abstract boolean solveMasterProblem(long timeLimit) throws TimeLimitExceededException;
	
	/**
	 * Get the reduced cost information required for a particular pricingProblem. The pricing problem often looks like:
	 * a_1x_1+a_2x_2+...+a_nx_n <= b, where a_i are dual variables, and b some constant. The dual information is stored in
	 * the PricingProblem object.
	 *
	 * @param pricingProblem Object in which the dual information required to solve the pricing problems is stored.
	 */
	public abstract void initializePricingProblem(V pricingProblem);
	
	/**
	 * Returns the objective value of the current master problem.
	 */
	public double getObjective(){
		return masterData.objectiveValue;
	}
	
	/**
	 * @return Returns the number of times the master problem has been solved
	 */
	public int getIterationCount(){
		return masterData.iterations;
	}

	/**
	 * @return Returns true if the master problem has been solved to optimality
	 */
	public boolean isOptimal(){
		return masterData.optimal;
	}
	
	/**
	* Method which can be invoked externally to check whether the current master problem solution violates any cuts.
	* A handle to a cutHandler must have been provided when constructing the master problem
	* @return true if cuts were added to the master problem, false otherwise
	*/
	public boolean hasNewCuts(){
		logger.debug("Checking for cuts");
		boolean hasNewCuts=false;
		if(cutHandler != null){
			hasNewCuts=cutHandler.generateCuts();
		}
		logger.debug("Cuts found: {}", hasNewCuts);
		return hasNewCuts;
	}
	
	/**
	 * Adds cuts to this master.
	 * A handle to a cutHandler must have been provided in the constructor of this class
	 * @param cuts cuts to be added
	 */
	public void addCuts(Collection<Inequality> cuts){
		cutHandler.addCuts(cuts);
	}
	
	/**
	 * Returns all the cuts in the master model
	 * A handle to a cutHandler must have been provided in the constructor of this class
	 */
	public List<Inequality> getCuts(){
		return cutHandler.getCuts();
	}
	
	/**
	 * Add a column to the model
	 * @param column column to add
	 */
	public abstract void addColumn(U column);

	/**
	 * Add a initial solution (list of columns)
	 * @param columns initial set of columns
	 */
	public void addColumns(List<U> columns){
		for(U column : columns){
			this.addColumn(column);
		}
	}

	/**
	 * Returns all columns generated for the provided pricing problem.
	 * @param pricingProblem Pricing problem
	 * @return Set of columns
	 */
	public Set<U> getColumns(V pricingProblem){
		return masterData.getColumnsForPricingProblem(pricingProblem);
	}

	/**
	 * After the master problem has been solved, a solution has to be returned, consisting of a set of columns selected by the master problem, i.e the columns with a
	 * non-zero value.
	 * @return solution consisting of non-zero columns
	 */
	public abstract List<U> getSolution();
	
	/**
	 * Verifies whether a particular solution is an integer solution.
	 * @return Return true if the solution derived by the master problem is integer
	 */
	public boolean solutionIsInteger(){
		throw new UnsupportedOperationException("Not implemented. You should override this function");
	}
	
	/**
	 * To compute a lower bound on the optimal solution of the relaxed master problem (assuming that the master problem is a minimization problem), multiple components
	 * are required, including information from the master problem. This function returns that information.
	 */
	public double getLowerBoundComponent(){
		throw new UnsupportedOperationException("Not implemented. You should override this function");
	}
	/**
	 * Export the master problem to a file e.g. an .lp file
	 * @param name Name of the exported file
	 */
	public void exportModel(String name){
		throw new UnsupportedOperationException("Not implemented. You should override this function");
	}
	
	/**
	 * Give a textual representation of the solution
	 */
	public abstract void printSolution();
	
	/**
	 * Close the master problem
	 */
	public abstract void close();


	/**
	 * Method invoked when a branching decision is executed.
	 * @param bd branching decision
	 */
	@Override
	public void branchingDecisionPerformed(BranchingDecision bd) {
	}

	/**
	 * Method invoked when a branching decision is reversed due to backtracking in the branch and price tree.
	 * @param bd branching decision
	 */
	@Override
	public void branchingDecisionRewinded(BranchingDecision bd) {
	}
}

