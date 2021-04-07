package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.dto.Tax;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FlooringDaoFileImpl implements FlooringDao{

    private Map<Integer, Order> orders = new HashMap<>();
    private Map<String, Tax> taxes = new HashMap<>();
    private Map<String, Product> products = new HashMap<>();
    private final String DELIMITER = ",";
    private final String taxFile = "Data/Taxes.txt";
    private final String productFile = "Data/Products.txt";
    private final String exportFile = "Backup/DataExport.txt";


    private Tax unmarshallTax (String taxAsText) {

        String[] taxToxens = taxAsText.split(DELIMITER);
        String stateAbrv = taxToxens[0];
        String stateName = taxToxens[1];
        BigDecimal taxRate = new BigDecimal(taxToxens[2]);

        Tax taxFromFile = new Tax();
        taxFromFile.setStateAbrv(stateAbrv);
        taxFromFile.setStateName(stateName);
        taxFromFile.setTaxRate(taxRate);

        return taxFromFile;
    }

    private Product unmarshallProduct (String productAsText) {

        String[] productToxens = productAsText.split(DELIMITER);
        String productType = productToxens[0];
        BigDecimal costPerSqFt = new BigDecimal(productToxens[1]);
        BigDecimal laborCostPerSqFt = new BigDecimal(productToxens[2]);

        Product productFromFile = new Product();
        productFromFile.setProductType(productType);
        productFromFile.setCostPerSqFt(costPerSqFt);
        productFromFile.setLaborCostPerSqFt(laborCostPerSqFt);

        return productFromFile;
    }

    private Order unmarshallOrder (String orderAsText){

        String[] orderTokens = orderAsText.split(DELIMITER);
        Integer orderNumber = Integer.parseInt(orderTokens[0]);
        String customerName = orderTokens[1];
        String state = orderTokens[2];
        BigDecimal taxRate = new BigDecimal(orderTokens[3]);
        String productType = orderTokens[4];
        BigDecimal area = new BigDecimal(orderTokens[5]);
        BigDecimal costPerSqFt = new BigDecimal(orderTokens[6]);
        BigDecimal laborCostPerSqFt = new BigDecimal(orderTokens[7]);

        Order orderFromFile = new Order();
        orderFromFile.setOrderNumber(orderNumber);
        orderFromFile.setCustomerName(customerName);
        orderFromFile.setState(state);
        orderFromFile.setTaxRate(taxRate);
        orderFromFile.setProductType(productType);
        orderFromFile.setArea(area);
        orderFromFile.setCostPerSqFt(costPerSqFt);
        orderFromFile.setLaborCostPerSqFt(laborCostPerSqFt);

        return orderFromFile;
    }

    @Override
    public Map<Integer, Order> loadOrdersForOneDay(String dateUserInput) throws FlooringDaoException {

        Map<Integer, Order> ordersForOneDay = new HashMap<>();

        Scanner scanner;

        try{
            scanner = new Scanner(new BufferedReader(new FileReader("Orders/Orders_" + dateUserInput + ".txt")));
        }
        catch (FileNotFoundException e) {
            throw new FlooringDaoException("Could not order data.", e);
        }

        String currentLine;
        Order currentOrder;

        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentOrder = unmarshallOrder(currentLine);
            ordersForOneDay.put(currentOrder.getOrderNumber(), currentOrder);
        }

        scanner.close();

        return ordersForOneDay;
    }

    @Override
    public void saveEditedOrdersToFile(Map<Integer, Order> ordersForOneDay, String dateUserInput) throws FlooringDaoException {

        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter("Orders/Orders_" + dateUserInput + ".txt"));
        } catch (IOException e) {
            throw new FlooringDaoException("Could not save order data.", e);
        }

        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

        for (Order orders : ordersForOneDay.values()){
            out.println(marshallOrder(orders));
            out.flush();
        }
        out.close();
    }

    @Override
    public void export() throws FlooringDaoException {

        PrintWriter out;
        Scanner scanner;
        String[] files;
        File f = new File("Orders");
        files = f.list();

        try {
            out = new PrintWriter(new FileWriter("Backup/DataExport.txt"));
            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate");
        } catch (IOException e) {
            throw new FlooringDaoException("Could not save export data.", e);
        }

        for(int i = 0; i < files.length; i++){

            String dateString = files[i].substring(7, 15);
            LocalDate ld = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MMddyyyy"));
            String formattedDateString = ld.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
            Map<Integer, Order> ordersForCurrentDate = loadOrdersForOneDay(dateString);
            List<Order> orderListForCurrentDate = new ArrayList<>(ordersForCurrentDate.values());

            for(Order currentOrder: orderListForCurrentDate){
                out.println(marshallOrder(currentOrder) + DELIMITER + formattedDateString);
                out.flush();
            }
        }

        out.close();
    }

    public void loadTaxes() throws FlooringDaoException{

        Scanner scanner;

        try{
            scanner = new Scanner(new BufferedReader(new FileReader(taxFile)));
        }
        catch (FileNotFoundException e) {
            throw new FlooringDaoException("Could not load tax data.", e);
        }

        String currentLine;
        Tax currentTax;

        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentTax = unmarshallTax(currentLine);
            taxes.put(currentTax.getStateName(), currentTax);
        }

        scanner.close();
    }

    public void loadProducts() throws FlooringDaoException{

        Scanner scanner;

        try{
            scanner = new Scanner(new BufferedReader(new FileReader(productFile)));
        }
        catch (FileNotFoundException e) {
            throw new FlooringDaoException("Could not load product data.", e);
        }

        String currentLine;
        Product currentProduct;

        if(scanner.hasNextLine()){
            scanner.nextLine();
        }

        while (scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            products.put(currentProduct.getProductType(), currentProduct);
        }

        scanner.close();
    }

    @Override
    public Map<Integer, Order> displayOrders(String dateUserInput) throws FlooringDaoException{

        Map<Integer, Order> orderListForDate = new HashMap<>();
        Scanner scan;

        try{
            scan = new Scanner(new BufferedReader(new FileReader("Orders/Orders_" + dateUserInput + ".txt")));
        }
        catch (FileNotFoundException e) {
            throw new FlooringDaoException("There are no orders for that date.", e);
        }

        String currentLine;
        Order currentOrder;

        if(scan.hasNextLine()){
            scan.nextLine();
        }

        while(scan.hasNextLine()){
            currentLine= scan.nextLine();
            currentOrder = unmarshallOrder(currentLine);
            orderListForDate.put(currentOrder.getOrderNumber(), currentOrder);
        }

        scan.close();

        return orderListForDate;
    }

    @Override
    public List<Product> getProductList() throws FlooringDaoException {
        loadProducts();
        List<Product> productList = new ArrayList<>(products.values());
        return productList;
    }

    @Override
    public List<Tax> getTaxList() throws FlooringDaoException {
        loadTaxes();
        List<Tax> taxList = new ArrayList<>(taxes.values());
        return taxList;
    }

    @Override
    public Order calculateOrder(Order orderToBeAdded) throws FlooringDaoException {
        Order calculatedOrder = orderToBeAdded;
        loadTaxes();
        loadProducts();

        for (Tax taxes : taxes.values()){
            if(taxes.getStateName().toLowerCase().equals(orderToBeAdded.getState().toLowerCase())) {
                orderToBeAdded.setTaxRate(taxes.getTaxRate());
            }
        }

        for (Product products : products.values()){
            if(products.getProductType().toLowerCase().equals(orderToBeAdded.getProductType().toLowerCase())){
                orderToBeAdded.setCostPerSqFt(products.getCostPerSqFt());
                orderToBeAdded.setLaborCostPerSqFt(products.getLaborCostPerSqFt());
            }
        }

        return calculatedOrder;
    }

    @Override
    public int getLastOrderNumber() throws FlooringDaoException {
        Scanner scanner;
        int highestValue = 0;

        try{
            scanner = new Scanner(new BufferedReader(new FileReader(exportFile)));
        }
        catch (FileNotFoundException e) {
            throw new FlooringDaoException("Could not load export data.", e);
        }

        String currentLine = "";

        if(scanner.hasNextLine()){
            scanner.nextLine();
        }

        while (scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            String[] exportTokens = currentLine.split(DELIMITER);
            int currentInt = Integer.parseInt(exportTokens[0]);
            if (currentInt > highestValue){
                highestValue = currentInt;
            }
        }

        scanner.close();

        return highestValue + 1;
    }

    @Override
    public void saveOrderToFile(Order orderToBeAdded, String futureOrderDate) throws FlooringDaoException {

        LocalDate ld = LocalDate.parse(futureOrderDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String modifiedDateString = ld.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        String textFile = "Orders/Orders_" + modifiedDateString + ".txt";
        String orderAsText = marshallOrder(orderToBeAdded);
        boolean printHeader = false;

        Scanner scanner;
        try{
            scanner = new Scanner(new BufferedReader(new FileReader(textFile)));
        }
        catch (FileNotFoundException e) {
            printHeader = true;
        }

        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(textFile, true));
        } catch (IOException e) {
            throw new FlooringDaoException("Could not save order data.", e);
        }

        if (printHeader) {
            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        }

        out.println(orderAsText);
        out.flush();
        out.close();
    }

    @Override
    public boolean checkOrderExists(String dateUserInput, int editOrderNumber) throws FlooringDaoException {
        boolean orderExists = false;
        String currentLine = "";
        Scanner scanner;
        Order currentOrder;

        try{
            scanner = new Scanner(new BufferedReader(new FileReader("Orders/Orders_" + dateUserInput + ".txt")));
            scanner.nextLine();
            while (scanner.hasNextLine()){
                currentLine = scanner.nextLine();
                currentOrder = unmarshallOrder(currentLine);
                if(currentOrder.getOrderNumber() == editOrderNumber){
                    orderExists = true;
                }
            }
        }
        catch (FileNotFoundException e) {
            throw new FlooringDaoException("No orders on this date.", e);
        }

        return orderExists;
    }

    private String marshallOrder(Order orderToBeAdded) {

        String orderAsText = orderToBeAdded.getOrderNumber() + DELIMITER;
        orderAsText += orderToBeAdded.getCustomerName() + DELIMITER;
        orderAsText += orderToBeAdded.getState() + DELIMITER;
        orderAsText += orderToBeAdded.getTaxRate() + DELIMITER;
        orderAsText += orderToBeAdded.getProductType() + DELIMITER;
        orderAsText += orderToBeAdded.getArea() + DELIMITER;
        orderAsText += orderToBeAdded.getCostPerSqFt() + DELIMITER;
        orderAsText += orderToBeAdded.getLaborCostPerSqFt() + DELIMITER;
        orderAsText += orderToBeAdded.materialCost() + DELIMITER;
        orderAsText += orderToBeAdded.laborCost() + DELIMITER;
        orderAsText += orderToBeAdded.tax() + DELIMITER;
        orderAsText += orderToBeAdded.total();

        return orderAsText;
    }


}
