package io.github.wrhcute.utils.reflection;

import io.github.wrhcute.utils.Reflections;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

public class ReflectionTest {



    @Test
    public void testGetDeclaredField(){
        List<Field> fields = Reflections.getFields(Sub.class);
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }
}
