package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.authentication.CustomerAuthentication;
import org.example.exceptions.LowBalanceException;
import org.example.models.*;
import org.example.services.BalanceService;

import java.time.Instant;
import java.util.*;

@Slf4j
public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        foodApp();
    }

    public static void foodApp() {
        log.info("Welcome to Santorini Food");
        log.info("Login to place the order");

        var customer = login();
        if(customer != null) {
            var menu = new Food().getFoodsList();
            var cart = chooseAndAddItemToCart(menu);
            var totalAmount = cart.getFoodsList().stream()
                    .map(OrderItem::getOrderItemPrice)
                    .mapToInt(i -> i)
                    .sum();
            log.info("The cart has {} EUR of foods:", totalAmount);

            var orderId = new Random().nextInt(10000);
            Order order = new Order(orderId, customer.getCustomerId(), cart.getFoodsList(), Instant.now(), totalAmount);
            try {
                checkCustomerBalance(order);
                displayOrderSummary(order);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.warn("Authentication failed :( Reached max attempts, please try again after sometime");
        }
    }

    private static void checkCustomerBalance(Order order) {
        int balance = BalanceService.getBalanceForCustomer(order.getCustomerId());
        if (balance < order.getTotalOrderPrice()) {
            throw new LowBalanceException("Insufficient Balance can't process the order");
        }
    }

    private static void displayOrderSummary(Order order) {
        order.getOrderItem().forEach(f ->
            log.info("{} {} piece(s), {} EUR total", f.getOrderItemName().toUpperCase(), f.getOrderItemPieces(), f.getOrderItemPrice())
        );
        log.info("Your order {} has been confirmed. We are processing your order...", order.getOrderId());
        log.info("Thank you for your purchase :)");
    }

    private static Cart chooseAndAddItemToCart(List<Food> menu) {
        String end;
        List<OrderItem> orderItems = new ArrayList<>();
        do {
            displayMenu(menu);
            log.info("Please enter the name of the food you would like to buy");
            var itemName = checkItemAvailability(menu);
            log.info("How many pieces do you want to buy");
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
                log.info("Added {} piece(s) of {} to the cart.", quantity, itemName);
                log.info("Are you finished with your order? (y/n)");
                SCANNER.nextLine();
                end = SCANNER.nextLine();
            } else {
                log.warn("Couldn't find the item details");
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
                log.warn("{} is not available, Please choose another item", itemName.toUpperCase());
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
                log.warn("Please add proper quantity");
            }
        } while (true);
    }

    private static Food getItemDetailsFromMenu(List<Food> menu, String itemName) {
        return menu.stream()
                .filter(fi -> fi.getName().equalsIgnoreCase(itemName))
                .findAny().orElse(null);
    }

    private static void displayMenu(List<Food> menu) {
        log.info("These are our goods today:");
        log.info("-------------------------");
        menu.stream()
            .sorted(Comparator.comparing(Food::getCategory).reversed())
            .forEach(f ->
                print("- "+ f.getName().toUpperCase() + " " + f.getPrice() + " EUR each")
            );
    }

    private static Customer login() {
        Customer customer = null;
        int i = 0;
        int j = 3;
        do {
            log.info("Enter customer email :");
            var email = SCANNER.nextLine();
            log.info("Enter customer password :");
            var password = SCANNER.nextLine();

            log.info("Authenticating the user...");
            try {
                customer = CustomerAuthentication.authenticate(email, password);
                log.info("Welcome {}", customer.getCustomerName().toUpperCase());
                return customer;
            } catch (Exception e) {
                log.error("Error occurred while authenticating");
                j--;
                if (j >= 1) {
                    log.info("Please enter valid email and password {} attempts left", j);
                }
                i++;
            }
        } while (i < 3);

        return customer;
    }

    public static void print(String message) {
        System.out.println(message);
    }
}