package dev.m7wq.test;

import dev.m7wq.main.Inject;

public class TestInstance {

    @Inject
    Test test;

    @Inject("test2")
    Test test2;

    public void act(){
        System.out.println(test.value);
        System.out.println(test2.value);
    }
}
