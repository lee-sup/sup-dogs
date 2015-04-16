# sup-dogs
What Sup Dogs?

With today's modern, connected world, sensors are everywhere (or should be) and we owe it to ourselves to make sure that our dogs are safe and active within off-leash areas. This simple REST service provides you with the information related to a group of dogs being montiored within a bounded dog park. 

This first version (v0.0.1) of the project, emulates a pack of dogs being montiored within an off-leash area. The monitoring is done in real-time and keeps track of vital information including location, heart rate and temperature.

Each dog in the park is provided an id that is unique to the park. They follow the format of idXXX where x is a value from 0 to 9. For example, valid IDs look like id005, id014 and id890.

##Using the SUP Dogs Service   
If you're wanting to use the service as-is, it is easy to get started. The interface provides two simple GET methods. You can sample them using:

http://sup-dog-park.azurewebsites.net/sensorup/dogs/id013 : Interested in getting information for a specific dog? Then try this approach. Just add the dog ID to the URL and it'll give you the details for just that one dog. The data is returned as a JSON object. For example: {"id":"id013","heartRate":91,"temperature":38.35852115673793,"location":{"x":27,"y":63}}

http://sup-dog-park.azurewebsites.net/sensorup/dogs  : Using this URL will provide you with the current status for all of the dogs in the park. The data is returned as a JSON array (the example currently returns data on 100 dogs): 
[{"id":"id013","heartRate":118,"temperature":38.382040925228594,"location":{"x":40,"y":84}},{"id":"id084","heartRate":123,"temperature":38.38457824664712,"location":{"x":39,"y":68}},
.....
{"id":"id099","heartRate":128,"temperature":38.39509793147776,"location":{"x":92,"y":57}},{"id":"id075","heartRate":101,"temperature":39.02846803654054,"location":{"x":19,"y":22}}]

<b>Note</b>: The Dogs listed in the JSON array are not sorted. Your client should not count on a specific ordering.

The provided maven script will package the service up as a WAR file that can be deployed quickly on Tomcat. The POM file lists and manages all of the dependencies. 

##Guide to the Code
If you are looking to do more than just use the service as-is, then you'll need to understand some of the code. The project structure is set up with two main packages: 
<ol><li>com.sensorup.dogs.domain: This package contains the domain specific classes: <b>Park</b>, <b>Coordinate</b> and <b>Dog</b>:</li>
<ol><li>Park: A simple class used to define a bounded area that a dog can run within. Think of this as the off-leash area. It could be an outdoor park, an indoor park, etc. The key thing is that it has rectangular dimensions starting from some minimum coordinate (x,y) to a maximum coordinate (x,y). A useful aspect of this class is that you can query it to determine whether a current location is within the boundaries of the park. We don't want to have any of the dogs sneaking out of the park.</li>
<li>Coordinate: A helper class to manage and share coordinate information (x,y).</li>
<li>Dog: This is a representation of each dog in the park. Each dog has a unique ID and will be within a park. In addition, each dog has a set of monitored properties (location, heart rate, temperature). Remember that right now this monitoring information is emulated. Within the class you'll find private methods that adjust heart rate, temperature and location. In later versions these will be replaced with methods that interface with the real sensors.</li></ol> 
<li>com.sensorup.dogs.services: Within this package you'll find the DogSensorService. This is our RESTful service and it depends on Jersey and Jackson. There are two GET operations (as discussed above). They are very simple; just returning either the entire collection of dogs or an individual dog.</li>
</ol>

As this is emulating the set of dogs in the park there are a couple of interesting things to watch for in Dog and DogSensorService. You'll notice that in the constructor for DogSensorService we create and then spin up a thread for each dog. This populates our collection and keeps our dogs moving around in the park. You'll then want to take another look at Dog and its run() method. For as long as the service is running, each dog will continue to have its properties updated.

One last thing, a reference to each of the dogs is placed in a collection within DogSensorService. Notice that the collection used is a ConcurrentHashMap. We need to keep this thread safe as the servlet has been designated as a singleton and many threads may end up accessing the collection (it should be fine, we're just being cautious).

New Feature coming: Secure Login.

Give me a shout if you have any questions about the code or the project.

