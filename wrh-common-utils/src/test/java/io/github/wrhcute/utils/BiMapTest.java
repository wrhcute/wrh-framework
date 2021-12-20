package io.github.wrhcute.utils;

import org.junit.Test;

public class BiMapTest {


    @Test
    public void test(){
        BiMap<String,Integer> nameIdMap = new BiMap<>();
        nameIdMap.put("wrh",18);
        nameIdMap.put("keel",5);
        nameIdMap.put("ccc",9);

        Integer id = nameIdMap.get("wrh");
        nameIdMap.remove("wrh");
    }
}
