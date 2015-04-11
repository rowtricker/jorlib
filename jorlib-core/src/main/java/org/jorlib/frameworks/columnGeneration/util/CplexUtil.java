package org.jorlib.frameworks.columnGeneration.util;

public class CplexUtil {
	
	private static final Configuration config=Configuration.getConfiguration();

	/**
	 * Returns the nearest rounded double. Throws an exception if the nearest double is further away than a given constant
	 */
	public static double doubleToRoundedDouble(double value){
		double result= Math.round(value);
		if(Math.abs(value-result)<config.PRECISION)
			return result;
		else
			throw new RuntimeException("Failed to round double, not near an integer value: " + value);
	}
	
	/**
	 * Returns the nearest rounded int. Throws an exception if the nearest int is further away than a given constant
	 */
	public static int doubleToInt(double value){
		int result= (int)Math.round(value);
		if(Math.abs(value-result)<config.PRECISION)
			return result;
		else
			throw new RuntimeException("Failed to round double, not near an integer value: " + value);
	}
	
	/**
	 * Returns true if the variable is +/- 1, false if the variable is +/- 0, and throws an error otherwise
	 */
	public static boolean doubleToBoolean(double value){
		if(Math.abs(1-value) < config.PRECISION ){
			return true;
		}
		else if(Math.abs(value) < config.PRECISION){
			return false;
		}
		else throw new RuntimeException("Failed to convert to boolean, not near zero or one: " + value);
	}
	
	/**
	 * Returns true if variable is fractional, i.e more than epsilon away from the nearest int value, false otherwise.
	 */
	public static boolean isFractional(double value){
		return Math.abs(value-Math.round(value)) > config.PRECISION;
	}
}
