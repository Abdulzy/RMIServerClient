package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import shared.MapServer;

/**
 * ServerImpl. Implements methods of MapServer and contains all the functionality
 * that the client will access via RMI.
 */
public class ServerImpl implements MapServer {

  Map<String, Integer> myMap;

  public ServerImpl() throws RemoteException {
    this.myMap = new HashMap<>();
    UnicastRemoteObject.exportObject(this, 0);
  }

  /**
   * Returns a String of the current system time in "yyyy-MM-dd HH:mm:ss.SSS" format
   *
   * This code from:
   * https://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java
   *
   * @return String of current system time in "yyyy-MM-dd HH:mm:ss.SSS" format
   */
  @Override
  public String getFormattedCurrentSystemTime() throws RemoteException {
    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    // Get the current date
    Date currentDate = new Date();
    String currentDateTimeOutput = timeFormat.format(currentDate);
    return currentDateTimeOutput;
  }

  /**
   * Returns a boolean representing whether a given string consists of all letters and digits
   * @param string - a string that we want to verify as alphanumeric
   * @return boolean representing whether a string is alphanumeric
   */
  @Override
  public boolean isAlphaNumeric(String string) throws RemoteException {
    for (char c : string.toCharArray()) {
      if (!Character.isLetter(c) && !Character.isDigit(c)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if a string can be converted into an integer
   * @param string - string that we want to convert to integer
   * @return boolean representing whether a string is an integer or not
   */
  @Override
  public boolean isInteger(String string) throws RemoteException {
    long value = 0;
    for (char c : string.toCharArray()) {
      if (!Character.isDigit(c)) {
        return false;
      }
      if (value > 2147483647) { // integers only go up to this value
        return false;
      }
      value = value*10 + Integer.parseInt(String.valueOf(c));
    }
    if (value > 2147483647) { // integers only go up to this value
      return false;
    }
    return true;
  }

  /**
   * Prints timestamped PUT logs for the server, and returns String response for the client
   * @param clientINetAddress - client's internet address
   * @param clientPORT_NUMBER - client's port number
   * @param splitClientMessage - String array containing the PUT command sent by the client
   * @return String representing our response to the client
   */
  @Override
  public synchronized String PUT(String clientINetAddress, String clientPORT_NUMBER, String[] splitClientMessage) throws RemoteException {
    String timeStampClientINetPortNumber = "Timestamp=" + getFormattedCurrentSystemTime() + " (From " + clientINetAddress + " " + clientPORT_NUMBER;
    String responseToClient = "";
    if (splitClientMessage.length != 3) {
      System.out.println(timeStampClientINetPortNumber + ") Server received bad PUT request; PUT request does not have appropriate number of arguments");
      responseToClient = "Unsuccessful operation: PUT request does not have appropriate number of arguments";
    } else {
      String key = splitClientMessage[1];
      String stringValue = splitClientMessage[2];
      boolean isAlphaNum = isAlphaNumeric(splitClientMessage[1]);
      boolean isInt = isInteger(splitClientMessage[2]);
      if (isAlphaNum && isInt) { // successful PUT operation
        int value = Integer.valueOf(stringValue);
        myMap.put(key, value);
        System.out.println(timeStampClientINetPortNumber + ") Server successfully PUT " + key + " " + value + " into the map");
        responseToClient = "Successful PUT operation: " + key + " " + value;
      } else if (!isAlphaNum && !isInt) {
        System.out.println(timeStampClientINetPortNumber + ") Server received bad PUT request; bad PUT key & bad PUT value");
        responseToClient = "Unsuccessful operation: PUT's key must contain only alphanumeric characters and PUT's value must be a non-negative integer";
      } else if (!isAlphaNum) {
        System.out.println(timeStampClientINetPortNumber + ") Server received bad PUT request; bad PUT key");
        responseToClient = "Unsuccessful operation: PUT's key must contain only alphanumeric characters";
      } else {
        System.out.println(timeStampClientINetPortNumber + ") Server received bad PUT request; bad PUT value");
        responseToClient = "Unsuccessful operation: PUT's value must be a non-negative integer";
      }
    }
    return responseToClient;
  }

  /**
   * Prints timestamped GET logs for the server, and returns String response for the client
   * @param clientINetAddress - client's internet address
   * @param clientPORT_NUMBER - client's port number
   * @param splitClientMessage - String array containing the GET command sent by the client
   * @return String representing our response to the client
   */
  @Override
  public synchronized String GET(String clientINetAddress, String clientPORT_NUMBER, String[] splitClientMessage) throws RemoteException {
    String timeStampClientINetPortNumber = "Timestamp=" + getFormattedCurrentSystemTime() + " (From " + clientINetAddress + " " + clientPORT_NUMBER;
    String responseToClient = "";
    if (splitClientMessage.length != 2) {
      System.out.println(timeStampClientINetPortNumber + ") Server received bad GET request; GET request does not have appropriate number of arguments");
      responseToClient = "Unsuccessful operation: GET request does not have appropriate number of arguments";
    } else {
      String key = splitClientMessage[1];
      boolean isAlphaNum = isAlphaNumeric(splitClientMessage[1]);
      Integer value = myMap.get(key);
      if (value != null) { // successful GET operation
        System.out.println(timeStampClientINetPortNumber + ") Server successfully retrieved GET's (" + key + ")'s value (" + value + ")");
        responseToClient = "Successful GET operation. key=" + key + " value=" + String.valueOf(value);
      } else if (!isAlphaNum) {
        System.out.println(timeStampClientINetPortNumber + ") Server received bad GET request; bad GET key");
        responseToClient = "Unsuccessful operation: GET's key must contain only alphanumeric characters";
      } else {
        System.out.println(timeStampClientINetPortNumber + ") Server received bad GET request; GET's key does not exist");
        responseToClient = "Unsuccessful operation: GET's key does not exist";
      }
    }
    return responseToClient;
  }

  /**
   * Prints timestamped DELETE logs for the server, and returns String response for the client
   * @param clientINetAddress - client's internet address
   * @param clientPORT_NUMBER - client's port number
   * @param splitClientMessage - String array containing the DELETE command sent by the client
   * @return String representing our response to the client
   */
  @Override
  public synchronized String DELETE(String clientINetAddress, String clientPORT_NUMBER, String[] splitClientMessage) throws RemoteException {
    String timeStampClientINetPortNumber = "Timestamp=" + getFormattedCurrentSystemTime() + " (From " + clientINetAddress + " " + clientPORT_NUMBER;
    String responseToClient = "";
    if (splitClientMessage.length != 2) {
      System.out.println(timeStampClientINetPortNumber + ") Server received bad DELETE request; DELETE request does not have appropriate number of arguments");
      responseToClient = "Unsuccessful operation: DELETE request does not have appropriate number of arguments";
    } else {
      String key = splitClientMessage[1];
      boolean isAlphaNum = isAlphaNumeric(splitClientMessage[1]);
      Integer value = myMap.remove(key);
      if (value != null) { // successful DELETE operation
        System.out.println(timeStampClientINetPortNumber + ") Server successfully deleted key:" + key + " value:" + value + " from the map");
        responseToClient = "Successful DELETE operation. Key=" + key + " value=" + value + " deleted from the map";
      } else if (!isAlphaNum) {
        System.out.println(timeStampClientINetPortNumber + ") Server received bad DELETE request; bad DELETE key");
        responseToClient = "Unsuccessful operation: DELETE's key must contain only alphanumeric characters";
      } else {
        System.out.println(timeStampClientINetPortNumber + ") Server received bad DELETE request; DELETE's key does not exist");
        responseToClient = "Unsuccessful operation: DELETE's key does not exist";
      }
    }
    return responseToClient;
  }


}
