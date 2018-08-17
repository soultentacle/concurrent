package com.gw.lean.concurrent.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dell on 2018/8/14.
 */
public class SimpleThread extends Thread {

    public static void println(String str) {
        System.out.println(Thread.currentThread().getName() + "--------->" + str);
    }

    @Override
    public void run() {
        println("开始");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        println("结束");
    }

    public static void testYield() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                println("开始");
                Thread.currentThread().yield();
                println("结束");
            }
        });
        thread.start();
        thread.yield();
    }

    public static void testContextClassLoader(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                println("开始");
                println("结束");
            }
        });
        thread.setContextClassLoader(Object.class.getClassLoader());
        println(thread.getContextClassLoader().toString());
        println(Thread.currentThread().getContextClassLoader().toString());
    }


    public static void testJoin(){
        Runnable runnable = new Runnable() {
            private long result;
            @Override
            public void run() {
                try {
                    result =new Random(1000l).nextLong();
                    Thread.currentThread().sleep(result);
                } catch (Exception e) {
                }
                println("end");
            }
        };
        List<Thread> workers = new ArrayList<Thread>();
        for(int i = 0;i < 5 ;i++){
            Thread thread = new Thread(runnable,"worker" + i);
            thread.start();
            workers.add(thread);
        }

        for(Thread thread : workers){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        println("end");

    }


    //interrupt 终端阻塞的线程
    public static void testInterrupt(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;){
                   try {
                       Thread.sleep(1000);
                   }catch (InterruptedException e){
                    println("响应终端");
                    println("终端标志为"+ Thread.currentThread().isInterrupted());
                    throw new RuntimeException();
                   }
                }
            }
        });
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;){
                    synchronized (SimpleThread.class){
                        println("ok");
                    }
                }
            }
        });
        thread.interrupt();
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        thread.start();
        synchronized (SimpleThread.class){
            thread1.start();
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            thread1.interrupt();
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }






    public static void main(String[] args) {
//        testYield();
//        testContextClassLoader();
//        testJoin();
        testInterrupt();
    }

}
