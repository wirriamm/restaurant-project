package app;

import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import app.boundaries.Boundary;
import app.boundaries.CustomerBoundary;
import app.boundaries.MenuItemBoundary;
import app.boundaries.OrderBoundary;
import app.boundaries.PromotionBoundary;
import app.boundaries.ReservationBoundary;
import app.boundaries.StaffBoundary;
import app.boundaries.TableBoundary;
import app.entities.Order;
import app.utilities.ChoicePicker;
import app.utilities.RevenueReport;

public class RestaurantApp {
  /* Use a single scanner across the app to prevent accidentally closing System.in input stream */
  public static final Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    int choice = -1;

    System.out.println("Welcome to the restaurant POS Application ");
    // Initialize boundaries
    StaffBoundary staffBoundary = new StaffBoundary();
    MenuItemBoundary itemBoundary = new MenuItemBoundary();
    PromotionBoundary promotionBoundary = new PromotionBoundary(itemBoundary);
    CustomerBoundary customerBoundary = new CustomerBoundary();
    TableBoundary tableBoundary = new TableBoundary();
    ReservationBoundary resvBoundary = new ReservationBoundary(customerBoundary, tableBoundary, staffBoundary);
    OrderBoundary orderBoundary = new OrderBoundary(customerBoundary,tableBoundary, staffBoundary, resvBoundary, itemBoundary, promotionBoundary);

    // Main menu
    String prompt = "Which section do you want to go to? ";
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "Menu Items");
    options.put(2, "Promotional Sets");
    options.put(3, "Orders");
    options.put(4, "Reservations");
    options.put(5, "Get Sales Revenue Report");
    options.put(6, "Table Management");
    options.put(7, "Customer Management");
    options.put(8, "Staff Management");
    options.put(9, "Quit and save data");
    options.put(100, "Quit and Delete data files (clear DB)");
    ChoicePicker picker = new ChoicePicker(prompt, options);
    while (choice != 9 && choice != 100) {
      choice = picker.run();
      switch (choice) {
        case 1:
        itemBoundary.mainOptions();
        break;
        case 2:
        promotionBoundary.mainOptions();
        break;
        case 3:
        orderBoundary.mainOptions();
        break;
        case 4:
        resvBoundary.mainOptions();
        break;
        case 5:
        System.out.println("Printing out sales report");
        askSalesReport(orderBoundary);
        break;
        case 6:
        tableBoundary.mainOptions();
        break;
        case 7:
        customerBoundary.mainOptions();
        break;
        case 8:
        staffBoundary.mainOptions();
        break;
        case 9:
        System.out.println("Exiting the app ...");
        /* Save all ephemeral data to stores */
        staffBoundary.saveAll();
        itemBoundary.saveAll();
        promotionBoundary.saveAll();
        customerBoundary.saveAll();
        tableBoundary.saveAll();
        resvBoundary.saveAll();
        orderBoundary.saveAll();
        break;
        case 100:
        /* For development purposes */
        System.out.println("Deleting files ...");
        deleteFiles(staffBoundary);
        deleteFiles(itemBoundary);
        deleteFiles(promotionBoundary);
        deleteFiles(customerBoundary);
        deleteFiles(tableBoundary);
        deleteFiles(resvBoundary);
        deleteFiles(orderBoundary);
        break;
        default:
        break;
      }
    }
    sc.close();
  }

  private static void deleteFiles(Boundary c) {
    /* For development purposes */
    c.getController().deleteControllerFile();
  }

  private static void askSalesReport(OrderBoundary orderBoundary) {
    int choice = -1;

    /* Get all orders */
    List<Order> orders = orderBoundary.getController().getList().stream()
      .map((e) -> (Order) e)
      .collect(Collectors.toList());
    RevenueReport revReport = new RevenueReport(orders);

    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "By Day");
    options.put(2, "By Month");
    options.put(0, "Quit");
    ChoicePicker picker = new ChoicePicker("Which kind of report do you want?", options);
    while (choice != 0) {
      choice = picker.run();
      switch (choice) {
        case 1:
          revReport.printReport("day");
          break;
        case 2:
          revReport.printReport("month");
          break;
        default:
          break;
      }
    }
  }
}
