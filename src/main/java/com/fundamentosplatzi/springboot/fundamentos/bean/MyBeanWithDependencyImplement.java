package com.fundamentosplatzi.springboot.fundamentos.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyBeanWithDependencyImplement implements MyBeanWithDependency{
    Log LOGGER = LogFactory.getLog(MyBeanWithDependencyImplement.class);
    private MyOperation myOperation;

    public MyBeanWithDependencyImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependency() {
        LOGGER.info("We have entered printWithDependency");
        int num = 2;
        LOGGER.debug("The number given as parameter to the operation dependency is: " + num);
        System.out.println(myOperation.sum(num));
        System.out.println("Hi from the implementation of a bean with dependency");
    }
}
