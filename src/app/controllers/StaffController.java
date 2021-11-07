package app.controllers;

import java.util.TreeMap;

import app.db.*;
import app.entities.*;
import app.entities.Staff.Title;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public class StaffController extends Controller {
  private Staff curStaff;

  public StaffController() {
    super(new StaffData());
    /* Sets the protected attributes: data and sc */
    staffSelector();
  }

  public Staff getCurStaff() { return curStaff; }
  /* private because should only set staff by staffSelection() */
  private void setCurStaff(Staff s) { curStaff = s; }

  protected void staffSelector() {
    int choice;

    ChoicePicker picker = new ChoicePicker(
      "Which staff are you? Enter the corresponding number: ",
      data.getChoiceMap()
    );
    choice = picker.run();
    setCurStaff((Staff) data.getList().get(choice - 1));
    System.out.println("Hello " + curStaff.getName() + "!");
  }

  protected EntityStorable createCaller() {
    int staffId;
    String name;
    Title jobTitle;
    System.out.println("Creating new staff ...");
    System.out.println("Enter staff name: ");
    name = sc.nextLine();

    System.out.println("Enter staff id: ");
    staffId = sc.nextInt(); sc.nextLine();

    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    for (int i = 0; i < Title.values().length; i++)
      options.put(i + 1, Title.values()[i].name());
    ChoicePicker titlePicker = new ChoicePicker(
        "Enter number corresponding to job title: ",
        options
    );
    jobTitle = Title.values()[titlePicker.run() - 1];

    return (EntityStorable)(new Staff(staffId, name, jobTitle));
  }

  protected int deleteChoicePicker() {
    ChoicePicker delPicker = new ChoicePicker(
      "Which Staff do you want to delete? ", data.getChoiceMap()
    );
    return delPicker.run(); /* Returns choice number */
    /* Note: choice starts from 1, index start from 0 */
  }

  public void mainOptions() {
    int choice = -1;
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "List all Staff");
    options.put(2, "Add new Staff");
    options.put(3, "Remove a Staff");
    options.put(4, "Change current Staff");
    options.put(9, "Exit - Back to main menu");
    ChoicePicker mainPicker = new ChoicePicker(
      "This is the Staff menu, what would you like to do? ",
      options
    );
    while (choice != 9) {
      choice = mainPicker.run();
      switch (choice) {
        case 1:
          data.printAll();
          break;
        case 2:
          create();
          break;
        case 3:
          delete();
          break;
        case 4:
          staffSelector();
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
