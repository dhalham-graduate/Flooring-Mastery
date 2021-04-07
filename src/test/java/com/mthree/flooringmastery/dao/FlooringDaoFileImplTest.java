package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.dto.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FlooringDaoFileImplTest {

    FlooringDao testDao;

    public FlooringDaoFileImplTest(){
    }

    @BeforeEach
    void setUp() throws Exception {
        testDao = new FlooringDaoFileImpl();
    }

    @Test
    void testAddAndGetOrder() throws Exception {

        Order testOrder = new Order();
        testOrder.setCustomerName("Dhalham");
        testOrder.setProductType("Laminate");
        testOrder.setState("Texas");
        testOrder.setArea(new BigDecimal("120"));
        testOrder.setOrderNumber(1);
        testOrder = testDao.calculateOrder(testOrder);
        String testDate = "01/01/2020";
        String testDateFormatted = "01012020";

        testDao.saveOrderToFile(testOrder, testDate);

        Map<Integer, Order> testMap = testDao.loadOrdersForOneDay(testDateFormatted);
        Order retrievedOrder = testMap.get(1);

        //Assert
        assertEquals(1, testMap.size());
        assertEquals(testOrder.getArea(),retrievedOrder.getArea());
        assertEquals(testOrder.getCustomerName(),retrievedOrder.getCustomerName());
        assertEquals(testOrder.laborCost(),retrievedOrder.laborCost());
        assertEquals(testOrder.total(),retrievedOrder.total());
        assertEquals(testOrder.getOrderNumber(), retrievedOrder.getOrderNumber());
    }

    @Test
    void testCheckOrderExists() throws FlooringDaoException {
        Order testOrder = new Order();
        testOrder.setCustomerName("Dhalham");
        testOrder.setProductType("Laminate");
        testOrder.setState("Texas");
        testOrder.setArea(new BigDecimal("120"));
        testOrder.setOrderNumber(1);
        testOrder = testDao.calculateOrder(testOrder);
        String testDate = "01/02/2020";
        String testDateFormatted = "01022020";

        testDao.saveOrderToFile(testOrder, testDate);

        //Assert
        assertTrue(testDao.checkOrderExists(testDateFormatted, 1));
    }

    @Test
    void testGetLastOrderNumber() throws FlooringDaoException {

        //Setting the order number manually to 200
        //Purpose of the method is to return the highest order number +1 out of all the orders in the export file
        Order testOrder = new Order();
        testOrder.setCustomerName("Dhalham");
        testOrder.setProductType("Laminate");
        testOrder.setState("Texas");
        testOrder.setArea(new BigDecimal("120"));
        testOrder.setOrderNumber(200);
        testOrder = testDao.calculateOrder(testOrder);
        String testDate = "01/05/2020";
        String testDateFormatted = "01052020";

        testDao.saveOrderToFile(testOrder, testDate);
        testDao.export();

        assertEquals(201, testDao.getLastOrderNumber());
    }
}