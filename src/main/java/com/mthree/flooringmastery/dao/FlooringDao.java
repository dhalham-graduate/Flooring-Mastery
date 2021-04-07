package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.dto.Tax;

import java.util.List;
import java.util.Map;

public interface FlooringDao {
    Map<Integer, Order> displayOrders(String dateUserInput) throws FlooringDaoException;

    List<Product> getProductList() throws FlooringDaoException;

    List<Tax> getTaxList() throws FlooringDaoException;

    Order calculateOrder(Order orderToBeAdded) throws FlooringDaoException;

    int getLastOrderNumber() throws FlooringDaoException;

    void saveOrderToFile(Order orderToBeAdded, String futureOrderDate) throws FlooringDaoException;

    boolean checkOrderExists(String dateUserInput, int editOrderNumber) throws FlooringDaoException;

    Map<Integer, Order> loadOrdersForOneDay(String dateUserInput) throws FlooringDaoException;

    void saveEditedOrdersToFile(Map<Integer, Order> ordersForOneDay, String dateUserInput) throws FlooringDaoException;

    void export() throws FlooringDaoException;
}
