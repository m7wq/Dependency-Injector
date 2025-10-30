package dev.m7wq.test;

import dev.m7wq.main.InstanceHandler;

public class App {

    private static Test test;

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {


        TestInstance testInstance = new InstanceHandler()
                .resolve(Test.class, () -> new Test("Specified"))
                .resolve("test2", new Test("Named"))
                .of(TestInstance.class);

        testInstance.act();
    }
}
