/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Corey
 * 
 * This class is a marker to ensure safe threading
 * 
 */
public class MyThread extends Thread {
    public MyThread(Runnable thread) {
        super(thread);
    }
}
