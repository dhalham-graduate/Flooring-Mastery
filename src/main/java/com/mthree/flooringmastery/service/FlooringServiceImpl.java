package com.mthree.flooringmastery.service;

import com.mthree.flooringmastery.dao.FlooringDao;
import com.mthree.flooringmastery.dao.FlooringDaoException;
import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.dto.Tax;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class FlooringServiceImpl implements FlooringService{

    private FlooringDao dao;

    public FlooringServiceImpl(FlooringDao dao) {
        this.dao = dao;
    }

    @Override
    public Map<Integer, Order> displayOrders(String dateUserInput) throws FlooringDaoException {
        return dao.displayOrders(dateUserInput);
    }

    @Override
    public boolean isDateValid(String futureOrderDate) {
        boolean dateIsValid = false;
        LocalDate currentDate = LocalDate.now();
        try {
            LocalDate ld = LocalDate.parse(futureOrderDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            if (ld.isAfter(currentDate)) {
                dateIsValid = true;
            }
        }
        catch (DateTimeException e){
            dateIsValid = false;
        }
        return dateIsValid;
    }

    @Override
    public List<Product> getProductList() throws FlooringDaoException {
        return dao.getProductList();
    }

    @Override
    public boolean isStateValid(Order orderToBeAdded) throws FlooringDaoException {

        boolean stateIsValid = false;
        List<Tax> taxList = dao.getTaxList();

        for (Tax taxes: taxList) {
            if(taxes.getStateName().toLowerCase().equals(orderToBeAdded.getState().toLowerCase())){
                stateIsValid = true;
            }
        }

        return stateIsValid;
    }

    @Override
    public boolean isProductValid(Order orderToBeAdded) throws FlooringDaoException {

        boolean productIsValid = false;
        List<Product> productList = dao.getProductList();

        for (Product products : productList) {
            if(products.getProductType().toLowerCase().equals(orderToBeAdded.getProductType().toLowerCase())) {
                productIsValid = true;
            }
        }
        return productIsValid;
    }

    @Override
    public Order calculateOrder(Order orderToBeAdded) throws FlooringDaoException {
        return dao.calculateOrder(orderToBeAdded);
    }

    @Override
    public int getLastOrderNumber() throws FlooringDaoException {
        return dao.getLastOrderNumber();
    }

    @Override
    public void saveOrderToFile(Order orderToBeAdded, String futureOrderDate) throws FlooringDaoException {
        dao.saveOrderToFile(orderToBeAdded, futureOrderDate);
    }

    @Override
    public boolean checkOrderExists(String dateUserInput, int editOrderNumber) throws FlooringDaoException {
        return dao.checkOrderExists(dateUserInput, editOrderNumber);
    }

    @Override
    public Map<Integer, Order> loadOrdersForOneDay(String dateUserInput, int editOrderNumber) throws FlooringDaoException {
        return dao.loadOrdersForOneDay(dateUserInput);
    }

    @Override
    public void saveEditedOrdersToFile(Map<Integer, Order> ordersForOneDay, String dateUserInput) throws FlooringDaoException {
        dao.saveEditedOrdersToFile(ordersForOneDay, dateUserInput);
    }

    @Override
    public void export() throws FlooringDaoException {
        dao.export();
    }

    @Override
    public boolean isNameValid(Order orderToBeAdded) {
        if(orderToBeAdded.getCustomerName().equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean isAreaValid(Order orderToBeAdded) {
        BigDecimal check = orderToBeAdded.getArea();
        if (check.floatValue() >= 100) {
            return true;
        }
        else {
            return false;
        }
    }
}
