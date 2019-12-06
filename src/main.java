import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;


public class main{

    public static void main(String args[]){
    	User Pierre = new User("a","b","c");
    	User Thomas = new User("e","f","g");
        int port_client = Integer.parseInt(args[0]);
        int port_server = Integer.parseInt(args[1]);
        
        server_udp server = new server_udp(port_server);
        client_udp client = new client_udp(port_client,Pierre,Thomas);
        //System.out.println(Pierre.get_local_ip());
        
    }

}