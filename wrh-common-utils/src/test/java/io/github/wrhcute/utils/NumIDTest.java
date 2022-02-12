package io.github.wrhcute.utils;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NumIDTest {



    @Test
    public void test(){
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        ConcurrentHashMap<Long,Long> map = new ConcurrentHashMap<>();
        NumID numID = new NumID(89);
        for (int i = 0; i < 20; i++) {
           executorService.submit(() -> {
               for (;;){
                   long id = numID.take();
                   Long put = map.put(id, id);
                   System.out.println("线程"+ Thread.currentThread().getName() + "放入 " + id);
                   if (put != null){
                       System.err.println("重复ID：" + put);
                       System.exit(-1);
                   }
               }
           });
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void test1(){
        System.out.println(1 << 12);
    }
}
