package com.newcoder.toutiao;

/**
 * Created by 12274 on 2018/1/5.
 */
class myThread extends Thread{
    private int threadId;
    public myThread(int id){
        this.threadId=id;
    }
    @Override
    public void run() {
        try{
            Thread.sleep(1000);
            for(int i=0;i<3;i++){
                System.out.println(String.format("%d:%d",threadId,i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

public class MultiThread {
    public static void testThread() {
        /*for(int i=0;i<10;i++){
            new myThread(i).start();
        }*/
        for (int i = 0; i < 10; i++) {
            int tId=i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                        for(int j=0;j<3;j++){
                            System.out.println(String.format("%d:%d",tId,j));
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public static void main(String[] args){
        testThread();
    }
}
