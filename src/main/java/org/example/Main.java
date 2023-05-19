package org.example;

import org.example.authentication.CustomerAuthentication;
import org.example.exceptions.LowBalanceException;
import org.example.models.*;
import org.example.services.BalanceService;

import java.time.Instant;
import java.util.*;

public class Main {
    public static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        foodApp();
    }

    public static void foodApp() {
        print("Welcome to Santorini Food");
        print("Login to place the order");

        var customer = login();
        if(customer != null) {
            var menu = new Food().getFoodsList();
            var cart = chooseAndAddItemToCart(menu);
            var totalAmount = cart.getFoodsList().stream()
                    .map(OrderItem::getOrderItemPrice)
                    .mapToInt(i -> i)
                    .sum();
            print("The cart has " + totalAmount + " EUR of foods:");

            var orderId = new Random().nextInt(10000);
            Order order = new Order(orderId, customer.getCustomerId(), cart.getFoodsList(), Instant.now(), totalAmount);
            try {
                checkCustomerBalance(order);
                displayOrderSummary(order);
            } catch (Exception e) {
                print(e.getMessage());
            }
        } else {
            print("Unable to authenticate :(");
        }
    }

    private static void checkCustomerBalance(Order order) {
        int balance = BalanceService.getBalanceForCustomer(order.getCustomerId());
        if (balance < order.getTotalOrderPrice()) {
            throw new LowBalanceException("Insufficient Balance can't process the order");
        }
    }

    private static void displayOrderSummary(Order order) {
        order.getOrderItem().forEach(f -> print(f.getOrderItemName().toUpperCase() + " " + f.getOrderItemPieces() + " piece(s), "
                + f.getOrderItemPrice() + " EUR total"));
        print("Your order " + order.getOrderId() + " has been confirmed. We are processing your order...");
        print("Thank you for your purchase :)");
    }

    private static Cart chooseAndAddItemToCart(List<Food> menu) {
        String end;
        List<OrderItem> orderItems = new ArrayList<>();
        do {
            displayMenu(menu);
            print("Please enter the name of the food you would like to buy");
            var itemName = checkItemAvailability(menu);
            print("How many pieces do you want to buy");
            var quantity = checkQuantity();
            var itemDetails = getItemDetailsFromMenu(menu, itemName);
            if (itemDetails != null) {
                OrderItem orderItem = new OrderItem(itemName, quantity, itemDetails.getPrice() * quantity);
                var itemAlreadyPresent = getAlreadyExistedItem(orderItems, itemName);
                if (itemAlreadyPresent.isPresent()) {
                    var existedItem = itemAlreadyPresent.get();
                    var newQuantity = existedItem.getOrderItemPieces() + quantity;
                    orderItems.remove(existedItem);
                    orderItems.add(new OrderItem(itemName, newQuantity, itemDetails.getPrice() * newQuantity));
                } else {
                    orderItems.add(orderItem);
                }
                print("Added " + quantity + " piece(s) of " + itemName + " to the cart.");
                print("Are you finished with your order? (y/n)");
                SCANNER.nextLine();
                end = SCANNER.nextLine();
            } else {
                print("Couldn't find the item details");
                end = "n";
            }
        } while ("n".equals(end));
        return new Cart(orderItems);
    }

    private static Optional<OrderItem> getAlreadyExistedItem(List<OrderItem> orderItems, String itemName) {
        return orderItems.stream().filter(ot -> ot.getOrderItemName().equals(itemName)).findAny();
    }

    private static String checkItemAvailability(List<Food> menu) {
        String itemName;
        do {
            itemName = SCANNER.nextLine();
            var food = getItemDetailsFromMenu(menu, itemName);
            if (food != null) {
                return itemName;
            } else {
                print(itemName.toUpperCase() + " is not available, Please choose another item");
            }
        } while (true);
    }

    private static int checkQuantity() {
        int quantity;
        do {
            quantity = SCANNER.nextInt();
            if (quantity >= 1) {
                return quantity;
            } else {
                print("Please add proper quantity");
            }
        } while (true);
    }

    private static Food getItemDetailsFromMenu(List<Food> menu, String itemName) {
        return menu.stream()
                .filter(fi -> fi.getName().equalsIgnoreCase(itemName))
                .findAny().orElse(null);
    }

    private static void displayMenu(List<Food> menu) {
        print("These are our goods today:");
        print("-------------------------");
        menu.stream()
            .sorted(Comparator.comparing(Food::getCategory).reversed())
            .forEach(f -> print("- "+ f.getName().toUpperCase() + " " + f.getPrice() + " EUR each"));
    }

    private static Customer login() {
        boolean authorization = true;
        Customer customer = null;
        int i = 0;
        int j = 3;
        do {
            print("Enter customer email :");
            var email = SCANNER.nextLine();
            print("Enter customer password :");
            var password = SCANNER.nextLine();

            print("Authenticating the user...");
            try {
                customer = CustomerAuthentication.authenticate(email, password);
                print("Welcome " + customer.getCustomerName().toUpperCase());
                return customer;
            } catch (Exception e) {
                print(e.getMessage());
                j--;
                if (j >= 1) {
                    print("Please enter valid email and password " + j + " attempts left");
                }
                i++;
            }
        } while (authorization && i<3);
        if (i > 2) {
            print("Reached max attempts, please try after sometime");
        }
        return customer;
    }

    public static void print(String message) {
        System.out.println(message);
    }
}