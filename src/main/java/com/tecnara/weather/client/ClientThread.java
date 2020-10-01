package com.tecnara.weather.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread{


    Socket socket;
    DataOutputStream dos;
    DataInputStream dis;
    Scanner sc= new Scanner(System.in);

    public ClientThread(){

        try {
            socket= new Socket("localhost",3334);
            dos= new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInputParameters(){

        System.out.println("Give me the latitude");
        float lat= sc.nextFloat();
        System.out.println("Give me the length");
        float lon= sc.nextFloat();
        return "{\"lon\":"+ lon + ", \"lat\":" + lat + "}";
    }

    public void sendRequest(String msg)  {

        try {
            dos.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResponse() throws IOException {
        try {
            join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dis.readUTF();
    }

    public void closeConnection(){
        try {
            if(dis != null)
                dis.close();
            if(dos != null)
                dos.close();
            if(socket != null)
                socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void run () {

        try {
            sendRequest(getInputParameters());
            System.out.println(getResponse()+"\n");
            closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
