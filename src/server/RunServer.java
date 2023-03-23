package server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import shared.MapServer;

/**
 * RunServer. This creates the registry and server, and binds the server to the
 * registry for the client to access.
 */
public class RunServer {

  /**
   * Main method creates the registry and server, and binds the server to the
   * registry for the client to access.
   * @param args (nothing)
   * @throws RemoteException Exception may occur during the execution of a remote method call.
   * @throws AlreadyBoundException Thrown if an attempt is made to lookup or unbind in the registry a
   *                           name that has no associated binding.
   */
  public static void main(String[] args) throws RemoteException, AlreadyBoundException {
    if (args.length != 1) {
      System.out.println("Please pass PORT_NUMBER as arguments through args");
    } else {
      String fParameter = args[0];
      if(fParameter.matches("^[a-zA-Z]*$")){
        throw new IllegalArgumentException("PORT_NUMBER has to only be numbers");
      }
      if(Integer.parseInt(args[0]) > 65536){
        throw new IllegalArgumentException("PORT_NUMBER must be a non-negative number less than 65536");
      }
      int PORT_NUMBER = Integer.valueOf(args[0]);
      MapServer server = new ServerImpl();
      Registry registry = LocateRegistry.createRegistry(PORT_NUMBER);
      registry.bind("Server", server);
      System.out.println("RMI server started!");
    }
  }
}
