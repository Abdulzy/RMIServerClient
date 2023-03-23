#Project 2
#Java RMI Server & Client 

##Assignment Overview
The purpose and scope of this assignment was to implement client-server architecture using Java RMI (Remote Method Invocation), 
communicating with RPC (Remote Procedure Call). RMI has a higher level of abstraction compared to that of sockets; 
one of the notable differences is communication of the architecture is hidden from the programmer/User. 
As mentioned above, RMI is Java’s object-oriented take on RPC, in which an object running in one JVM (Java Virtual Machine) can 
invoke a method on an object on a different JVM. In Java RMI both the server and client have to be written in Java. 
A major benefits that comes with using Java RMI is that the server can handle multiple clients at once which basically means 
it is multithreaded. However, we were still responsible for maintaining the concurrency and accuracy of the hash map data structure. 
In my implementation of Java RMI, I have a client which contains an instance of the server. This client uses the server’s methods to PUT, 
GET, and DELETE entries from the hash map (based on user input). Aside from the replacing sockets with Java RMI, project 2 is very 
similar to project 1 with basically all of the logic remaining untouched. Some of the new class/functions that we used in this Project 
were needed due to the implementation of Java RMI which are Registry, getRegistry(), registry.lookup(string), createRegistry(int), and 
registry.bind().

##Technical impression
The actual technicality of the code for this project wasn’t complex has it was just a repetition of project 1 just implementing the 
Java RMI. Since we did use Java RMI in homework 4, I kinda understood what we had to do. I just had to duplicate the structure of the 
homework and implement it with the methods from project 1 but I still had to deal with how to ensure concurrency with multiple client’s 
accessing the server’s hash map. 
A friend writing taking a corner stone project that implements distributed systems told me to watch this video 
(https://www.youtube.com/watch?v=eKWjfZ-TUdo) which demonstrated the power of Java’s synchronized  keyword. By attaching synchronized 
to a method signature, I could ensure that only one thread would be accessing all methods with the synchronized  keyword. I did see 
the multiple questions about concurrent map compared to basic map but with the synchronized feature I added everything worked fine and 
those questions made me understand the synchronization even more. I am still somewhat on the fence about it. Maybe if there was an 
addition of subtraction function then the synchronized function would seem more useful but for now I understand its usefulness and 
implemented it nonetheless. 


##How to Run

Here are the steps to run the program:

###(TCP) How to start the client-server architecture

1. Run the TCP_Server via "java -jar RunServer.jar <PORT_NUMBER>"

2. Run the TCP_Client via "java -jar RunClient.jar <IP_ADDRESS> <PORT_NUMBER>"

3. To execute a PUT command, type "PUT <String> <Integer>"

4. To execute a GET command, type "GET <String>"

5. To execute a DELETE command, type "DELETE <String>"

##Examples with description
Any of the servers can be choosen for the runs
This is just a quick explanation of how the code runs, I had a test run with:
PORT_NUMBER = 32000
IP_ADDRESS = 127.0.0.1
The IP_ADDRESS was my device's address as i ran this on my device. 
With all functionality being met. 
PUT Abdul 5000 - Adds abdul to the map
GET Abdul - returns the value of Abdul
DELETE Abdul - removes Abdul from the map
 
##Limitations/Notes
This project has some limitions and notes that would be listed below:
If the PORT_NUMBER isn't an Integer then there would be a flag.
If the PORT_NUMBER isn't an non-negative Integer less than 65536 then there would be a flag.
If the IP_ADDRESS isn't the traditional numbers seperated by "." then it would be flagged. 
You can only use GET, PUT, DELETE opperations followed by the required and valid inputs. 
As demonstrated above in examples with description.

##Citation
Used https://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java
for a part of my code, which helped with getting date and time in the right format

https://www.youtube.com/watch?v=eKWjfZ-TUdo, helped with making methods synchronised appropriately.

used the Notes provided on canvas and class videos, used multiple of them.