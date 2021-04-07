package com.mthree.flooringmastery;

import com.mthree.flooringmastery.controller.FlooringController;
import com.mthree.flooringmastery.dao.FlooringDaoException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) throws FlooringDaoException {

        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        FlooringController controller = appContext.getBean("controller", FlooringController.class);
        controller.run();

    }
}
