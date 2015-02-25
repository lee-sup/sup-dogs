package com.sensorup.dogs.services;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.sensorup.dogs.domain.Dog;
import com.sensorup.dogs.domain.Park;
import com.sun.jersey.spi.resource.Singleton;

@Path("/dogs")
@Singleton
public class DogSensorService {

	// we'll use a map to manage the collection of dogs
	// makes it easy for us to get all of the dogs
	// while also supporting us if we need to retrieve a single dog by ID
	private Map<String, Dog> theDogs = new ConcurrentHashMap<String, Dog>();

	/*
	 * Use the constructor to initialize the collection of dogs and to kick off
	 * the thread that will monitor the "sensors".
	 */
	public DogSensorService() {
		// set up our new park including its dimensions
		Park thePark = new Park(0, 100, 0, 100);

		Dog aDog;
		Thread t;
		// we want to generate 100 dogs and have them run around the park
		// each dog will have its own thread which will handle updating its info
		for (int i = 1; i < 101; i++) {
			aDog = new Dog(thePark, "id" + String.format("%03d", i));

			// add the new dog to the collection
			theDogs.put(aDog.getId(), aDog);

			// and now let the dog loose in the park
			t = new Thread(aDog);
			t.start();
		}
	}

	/*
	 * Provide the option to retrieve a single dog based on its id
	 */
	@GET
	@Path("{id}")
	@Produces("application/json")
	public Dog getDog(@PathParam("id") String id) {

		// use the id to look up the dog
		Dog aDog = theDogs.get(id);
		// return the requested dog. Jackson will convert it to JSON
		return aDog;
	}

	/*
	 * Retrieve all of the dogs
	 */
	@GET
	@Produces("application/json")
	public Collection<Dog> getDogs() {

		//return the list of dogs. Jackson will convert it to JSON
		System.out.println("Returning the dogs");
		return theDogs.values();
	}

}
