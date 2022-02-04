package io.github.wrhcute.utils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName RandomUtil.java
 * @Description 随机数工具类
 * @createTime 2022年02月04日 21:26:00
 */
public class RandomUtil {

    private static Random random = new Random();

    public static int random(int min , int max){
        return min + random.nextInt(max - min + 1);
    }


    public static float random(float min ,float max,int pow){
        float base = random.nextFloat(); // [0,1)
        DecimalFormat format = new DecimalFormat("#." + StrUtil.repeat("0",pow));
        return Float.parseFloat(format.format(min  + base * (max - min))); //嗯....永远取不到最大值
    }


}
