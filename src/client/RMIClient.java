package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import shared.MapServer;

/**
 * RMIClient. The RMIClient contains server, which the client uses to perform operations.
 */
public class RMIClient {
  private MapServer server;
  private String IP_ADDRESS;
  private Integer PORT_NUMBER;

  public RMIClient(String IP_ADDRESS, Integer PORT_NUMBER) {
    this.IP_ADDRESS = IP_ADDRESS;
    this.PORT_NUMBER = PORT_NUMBER;
  }


  /**
   * Retrieves the server from the registry.
   * @throws RemoteException Exception may occur during the execution of a remote method call.
   * @throws NotBoundException Thrown if an attempt is made to lookup or unbind in the registry a
   *                           name that has no associated binding.
   */
  public void startClient() throws RemoteException, NotBoundException {
    Registry registry = LocateRegistry.getRegistry(IP_ADDRESS, PORT_NUMBER);
    server = (MapServer) registry.lookup("Server");
  }

  /**
   * Calls the server's getFormattedCurrentSystemTime method.
   * @return String of current system time in "yyyy-MM-dd HH:mm:ss.SSS" format.
   */
  public String getFormattedCurrentSystemTime() {
    String result = null;
    try {
      result = server.getFormattedCurrentSystemTime();
    } catch (RemoteException e) {
      e.printStackTrace();
      throw new RuntimeException("Could not contact server");
    }
    return result;
  }

  /**
   * Prints timestamped PUT logs for the server, and returns String response for the client
   * @param clientINetAddress - client's internet address
   * @param clientPORT_NUMBER - client's port number
   * @param splitClientMessage - String array containing the PUT command sent by the client
   * @return String representing our response to the client
   */
  public synchronized String PUT(String clientINetAddress, String clientPORT_NUMBER, String[] splitClientMessage) {
    String result = null;
    try {
      result = server.PUT(clientINetAddress, clientPORT_NUMBER, splitClientMessage);
    } catch (RemoteException e) {
      e.printStackTrace();
      throw new RuntimeException("Could not contact server");
    }
    return result;
  }

  /**
   * Prints timestamped GET logs for the server, and returns String response for the client
   * @param clientINetAddress - client's internet address
   * @param clientPORT_NUMBER - client's port number
   * @param splitClientMessage - String array containing the GET command sent by the client
   * @return String representing our response to the client
   */
  public synchronized String GET(String clientINetAddress, String clientPORT_NUMBER, String[] splitClientMessage) {
    String result = null;
    try {
      result = server.GET(clientINetAddress, clientPORT_NUMBER, splitClientMessage);
    } catch (RemoteException e) {
      e.printStackTrace();
      throw new RuntimeException("Could not contact server");
    }
    return result;
  }

  /**
   * Prints timestamped DELETE logs for the server, and returns String response for the client
   * @param clientINetAddress - client's internet address
   * @param clientPORT_NUMBER - client's port number
   * @param splitClientMessage - String array containing the DELETE command sent by the client
   * @return String representing our response to the client
   */
  public synchronized String DELETE(String clientINetAddress, String clientPORT_NUMBER, String[] splitClientMessage) {
    String result = null;
    try {
      result = server.DELETE(clientINetAddress, clientPORT_NUMBER, splitClientMessage);
    } catch (RemoteException e) {
      e.printStackTrace();
      throw new RuntimeException("Could not contact server");
    }
    return result;
  }
}
