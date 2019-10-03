package pl.com.przepiora.week3rest.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import pl.com.przepiora.week3rest.model.Car;

public class DialogAdd extends Dialog {

    protected TextField textFieldMark;
    protected TextField textFieldModel;
    protected TextField textFieldColor;
    protected Button buttonAdd;
    protected HorizontalLayout buttons;
    protected Button buttonCancel;

    public DialogAdd() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        textFieldMark = new TextField("Mark");
        textFieldModel = new TextField("Model");
        textFieldColor = new TextField("Color");
        buttonAdd = new Button("Add");
        buttonCancel = new Button("Cancel");

        buttonAdd.addClickListener(buttonClickEvent -> {
            String mark = textFieldMark.getValue();
            String model = textFieldModel.getValue();
            String color = textFieldColor.getValue();
            Car car = Car.builder().mark(mark).model(model).color(color).build();
            RestService restService = new RestService();
            try {
                restService.addCar(car);
                this.close();
                Notification.show("Car was added.", 2000, Notification.Position.MIDDLE);
            } catch (Exception e) {
                Notification.show("Error: " + e.getMessage() + ".\n Car was not added!!!", 2000, Notification.Position.MIDDLE);
                e.printStackTrace();
            }
        });

        buttonCancel.addClickListener(buttonClickEvent -> this.close());

        buttons = new HorizontalLayout(buttonAdd, buttonCancel);
        mainLayout.add(textFieldMark, textFieldModel, textFieldColor, buttons);
        add(mainLayout);
    }
}
