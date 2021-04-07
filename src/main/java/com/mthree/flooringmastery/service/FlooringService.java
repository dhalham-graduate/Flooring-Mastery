package com.mthree.flooringmastery.service;

import com.mthree.flooringmastery.dao.FlooringDaoException;
import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;

import java.util.List;
import java.util.Map;

public interface FlooringService {
    Map<Integer, Order> displayOrders(String dateUserInput) throws FlooringDaoException;

    boolean isDateValid(String futureOrderDate);

    List<Product> getProductList() throws FlooringDaoException;

    boolean isStateValid(Order orderToBeAdded) throws FlooringDaoException;

    boolean isProductValid(Order orderToBeAdded) throws FlooringDaoException;

    Order calculateOrder(Order orderToBeAdded) throws FlooringDaoException;

    int getLastOrderNumber() throws FlooringDaoException;

    void saveOrderToFile(Order orderToBeAdded, String futureOrderDate) throws FlooringDaoException;

    boolean checkOrderExists(String dateUserInput, int editOrderNumber) throws FlooringDaoException;

    Map<Integer, Order> loadOrdersForOneDay(String dateUserInput, int editOrderNumber) throws FlooringDaoException;

    void saveEditedOrdersToFile(Map<Integer, Order> ordersForOneDay, String dateUserInput) throws FlooringDaoException;

    void export() throws FlooringDaoException;

    boolean isNameValid(Order orderToBeAdded);

    boolean isAreaValid(Order orderToBeAdded);
}
