package pl.com.przepiora.week3rest.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.com.przepiora.week3rest.model.Car;

@Route("ui")
@UIScope
public class MainView extends VerticalLayout {

  private RestService restService;
  private Grid<Car> gridCars;
  private Button buttonAddCar, buttonEditCar, buttonDeleteCar;
  private Car selectedCar;

  public MainView() {
    restService = new RestService();
    this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    VerticalLayout mainLayout = new VerticalLayout();
    mainLayout.setWidth("50%");
    mainLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

    initializeGridCars();

    buttonAddCar = new Button("Add");
    buttonEditCar = new Button("Edit");
    buttonDeleteCar = new Button("Delete");
    buttonDeleteCar.addClickListener(buttonClickEvent -> {
      Dialog dialogConfirmDelete = new DialogConfirmDelete(selectedCar);
      dialogConfirmDelete.open();
    });
    setButtonsEditDeleteEnabled(false);
    HorizontalLayout buttonsLayout = new HorizontalLayout();
    buttonsLayout.add(buttonAddCar, buttonEditCar, buttonDeleteCar);

    mainLayout.add(gridCars, buttonsLayout);
    Button button = new Button("OK");
    button.addClickListener(buttonClickEvent -> {
      setButtonsEditDeleteEnabled(false);
    });
    add(mainLayout, button);
  }

  private void setButtonsEditDeleteEnabled(boolean set) {
    buttonEditCar.setEnabled(set);
    buttonDeleteCar.setEnabled(set);
  }

  private void initializeGridCars() {
    gridCars = new Grid<>();
    gridCars.setItems(restService.getAllCars());
    gridCars.addColumn(Car::getId).setHeader("Id").setSortable(true);
    gridCars.addColumn(Car::getMark).setHeader("Mark").setSortable(true);
    gridCars.addColumn(Car::getModel).setHeader("Model").setSortable(true);
    gridCars.addColumn(Car::getColor).setHeader("Color").setSortable(true);
    gridCars.addItemClickListener(carItemClickEvent -> {
      setButtonsEditDeleteEnabled(!gridCars.getSelectedItems().isEmpty());
      selectedCar = carItemClickEvent.getItem();
     });
  }
}
