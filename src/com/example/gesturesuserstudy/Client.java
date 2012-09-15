package com.example.gesturesuserstudy;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;

 
import android.util.Log;
 
public class Client implements Runnable {
        
      private static byte[]receivedata=new byte[1024];
      //private static Datagrampacket recv_packet;  
      private static String sendData;
       
      Client(String str)
      {
    	  sendData=str;
      
    	  
      }
      
        public void run() {
                try {
                        // Retrieve the ServerName
                        InetAddress serverAddr = InetAddress.getByName("192.168.43.102");
                        
                        Log.d("UDP", "C: Connecting...");
                        /* Create new UDP-Socket */
                        DatagramSocket socket = new DatagramSocket();
                       
                        /* Prepare some data to be sent. */
                        byte[] buf = sendData.getBytes();
                       
                        /* Create UDP-packet with
                         * data & destination(url+port) */
                        DatagramPacket packet = new DatagramPacket(buf,buf.length,serverAddr,12002);
                        //Log.d("UDP", "C: Sending: '" + new String(buf) + "'");
                       
                        /* Send out the packet */
                                           	
                    	socket.send(packet);
                        
                        Log.d("UDP", packet.getData().toString());
                    
                        
                        //Log.d("UDP", "C: Done.");
                } catch (Exception e) {
                        Log.e("UDP", "C: Error", e);
                }
        }
}

