package org.openjfx;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int n = 5;
        for(int i = 0; i< n; i++){
//            Multithreading object = new Multithreading();
//            object.start();
            Multithreadiing_Runnable object1= new Multithreadiing_Runnable();
            Multithreadiing_Runnable object2= new Multithreadiing_Runnable();

            Thread thread1 = new Thread(object1);
            Thread thread2 = new Thread(object2);

            thread1.start();
            System.out.println("seperate thread");
            thread2.start();
        }
    }
}