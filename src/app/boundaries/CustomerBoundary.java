package app.boundaries;

import java.util.TreeMap;

import app.controllers.CustomerController;
import app.entities.Customer;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public class CustomerBoundary extends Boundary {
  public CustomerBoundary() {
    super(new CustomerController());
    entityName = "Customer";
  }

  @Override
  protected EntityStorable entityCreator() {
    String name;
    String contact;
    Boolean membership;

    System.out.println("Enter item name: ");
    name = sc.nextLine();

    System.out.println("Enter contact: ");
    contact = sc.nextLine();

    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "Yes, a member");
    options.put(2, "No, not a member");
    ChoicePicker memPicker = new ChoicePicker("Is this customer a member: ", options);
    membership = (memPicker.run() == 1) ? true : false;

    return (EntityStorable) (new Customer(name, contact, membership));
  }

  @Override
  public void mainOptions() {
    int choice = -1;
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "List all Customers");
    options.put(2, "Add new Customer");
    options.put(3, "Remove a Customer");
    options.put(4, "Edit a Customer");
    options.put(9, "Exit - Back to main menu");
    ChoicePicker mainPicker = new ChoicePicker("This is the Customers menu, what would you like to do? ", options);
    while (choice != 9) {
      choice = mainPicker.run();
      switch (choice) {
      case 1:
        indexAll();
        break;
      case 2:
        create();
        break;
      case 3:
        delete();
        break;
      case 4:
        edit();
        break;
      case 9:
        System.out.println("Going back to the main menu ... ");
        break;
      default:
        break;
      }
    }
  }
}
