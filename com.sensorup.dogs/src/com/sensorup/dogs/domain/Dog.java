package com.sensorup.dogs.domain;

import java.util.Random;

public class Dog implements Runnable {

	private String id;
	private int heartRate;
	private double temperature;
	private Coordinate currentLocation;
	private Park park;

	public String getId() {
		return id;
	}

	public int getHeartRate() {
		return heartRate;
	}

	public double getTemperature() {
		return temperature;
	}

	public Coordinate getLocation() {
		return currentLocation;
	}


	/*
	 * Create a Dog. We can specify the park and an ID for the dog.
	 * 
	 * The heartRate, Temp and Location are all read from the sensor and cannot
	 * be set otherwise
	 */
	public Dog(Park aPark, String anId) {
		this.park = aPark;
		this.id = anId;

		// set the other properties based on sensor readings
		initializeSensorValues();
	}

	/*
	 * In reality we'd pick up values from the sensor. In this example, we'll
	 * just make up some values
	 */
	private void initializeSensorValues() {
		heartRate = getRandomHeartRate();
		temperature = getRandomTemperature();
		currentLocation = getInitialLocation();
	}

	private int getRandomHeartRate() {

		// A normal heart rate for dogs is between 60 and 140 beats per minute.
		int randomHeartRate = (int) (Math.random() * (140 - 60)) + 60;

		return randomHeartRate;
	}

	private double getRandomTemperature() {

		// typical dog temperatures are 38.3 to 39.2
		double randomTemp = (double) (Math.random() * (39.2 - 38.3)) + 38.3;

		return randomTemp;
	}

	private Coordinate getInitialLocation() {
		// random location within the park as a starting point
		// we'll assume that it takes a few seconds for the sensor to start
		// transmitting
		// location. So, the dog could be anywhere within the area for this
		// initial reading.
		int x = (int) (Math.random() * (park.getMaxX() - park.getMinX()))
				+ park.getMinX();
		int y = (int) (Math.random() * (park.getMaxY() - park.getMinY()))
				+ park.getMinY();

		return new Coordinate(x, y);
	}

	/*
	 * Real world, we'd look to get info from the sensor on the current
	 * location. Here, we'll look at the current location and then randomly move
	 * (or not move). A dog can move 0 - 3 spots per measurement cycle (x, y, or
	 * both).
	 */
	private void updateLocation() {
		// get our random x value
		Random randX = new Random();
		int x = randX.nextInt(3);

		// get our random y value
		Random randY = new Random();
		int y = randY.nextInt(3);

		// and we need to know if it is moving forward (+ ) or backwards (-)
		// let's get two more random numbers 0 (backwards) or 1 (forwards) for both x and y
		Random randDirX = new Random();
		int dirX = randDirX.nextInt(2);

		Random randDirY = new Random();
		int dirY = randDirY.nextInt(2);

		Coordinate newLocation = new Coordinate(currentLocation.getX(),
				currentLocation.getY());
		if (dirX == 0) {
			newLocation.setX(newLocation.getX() - x);
		} else {
			newLocation.setX(newLocation.getX() + x);
		}

		if (dirY == 0) {
			newLocation.setY(newLocation.getY() - y);
		} else {
			newLocation.setY(newLocation.getY() + y);
		}

		// and last but not least, we need to check that this is a valid new
		// location, if not, then let's assume that the dog hasn't moved
		if (park.contains(newLocation)) {
			currentLocation = newLocation;
		}else
		{
			//try to move the dog in the other direction
			if (dirX == 0) {
				newLocation.setX(newLocation.getX() + x);
			} else {
				newLocation.setX(newLocation.getX() - x);
			}

			if (dirY == 0) {
				newLocation.setY(newLocation.getY() + y);
			} else {
				newLocation.setY(newLocation.getY() - y);
			}
		}

	}

	@Override
	public void run() {

		// the dogs will run around the park forever and ever
		while (true) {
			// update the dog's heart rate, temperature and location
			heartRate = getRandomHeartRate();
			temperature = getRandomTemperature();
			updateLocation();

			//System.out.println("Status: " + toString());

			// dog's statistics will be updated every second
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				// let's set the interrupted status, let the caller determine if
				// they want to act on the status change
				Thread.currentThread().interrupt();
			}
		}
	}

	/*
	 * Simple method to use in debugging. What is the current status of this
	 * dog?
	 */
	public String toString() {
		return "ID: " + getId() + " Heart rate:" + getHeartRate()
				+ " Temperature: " + getTemperature() + " Location: "
				+ getLocation().toString();
	}

}
