package com.sensorup.dogs.domain;

/*
 * 
 * Simple class to keep track of the X and Y location of a dog. Also
 * useful for comparing the location to the known boundaries of a park.
 * 
 */
public class Coordinate {

	private int x;
	private int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return "X: " + getX() + " Y: " + getY();
	}
}
