package app.boundaries;

import java.time.LocalDateTime;
import java.util.TreeMap;

import app.controllers.TableController;
import app.entities.Table;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public class TableBoundary extends Boundary {
  public TableBoundary() {
    super(new TableController());
    entityName = "Table";
  }

  @Override
  protected EntityStorable entityCreator() {
    int pax;
    System.out.println("Enter table capacity (pax): ");
    pax = sc.nextInt(); sc.nextLine();
    return (EntityStorable) (new Table(pax));
  }

  @Override
  public void mainOptions() {
    int choice = -1;
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "List all Tables");
    options.put(2, "Add new Table");
    options.put(3, "Remove a Table");
    // options.put(4, "Edit a Table");
    options.put(5, "Free up Tables with expired Reservations");
    options.put(9, "Exit - Back to main menu");
    ChoicePicker mainPicker = new ChoicePicker("This is the Table menu, what would you like to do? ", options);
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
      // case 4:
      //   edit();
      //   break;
      case 5:
        freeUpTables();
        break;
      case 9:
        System.out.println("Going back to the main menu ... ");
        break;
      default:
        break;
      }
    }
  }

  public Table getOneFreeTable(int pax, LocalDateTime start, LocalDateTime end) {
    TableController tableControl = (TableController) getController();
    return tableControl.getOneFreeTable(pax, start, end);
  }

  public void freeUpTables() {
    TableController tableController = (TableController) getController();
    tableController.freeUpTables();
  }
}
