package pl.com.przepiora.week3rest.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import pl.com.przepiora.week3rest.model.Car;


public class DialogUpdate extends DialogAdd {
    private Car carUpdate;
    private Button buttonUpdate;

    public DialogUpdate(Car car) {
        super();
        this.carUpdate = car;
        buttonUpdate = new Button("Update");
        buttons.replace(buttonAdd, buttonUpdate);
        textFieldMark.setValue(car.getMark());
        textFieldModel.setValue(car.getModel());
        textFieldColor.setValue(car.getColor());

        buttonUpdate.addClickListener(buttonClickEvent -> {
            Long id = carUpdate.getId();
            String mark = textFieldMark.getValue();
            String model = textFieldModel.getValue();
            String color = textFieldColor.getValue();
            Car resultCar = Car.builder().mark(mark).model(model).color(color).build();
            RestService restService = new RestService();
            try {
                restService.updateCar(id, resultCar);
                this.close();
                Notification.show("Car was updated.", 2000, Notification.Position.MIDDLE);
            } catch (Exception e) {
                Notification.show("Error: " + e.getMessage() + ".\n Car was not updated!!!", 2000, Notification.Position.MIDDLE);
                e.printStackTrace();
            }
        });
    }
}
