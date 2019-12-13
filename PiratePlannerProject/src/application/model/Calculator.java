package application.model;
/**
 * Class that contains 4 functions for the 4 operation calculator
 * @author Zachary Zeller
 * Pirate Planer calculator
 */
public class Calculator {
	/**
	 * Calculates the sum of two numbers
	 * @param x double
	 * @param y double
	 * @return sum: double
	 */
	public static double add(double x, double y) {
		double sum = 0;
		sum = x+y;
		return sum;
	}
	/**
	 * Calculates the difference of two numbers
	 * @param x double
	 * @param y double
	 * @return difference: double
	 */
	public static double subtract(double x, double y) {
		double difference = 0;
		difference = x-y;
		return difference;
	}
	/**
	 * Calculates the product of two numbers
	 * @param x double 
	 * @param y double
	 * @return product: double
	 */
	public static double multiply(double x, double y) {
		double product = 0;
		product = x*y;
		return product;
	}
	/**
	 * Calculates the quotient of two numbers
	 * @param x double
	 * @param y double
	 * @return quotient: double
	 */
	public static double divide(double x, double y) {
		double quotient = 0;
		quotient = x/y;
		return quotient;
	}
	
}
