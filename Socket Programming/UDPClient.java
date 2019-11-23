// Group 5: Tyler Ton, Dinh Luong
// CECS 327 - Assignment 3
// This is an UDP Client code provided by instructor
// Modified by Dinh Luong and Tyler Ton

import java.net.*;
import java.io.*;
import java.io.Console;

public class UDPClient {

    public static void main(String args[]) {
        // Show user this is a Client
        System.out.println("\nThis is an Echo Client");
        Console console = System.console();
	// Get the IP Address from user by calling checkIpAddress method
	String IPAddress = checkIpAddress();
	// Get the port number from user by calling checkPortNumber method
	int serverPort = checkPortNumber();

        DatagramSocket aSocket = null;
        // A boolean variable for checking if user wants to stop the program
	boolean stop = false;
        try {
            aSocket = new DatagramSocket();
            while(!stop) {
		// Ask user for a message to send to the server
		System.out.print("\nPlease enter your message:");
		String msg = console.readLine();
		// convert a string message into byte array
		byte[] m = msg.getBytes();
		// get the IP Address from string input earlier
		InetAddress aHost = InetAddress.getByName(IPAddress);
		// prepare a Datagram Packet with provided message, IP Address and port number
		DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
		// send Datagram Packet to server
		aSocket.send(request);
		byte[] buffer = new byte[1000];
		// prepare a Datagram Packet for receiving a reply from server
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		// receive the Datagram Packet that server replies
		aSocket.receive(reply);
		// print out server's reply
		System.out.println("Reply from Server: " + new String(reply.getData()));
		// Ask if user wants to stop the program
		stop = checkStop();
	    }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally

        {
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
	    System.out.print("\nPlease enter the port number provided by Server: ");
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

    // This method is used to get IP address from user
    // and check if it's valid or not
    public static String checkIpAddress() {
	boolean checker = false;
	int counter = 0;
	String IPAddress = "";
	// This loop will run until user inputs a valid IP Address
	while(!checker) {
	    // Ask user for an IP Address
	    System.out.print("\nPlease enter the IP Address provided by Server: ");
	    Console console = System.console();
            IPAddress = console.readLine();
	    String[] elements = IPAddress.split("\\.");
	    // check if the IP Address is valid or not
	    if(elements.length == 4) {
		try {
		    for(int i = 0; i < elements.length; i++) {
			if(Integer.parseInt(elements[i]) >= 0 && Integer.parseInt(elements[i]) <= 255) {
			    counter++;
			}
		    }
		    if(counter == 4) {
			checker = true;
		    }
		} catch (NumberFormatException e) { }
	    }
	    // if the IP Address is invalid, show error message to user
	    if(!checker) {
		counter = 0;
		System.out.println("\nYou have entered an invalid IP address. Please try again.");
		System.out.println("IP addres should be a string of 4 integers from 0 to 255 splitted by periods.");
	    }
	}
	// return the valid IP Address
	return IPAddress;
    }

    // This method is used to check if user wants to stop the program or not
    // and check if user's choice is valid or not
    public static boolean checkStop() {
	boolean checker = false, stop = false;
	String choice = "";
	// This loop will run until user inputs a valid option
	while(!checker) {
	    // Ask user for an option
	    System.out.print("\nDo you want to continue? (y/n): ");
	    Console console = System.console();
            choice = console.readLine();
	    choice = choice.toLowerCase();
	    // check if the option is valid or not
	    if(choice.equals("y") || choice.equals("yes")) {
		stop = false;
		checker = true;
	    }
	    else if(choice.equals("n") || choice.equals("no")) {
		stop = true;
		checker = true;
	    }
	    // if the option is invalid, show error message to user
	    else {
		System.out.println("\nInvalid option. Please try again.");
	    }
	}
	// return a valid option
	return stop;
    }
}