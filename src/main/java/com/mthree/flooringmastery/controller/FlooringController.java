package com.mthree.flooringmastery.controller;

import com.mthree.flooringmastery.dao.FlooringDaoException;
import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.service.FlooringService;
import com.mthree.flooringmastery.view.FlooringView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlooringController {

    private FlooringService service;
    private FlooringView view;
    private Map<Integer, Order> activeOrders = new HashMap<>();
    private int currentOrderNumber = 0;

    public FlooringController(FlooringService service, FlooringView view){
        this.service = service;
        this.view = view;
    }

    public void run() throws FlooringDaoException{

        boolean keepGoing = true;
        int menuSelection;

        while (keepGoing) {
            try {
                menuSelection = getMenuSelection();
                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        export();
                        break;
                    case 6:
                        exit();
                        keepGoing = false;
                        break;
                }
            }
            catch (FlooringDaoException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }

    }

    private void exit() {
        view.printExit();
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void displayOrders() throws FlooringDaoException {
        String dateUserInput = view.printGetOrderDate();
        Map<Integer, Order> orderList = service.displayOrders(dateUserInput);
        view.printViewOrders(orderList);
    }

    private void addOrder() throws FlooringDaoException {
        Order orderToBeAdded = null;
        String futureOrderDate = "";
        boolean validFutureDate = false;
        boolean validOrder = false;
        boolean validState = false;
        boolean validProduct = false;
        boolean confirmation = false;
        boolean validName = false;
        boolean validArea = false;
        view.printAddOrderInformation();
        while (!validFutureDate){
            futureOrderDate = view.printGetFutureDate();
            validFutureDate = service.isDateValid(futureOrderDate);
            if(!validFutureDate){
                view.printPromptEnterValidDate();
            }
        }
        while(!validOrder) {
            List<Product> productList = service.getProductList();
            orderToBeAdded = view.printGetOrderDetails(productList);
            validState = service.isStateValid(orderToBeAdded);
            validProduct = service.isProductValid(orderToBeAdded);
            validName = service.isNameValid(orderToBeAdded);
            validArea = service.isAreaValid(orderToBeAdded);
            validOrder = validState && validProduct && validArea && validName;
            if (!validProduct && !validState){
                view.printInvalidProductAndState();
            }
            else if (!validProduct) {
                view.printInvalidProduct();
            }
            else if (!validState){
                view.printInvalidState();
            }
            else if (!validName){
                view.printInvalidName();
            }
            else if (!validArea){
                view.printInvalidArea();
            }
        }
        orderToBeAdded = service.calculateOrder(orderToBeAdded);
        confirmation = view.getAddOrderConfirmation(orderToBeAdded);
        view.printConfirmation(confirmation);
        if(confirmation) {
            if(currentOrderNumber == 0) {
                orderToBeAdded.setOrderNumber(service.getLastOrderNumber());
                currentOrderNumber = orderToBeAdded.getOrderNumber();
            }
            else{
                currentOrderNumber++;
                orderToBeAdded.setOrderNumber(currentOrderNumber);
            }
            activeOrders.put(orderToBeAdded.getOrderNumber(),orderToBeAdded);
            service.saveOrderToFile(orderToBeAdded, futureOrderDate);
        }
    }

    private void editOrder() throws FlooringDaoException {
        String dateUserInput = view.printGetOrderDate();
        Map<Integer, Order> ordersForOneDay = null;
        Order editedOrder = null;
        int editOrderNumber = view.getOrderNumber();
        boolean orderExists = service.checkOrderExists(dateUserInput, editOrderNumber);
        boolean validOrder = false;
        boolean validState = false;
        boolean validProduct = false;
        boolean validName = false;
        boolean validArea = false;
        boolean confirmation = false;
        if(orderExists) {
            view.printOrderExists();
            while (!validOrder) {
                ordersForOneDay = service.loadOrdersForOneDay(dateUserInput, editOrderNumber);
                editedOrder = ordersForOneDay.get(editOrderNumber);
                List<Product> productList = service.getProductList();
                editedOrder = view.printGetEditedOrderDetails(editedOrder, productList);
                validState = service.isStateValid(editedOrder);
                validProduct = service.isProductValid(editedOrder);
                validName = service.isNameValid(editedOrder);
                validArea = service.isAreaValid(editedOrder);
                validOrder = validState && validProduct && validArea && validName;
                if (!validProduct && !validState){
                    view.printInvalidProductAndState();
                }
                else if (!validProduct) {
                    view.printInvalidProduct();
                }
                else if (!validState){
                    view.printInvalidState();
                }
                else if (!validName){
                    view.printInvalidName();
                }
                else if (!validArea){
                    view.printInvalidArea();
                }
            }
            editedOrder = service.calculateOrder(editedOrder);
            confirmation = view.getAddOrderConfirmation(editedOrder);
            view.printConfirmation(confirmation);
            if (confirmation){
                ordersForOneDay.put(editOrderNumber, editedOrder);
                service.saveEditedOrdersToFile(ordersForOneDay, dateUserInput);
            }
            
        }
        else {
            view.printOrderDoesNotExist();
        }
    }

    private void removeOrder() throws FlooringDaoException {
        String dateUserInput = view.printGetOrderDate();
        Map<Integer, Order> ordersForOneDay = null;
        Order removeOrder = null;
        int removeOrderNumber = view.getOrderNumber();
        boolean orderExists = service.checkOrderExists(dateUserInput, removeOrderNumber);
        boolean confirmation = false;
        if(orderExists) {
            view.printOrderExists();
            ordersForOneDay = service.loadOrdersForOneDay(dateUserInput, removeOrderNumber);
            removeOrder = ordersForOneDay.get(removeOrderNumber);
            confirmation = view.getRemoveOrderConfirmation(removeOrder);
            view.printRemoveConfirmation(confirmation);
            if (confirmation){
                ordersForOneDay.remove(removeOrderNumber);
                service.saveEditedOrdersToFile(ordersForOneDay, dateUserInput);
            }

        }
        else {
            view.printOrderDoesNotExist();
        }
    }

    private void export() throws FlooringDaoException {
        service.export();
        view.printExportOrders();
    }

}
