package org.openjfx;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int n = 5;
        for(int i = 0; i< n; i++){
//            Multithreading object = new Multithreading();
//            object.start();
            Multithreadiing_Runnable object= new Multithreadiing_Runnable();
            Thread thread = new Thread(object);
            thread.start();
        }
    }
}