// Group 5: Tyler Ton, Dinh Luong
// CECS 327 - Assignment 3
// This is an UDP Server code provided by instructor
// Modified by Dinh Luong and Tyler Ton

import java.net.*;
import java.io.*;
import java.net.InetAddress; 
import java.io.Console;

public class UDPServer {

    public static void main(String args[]) {

        DatagramSocket aSocket = null;
        String host;
	// get the IP Address from the device running this program
        try {
            host = InetAddress.getLocalHost().getHostAddress();
          }
          catch (UnknownHostException ex) {
            host = "unknown";
          }
	// Show user this is a Server
        System.out.println("\nThis is an Echo Server");
	// Show user the server IP Address
        System.out.println("Server IP Address : " +  host);
	// Get the port number from user by calling checkPortNumber method
        int portNumber = checkPortNumber();
	// Tell user the port that the server is listening to
	System.out.println("\nNow server is listening to port: " +  portNumber + "\n"); 
        int i = 0;
        try {
            aSocket = new DatagramSocket(portNumber);
            byte[] buffer = new byte[1000];      
            while (true) {
		// prepare a Datagram Packet for receiving a message from client
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		// receive the Datagram Packet which contains client's message
                aSocket.receive(request);
		// prepare a Datagram Packet for sending a reply back to client
                DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(),
                        request.getPort());
		// send Datagram Packet to client
                aSocket.send(reply);
		// count the total message received from client
                i++;
		// show user the total message received from client
                System.out.println("Message(s) processed : "+ i);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null)
                aSocket.close();
        }
    }

    // This method is used to get port number from user
    // and check if it's valid or not
    public static int checkPortNumber() {
	boolean checker = false;
	String serverPort = "";
	// This loop will run until user inputs a valid port number
	while(!checker) {
	    // Ask user for a port number
	    System.out.print("\nPlease enter a port number: ");
	    Console console = System.console();
            serverPort = console.readLine();
	    // check if the port number is valid or not
	    try {
		if(Integer.parseInt(serverPort) >= 0 && Integer.parseInt(serverPort) <= 65535) {
		    checker = true;
		}
	    }
	    catch (NumberFormatException e) { }
	    // if the port number is invalid, show error message to user
	    if(!checker) {
		System.out.println("\nYou have entered an invalid port number. Please try again.");
		System.out.println("Port number should be an integer from 0 to 65535.");
	    }
	}
	// return the valid port number
	return Integer.parseInt(serverPort);
    }
}