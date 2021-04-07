package com.mthree.flooringmastery.service;

import com.mthree.flooringmastery.dao.FlooringDao;
import com.mthree.flooringmastery.dao.FlooringDaoException;
import com.mthree.flooringmastery.dao.FlooringDaoFileImpl;
import com.mthree.flooringmastery.dto.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FlooringServiceImplTest {

    private FlooringService service;

    public FlooringServiceImplTest(){

        FlooringDao dao = new FlooringDaoFileImpl();
        service = new FlooringServiceImpl(dao);
    }

    @Test
    void testIsDateValid() {

        String pastDate = "11/26/2020";
        String futureDate = "11/26/2025";

        assertFalse(service.isDateValid(pastDate));
        assertTrue(service.isDateValid(futureDate));
    }

    @Test
    void testIsNameValid() {

        Order invalidOrder = new Order();
        Order validOrder = new Order();

        invalidOrder.setCustomerName("");
        validOrder.setCustomerName("Dhalham");

        assertFalse(service.isNameValid(invalidOrder));
        assertTrue(service.isNameValid(validOrder));
    }

    @Test
    void testIsProductValid() throws FlooringDaoException {

        Order invalidOrder = new Order();
        Order validOrder = new Order();

        invalidOrder.setProductType("glass");
        validOrder.setProductType("laminate");

        assertFalse(service.isProductValid(invalidOrder));
        assertTrue(service.isProductValid(validOrder));
    }

    @Test
    void testIsAreaValid() {

        Order invalidOrder = new Order();
        Order validOrder = new Order();

        invalidOrder.setArea(new BigDecimal("99"));
        validOrder.setArea(new BigDecimal("101"));

        assertFalse(service.isAreaValid(invalidOrder));
        assertTrue(service.isAreaValid(validOrder));
    }

    @Test
    void testIsStateValid() throws FlooringDaoException {

        Order invalidOrder = new Order();
        Order validOrder = new Order();

        invalidOrder.setState("Florida");
        validOrder.setState("Kentucky");

        assertFalse(service.isStateValid(invalidOrder));
        assertTrue(service.isStateValid(validOrder));
    }
}