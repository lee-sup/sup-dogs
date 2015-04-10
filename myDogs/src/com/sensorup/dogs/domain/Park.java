package com.sensorup.dogs.domain;

/*
 * Represents that park that the dogs will run within. Setting it up as immutable
 * as the size of the Park should not change.
 */
public class Park {

	private int maxX;
	private int minX;
	private int maxY;
	private int minY;

	public Park(int minX, int maxX, int minY, int maxY) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinX() {
		return minX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getMinY() {
		return minY;
	}

	/*
	 * check to see if the location is within the boundary 
	 * of the park. Returns true if the location is within, false if outside the boundary.
	 * 
	 * A location exactly on the boundary is considered outside the park
	 * 
	 */
	public Boolean contains(Coordinate aLocation)
	{
		if((aLocation.getX() > minX)&&(aLocation.getX() < maxX))
		{
			//if x is fine, then check on Y
			if((aLocation.getY() > minY)&&(aLocation.getY() < maxY))
			{
				//we know that the location is within the boundaries
				return true;
			}
		}
		//otherwise some parameter isn't within the park so return false
		return false;
	}
}
