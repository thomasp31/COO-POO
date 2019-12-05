import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;


public class main{

    public static void main(String args[]){
        int port_client = Integer.parseInt(args[0]);
        int port_server = Integer.parseInt(args[1]);
        server_udp server = new server_udp(port_server);
        client_udp client = new client_udp(port_client);
    }

}