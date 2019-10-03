package pl.com.przepiora.week3rest.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
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
    private Button buttonEditCar;
    private Button buttonDeleteCar;
    private Car selectedCar;

    public MainView() {
        H1 header = new H1("Akademia Spring");
        restService = new RestService();
        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidth("50%");
        mainLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        initializeGridCars();

        Button buttonAddCar = new Button("Add");
        buttonEditCar = new Button("Edit");
        buttonDeleteCar = new Button("Delete");
        buttonAddCar.addClickListener(buttonClickEvent -> {
            Dialog dialogAddEdit = new DialogAdd();
            dialogAddEdit.open();
            dialogAddEdit.addDetachListener(detachEvent -> {
                gridCars.setItems(restService.getAllCars());
                setButtonsEditDeleteEnabled(false);
            });
        });
        buttonEditCar.addClickListener(buttonClickEvent -> {
            Dialog dialogUpdate = new DialogUpdate(selectedCar);
            dialogUpdate.open();
            dialogUpdate.addDetachListener(detachEvent -> {
                gridCars.setItems(restService.getAllCars());
                setButtonsEditDeleteEnabled(false);
            });
        });
        buttonDeleteCar.addClickListener(buttonClickEvent -> {
            Dialog dialogConfirmDelete = new DialogConfirmDelete(selectedCar);
            dialogConfirmDelete.open();
            dialogConfirmDelete.addDetachListener(detachEvent -> {
                gridCars.setItems(restService.getAllCars());
                setButtonsEditDeleteEnabled(false);
            });
        });
        setButtonsEditDeleteEnabled(false);
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(buttonAddCar, buttonEditCar, buttonDeleteCar);
        mainLayout.add(header, gridCars, buttonsLayout);
        add(mainLayout);
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
