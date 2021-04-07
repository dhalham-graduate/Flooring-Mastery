package com.mthree.flooringmastery.view;

import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class FlooringView {

    private UserIO io;

    public FlooringView(UserIO io){
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print(" * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("   * <<Flooring Program>>");
        io.print("   * 1. Display Orders");
        io.print("   * 2. Add an Order");
        io.print("   * 3. Edit an Order");
        io.print("   * 4. Remove an Order");
        io.print("   * 5. Export All Data");
        io.print("   * 6. Quit");
        io.print("   * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        return io.readInt("Please select an option:", 1, 6);
    }

    public String printGetOrderDate() {
        return io.readDate("Please enter the date, in the format: MM/DD/YYYY ");
    }

    public void printViewOrders(Map<Integer, Order> orderList) {
        for (Integer keys: orderList.keySet()){
            io.print("\r\nOrder Number: " + orderList.get(keys).getOrderNumber());
            io.print("Customer Name: " + orderList.get(keys).getCustomerName());
            io.print("State: " + orderList.get(keys).getState());
            io.print("Tax Rate: " + orderList.get(keys).getTaxRate());
            io.print("Product Type: " + orderList.get(keys).getProductType());
            io.print("Area: " + orderList.get(keys).getArea());
            io.print("Cost Per Squarefoot: " + orderList.get(keys).getCostPerSqFt());
            io.print("Labor Cost Per Squarefoot: " + orderList.get(keys).getLaborCostPerSqFt());
            io.print("Material Cost: " + orderList.get(keys).materialCost());
            io.print("Labor Cost: " + orderList.get(keys).laborCost());
            io.print("Tax: " + orderList.get(keys).tax());
            io.print("Total: " + orderList.get(keys).total());
        }

        io.readString("\r\nPlease hit enter to return to the main menu:");
    }

    public void printAddOrderInformation() {
        io.print("\r\nPlease enter the order details for the order you wish to add:");
    }

    public Order printGetOrderDetails(List<Product> productList) {
        boolean checkBigDecimal = true;
        Order orderDetails = new Order();
        orderDetails.setCustomerName(io.readString("Please enter your name:"));
        orderDetails.setState(io.readString("Please enter the state:"));
        io.print("\r\nProducts Available\r\n");
        for (Product products : productList){
            io.print(products.getProductType());
        }
        orderDetails.setProductType(io.readString("\r\nPlease enter the product type:"));
        while (checkBigDecimal) {
            try {
                orderDetails.setArea(new BigDecimal(io.readString("Please enter the area:")));
                checkBigDecimal = false;
            } catch (NumberFormatException e) {
                io.print("\r\nPlease enter a valid number!");
                checkBigDecimal = true;
            }
        }
        return orderDetails;
    }

    public String printGetFutureDate() {
       return io.readString("\r\nPlease enter the date (in the future) for your order in the following format: MM/DD/YYYY");
    }

    public void printPromptEnterValidDate() {
        io.print("\r\nThat is an invalid date!");
    }

    public void printInvalidProductAndState() {
        io.print("\r\nBoth the product and state you have entered are not available, please try again.\r\n");
    }

    public void printInvalidProduct() {
        io.print("\r\nThe product you have entered is not available, please try again.\r\n");

    }

    public void printInvalidState() {
        io.print("\r\nThe state you have entered is not available, please try again.\r\n");
    }

    public boolean getAddOrderConfirmation(Order orderToBeAdded) {
        String confirmation;
        io.print("\r\nHere are the details for your order you wish to add:\r\n");
        io.print("Customer Name: " + orderToBeAdded.getCustomerName());
        io.print("State: " + orderToBeAdded.getState());
        io.print("Tax Rate: " + orderToBeAdded.getTaxRate());
        io.print("Product Type: " + orderToBeAdded.getProductType());
        io.print("Area: " + orderToBeAdded.getArea());
        io.print("Cost Per Squarefoot: " + orderToBeAdded.getCostPerSqFt());
        io.print("Labor Cost Per Squarefoot: " + orderToBeAdded.getLaborCostPerSqFt());
        io.print("Material Cost: " + orderToBeAdded.materialCost());
        io.print("Labor Cost: " + orderToBeAdded.laborCost());
        io.print("Tax: " + orderToBeAdded.tax());
        io.print("Total: " + orderToBeAdded.total());
        confirmation = io.readString("\r\nDo you wish to confirm this order? Enter Y (yes) or N (no):");
        if(confirmation.toLowerCase().equals("y")){
            return true;
        }
        else {
            return false;
        }
    }

    public void printConfirmation(boolean confirmation) {
        if (confirmation) {
            io.readString("\r\nOrder has been successfully confirmed, please hit enter to return to the main menu:");
        }
        else {
            io.readString("\r\nOrder has not been added, please hit enter to return to the main menu:");

        }
    }

    public void displayErrorMessage(String message) {
        io.print("\r\nERROR:");
        io.readString(message + " Please hit enter to return to the main menu:\r\n");
    }

    public int getOrderNumber() {
        return io.readInt("\r\nPlease enter the order number: ");
    }

    public void printOrderExists() {
        io.print("\r\nThe order exists!");
    }

    public void printOrderDoesNotExist() {
        io.print("\r\nThe order does not exist.");
    }

    public Order printGetEditedOrderDetails(Order editedOrder, List<Product> productList) {
        String editedName = io.readString("Please enter your name (" + editedOrder.getCustomerName() + "): ");
        if (editedName.equals("")){
            ;
        }
        else {
            editedOrder.setCustomerName(editedName);
        }
        String editedState = io.readString("Please enter the state (" + editedOrder.getState() + "): ");
        if (editedState.equals("")){
            ;
        }
        else {
            editedOrder.setState(editedState);
        }

        io.print("\r\nProducts Available\r\n");
        for (Product products : productList){
            io.print(products.getProductType());
        }

        String editedProduct = io.readString("\r\nPlease enter the product (" + editedOrder.getProductType() + "): ");
        if (editedProduct.equals("")){
            ;
        }
        else {
            editedOrder.setProductType(editedProduct);
        }
        String areaBigDecimalString = io.readString("Please enter the area (" + editedOrder.getArea() + "): ");
        if(areaBigDecimalString.equals("")){
            ;
        }
        else{
            BigDecimal editedArea = new BigDecimal(areaBigDecimalString);
            editedOrder.setArea(editedArea);
        }
        return editedOrder;
    }

    public boolean getRemoveOrderConfirmation(Order orderToBeAdded) {
        String confirmation;
        io.print("\r\nHere are the details for your order you wish to remove:\r\n");
        io.print("Customer Name: " + orderToBeAdded.getCustomerName());
        io.print("State: " + orderToBeAdded.getState());
        io.print("Tax Rate: " + orderToBeAdded.getTaxRate());
        io.print("Product Type: " + orderToBeAdded.getProductType());
        io.print("Area: " + orderToBeAdded.getArea());
        io.print("Cost Per Squarefoot: " + orderToBeAdded.getCostPerSqFt());
        io.print("Labor Cost Per Squarefoot: " + orderToBeAdded.getLaborCostPerSqFt());
        io.print("Material Cost: " + orderToBeAdded.materialCost());
        io.print("Labor Cost: " + orderToBeAdded.laborCost());
        io.print("Tax: " + orderToBeAdded.tax());
        io.print("Total: " + orderToBeAdded.total());
        confirmation = io.readString("\r\nDo you wish to confirm this order? Enter Y (yes) or N (no):");
        if(confirmation.toLowerCase().equals("y")){
            return true;
        }
        else {
            return false;
        }
    }

    public void printRemoveConfirmation(boolean confirmation) {
        if (confirmation) {
            io.readString("\r\nOrder has been successfully removed, please hit enter to return to the main menu:");
        }
        else {
            io.readString("\r\nOrder has not been removed, please hit enter to return to the main menu:");

        }
    }

    public void printExportOrders() {
        io.readString("\r\nAll orders have been exported. Please hit enter to return to the main menu:");
    }

    public void printExit() {
        io.print("\r\nThe application is now closing...");
    }

    public void printInvalidName() {
        io.readString("\r\nName cannot be empty, press enter to try again:");
    }

    public void printInvalidArea() {
        io.readString("\r\nThe area has to be greater than or equal to 100, press enter to try again:");
    }
}
