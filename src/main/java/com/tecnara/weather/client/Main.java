package com.tecnara.weather.client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        ClientThread clientThread1= new ClientThread();
        ClientThread clientThread2= new ClientThread();
        ClientThread clientThread3= new ClientThread();

        clientThread1.start();
        clientThread2.start();
        clientThread3.start();
    }
}
