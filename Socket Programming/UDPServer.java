import java.net.*;
import java.io.*;
import java.net.InetAddress; 
import java.io.Console;
public class UDPServer {
    public static void main(String args[]) {

        DatagramSocket aSocket = null;
        String host;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
          }
          catch (UnknownHostException ex) {
            host = "unknown";
          }
        System.out.println("This is a Server");
        System.out.println("Server IP Address : " +  host); 
        System.out.println("Please enter a port number:");
        Console console = System.console();
        int portNumber = Integer.parseInt(console.readLine());
        int i = 0;
        try {
            aSocket = new DatagramSocket(portNumber);
            byte[] buffer = new byte[1000];      
            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(),
                        request.getPort());
                aSocket.send(reply);
                i++;
                System.out.println("Message(s) processed : "+i);
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
}