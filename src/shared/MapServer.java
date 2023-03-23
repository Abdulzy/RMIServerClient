package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * MapServer. ServerImpl implements these methods, and RMIClient accesses them via RMI.
 */
public interface MapServer extends Remote {

  /**
   * Returns a String of the current system time in "yyyy-MM-dd HH:mm:ss.SSS" format
   *
   * This code from:
   * https://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java
   *
   * @return String of current system time in "yyyy-MM-dd HH:mm:ss.SSS" format
   */
  String getFormattedCurrentSystemTime() throws RemoteException;

  /**
   * Returns a boolean representing whether a given string consists of all letters and digits
   * @param string - a string that we want to verify as alphanumeric
   * @return boolean representing whether a string is alphanumeric
   */
  boolean isAlphaNumeric(String string) throws RemoteException;

  /**
   * Check if a string can be converted into an integer
   * @param string - string that we want to convert to integer
   * @return boolean representing whether a string is an integer or not
   */
  boolean isInteger(String string) throws RemoteException;

  /**
   * Prints timestamped PUT logs for the server, and returns String response for the client
   * @param clientINetAddress - client's internet address
   * @param clientPORT_NUMBER - client's port number
   * @param splitClientMessage - String array containing the PUT command sent by the client
   * @return String representing our response to the client
   */
  String PUT(String clientINetAddress, String clientPORT_NUMBER, String[] splitClientMessage) throws RemoteException;

  /**
   * Prints timestamped GET logs for the server, and returns String response for the client
   * @param clientINetAddress - client's internet address
   * @param clientPORT_NUMBER - client's port number
   * @param splitClientMessage - String array containing the GET command sent by the client
   * @return String representing our response to the client
   */
  String GET(String clientINetAddress, String clientPORT_NUMBER, String[] splitClientMessage) throws RemoteException;

  /**
   * Prints timestamped DELETE logs for the server, and returns String response for the client
   * @param clientINetAddress - client's internet address
   * @param clientPORT_NUMBER - client's port number
   * @param splitClientMessage - String array containing the DELETE command sent by the client
   * @return String representing our response to the client
   */
  String DELETE(String clientINetAddress, String clientPORT_NUMBER, String[] splitClientMessage) throws RemoteException;
}
