package io.github.wrhcute.utils.reflection;

import io.github.wrhcute.utils.ReflectionUtil;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

public class ReflectionTest {



    @Test
    public void testGetDeclaredField(){
        List<Field> declaredFields = ReflectionUtil.getFields(Sub.class);
        for (Field field : declaredFields) {
            System.out.println(field.getName());
        }
    }
}
