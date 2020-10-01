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

public class ServerThread extends Thread{

    DataInputStream dis;
    DataOutputStream dos;
    Socket socket;


    public ServerThread(DataOutputStream dos, DataInputStream dis,Socket socket){
        this.dis=dis;
        this.dos=dos;
        this.socket=socket;

    }

    public void run(){
        Coordinates coordinates = getRequestCoordinates();
        String result = null;
        try {
            result = OpenWeatherMap.getCurrentWeather(coordinates);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            dos.writeUTF(sendResponse(result).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public Coordinates getRequestCoordinates(){

        Coordinates coordinates = null;
        String coordinatesMsg;

        try {

            coordinatesMsg = dis.readUTF();
            if (Checker.checkFormat(coordinatesMsg)){
                coordinates = toCoordinates(coordinatesMsg);
                if (!Checker.checkRange(coordinates)) {
                    dos.writeUTF("The range isn't correct.");
                }
            } else {
                dos.writeUTF("The sintax isn't correct, write numbers.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return coordinates;
    }

    public JSONInfoClass sendResponse(String jsonApiWeather){


        Gson gson = new Gson();
        JSONInfoClass jsonInfoClass = gson.fromJson(jsonApiWeather,JSONInfoClass.class);
        return jsonInfoClass;
    }


    public Coordinates toCoordinates(String coordinatesMsg){
        return Utils.parseCoordinates(coordinatesMsg);
    }





    public void closeConnection(){
        try {
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
