package dev.m7wq.main;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class InstanceHandler
{

    private final Map<String, Object> namedDependencies;
    private final Map<Class, Supplier> specifiedDependencies;

    public InstanceHandler(){
        namedDependencies = new HashMap<>();
        specifiedDependencies = new HashMap<>();
    }


    public <T> InstanceHandler resolve(Class<? extends T> clazz, Supplier<T> supplier){
        specifiedDependencies.put(clazz, supplier);
        return this;
    }

    public InstanceHandler resolve(String input, Object object){
        namedDependencies.put(input, object);
        return this;
    }

    public <T> T of(Class<? extends T> clazz) throws InstantiationException, IllegalAccessException {

        Object instance = clazz.newInstance();

        for (Field field : clazz.getDeclaredFields()){

            if (!field.isAnnotationPresent(Inject.class))
                continue;

            field.setAccessible(true);

            Inject injection = field.getAnnotation(Inject.class);

            if (injection.value().equalsIgnoreCase("")){
                for (Map.Entry<Class, Supplier> entry : specifiedDependencies.entrySet()){
                    if (field.getType().equals(entry.getKey())){
                        field.set(instance, entry.getValue().get());
                    }
                }
                continue;
            }

            for (Map.Entry<String, Object> entry : namedDependencies.entrySet()){
                if (injection.value().equalsIgnoreCase(entry.getKey())){
                    field.set(instance, entry.getValue());
                }
            }

        }

        return (T) instance;
    }
}
