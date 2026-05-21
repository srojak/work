/**
 * 
 */
package srojak.numerics;

/**
 * @author Stephen
 *
 */
public interface DoublePrecisionComparer {
	
	/**
	 * The standard comparison tolerance value.
	 */
	public static final double EPSILON_DEFAULT = 1.0e-14d;
	
	/**
	 * Comparison tolerance values must be less than this value.
	 */
	public static final double EPSILON_LIMIT = 1.0e-5d;
	
	/**
	 * The comparer using the default epsilon value.
	 */
	public static final DoublePrecisionComparer DEFAULT_COMPARER
		= new DoubleComparer(EPSILON_DEFAULT);
	
	/**
	 * Gets the current comparison tolerance value.
	 * @return The current comparison tolerance value.
	 */
	double getEpsilon();
	
	/**
	 * Compares two numbers for numerical equality.
	 * @param d1 The first number to compare.
	 * @param d2 The second number to compare.
	 * @return {@code true) if the numbers are equal within the comparison tolerance.
	 */
	boolean areEqual(double d1, double d2);
	
	/**
	 * Compares two numbers, applying the comparison tolerance.
	 * @param d1 The first number to compare.
	 * @param d2 The second number to compare.
     * @return  the value {@code 0} if {@code d1} is
     *          numerically equal to {@code d2} within the comparison tolerance;
     *          ; a value less than {@code 0} if {@code d1} is numerically less than {@code d2};
     *          and a value greater than {@code 0} if {@code d1} is numerically greater than {@code d2}.
	 */
	int compare(double d1, double d2);
}
