package com.team1458.turtleshell2.util.types;

/**
 * Interface for representing a class that can have math operations applied to
 * itself. It should be closed under addition and subtraction. It features
 * scalar multiplication.
 * 
 * This class can possibly be dropped, but it is useful for defining a common
 * standard.
 * 
 * @author mehnadnerd
 *
 * @param <T>
 *            Type that this class can have maths operations with.
 */
public interface Mathable<T> {
	/**
	 * Add this object to another.
	 * @param toAdd
	 * @return The sum of this + toAdd.
	 */
	public T plus(T toAdd);

	/**
	 * Subtract another object from this.
	 * @param toSubtract
	 * @return The result of this - toSubtract.
	 */
	public T minus(T toSubtract);

	/**
	 * Multiply this number by a scalar.
	 * @param scalar
	 * @return The result of this * scalar.
	 */
	public T scale(double scalar);
}
