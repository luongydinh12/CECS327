import java.net.*;
import java.io.*;
import java.io.Console;
public class UDPClient {
    public static void main(String args[]) {
        // args give message contents and server hostname
        System.out.println("This is a Client");
        System.out.println("Please enter the IP Address provided by Server:");
        Console console = System.console();
        String IPAddress = console.readLine();     
        System.out.println("Please enter the port number provided by Server:");
        int serverPort = Integer.parseInt(console.readLine());

        DatagramSocket aSocket = null;
        int cont = 1;
        try {
            aSocket = new DatagramSocket();
            while(cont != 0){
            System.out.println("Please enter your message:");
            String msg = console.readLine();
            byte[] m = msg.getBytes();
            InetAddress aHost = InetAddress.getByName(IPAddress);
            DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
            aSocket.send(request);
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);
            System.out.println("Reply: " + new String(reply.getData()));
            System.out.println("Do you want to continue? (input 0 for No, other number for Yes) : ");
            cont = Integer.parseInt(console.readLine());
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
}