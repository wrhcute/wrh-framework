package io.github.wrhcute.utils;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BiMapTest {


    @Test
    public void test(){
        BiMap<String,Integer> nameIdMap = new BiMap<>();
        nameIdMap.put("wrh",18);
        nameIdMap.put("keel",5);
        nameIdMap.put("ccc",9);

        nameIdMap.remove("wrh");
        nameIdMap.put("wrh",19);
        nameIdMap.put("kkk",19);
        List<Map.Entry<String, Integer>> entries = nameIdMap.lput("ccc", 19);
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry);
        }

        System.out.println("lget:wrh =>" + nameIdMap.lget("wrh"));
        System.out.println("lget:ccc =>" + nameIdMap.lget("ccc"));
        System.out.println("rget:19 =>" + nameIdMap.rget(19));
        System.out.println("rget:5 =>" + nameIdMap.rget(5));
        System.out.println("rget:18 =>" + nameIdMap.rget(18));
        for (String s : nameIdMap.keySet()) {
            System.out.println("key = " + s + ", vale = " + nameIdMap.get(s));
        }
    }


    @Test
    public void testPerformance(){
        BiMap<String,Integer> biMap = new BiMap<>();
        int count = 100, debug = count - 2 ;
        for (int i = 0; i < count; i++) {
            String l = UUID.randomUUID().toString();
            long s = System.currentTimeMillis();
            biMap.put(l,i);
            System.out.println("put 耗时:" + (System.currentTimeMillis() - s) + "ms,[" + l + "," + i + "]");
            long s1 = System.currentTimeMillis();
            Integer rs = biMap.get(l);
            System.out.println("get 耗时:" + (System.currentTimeMillis() - s1) + "ms,结果:" + rs);
            if (i == debug){
                System.out.println(i);
            }
        }
    }
}
