package com.tecnara.weather.server;

import com.google.gson.Gson;
import com.tecnara.weather.server.domain.Coordinates;
import com.tecnara.weather.server.services.meteo.JSONInfoClass;
import com.tecnara.weather.server.services.meteo.OpenWeatherMap;
import com.tecnara.weather.server.utils.Checker;
import com.tecnara.weather.server.utils.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    public Server(){
        try {
            serverSocket = new ServerSocket(3334);
            System.out.println("Listening...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void run(){
        while(true){
            try {
                socket = serverSocket.accept();
                dos = new DataOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());
                ServerThread serverThread = new ServerThread (dos,dis,socket);
                serverThread.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
        }

    }

    public static void main(String[] args) throws IOException {

        Server server = new Server();

        server.run();


        }


}
