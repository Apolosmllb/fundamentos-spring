package com.fundamentos.springboot.fundamentos.bean;

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
        LOGGER.info("Hemos ingresado al método printWithDependency");
        int number = 1;
        LOGGER.debug("El numero enviado como parametro a la dependencia operacion es: " + number);
        myOperation.sum(number);
        System.out.println(myOperation.sum(number));
        System.out.println("Hola desde mi implementacion de un bean con dependencia");
    }
}
