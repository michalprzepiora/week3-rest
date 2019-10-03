package pl.com.przepiora.week3rest.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.com.przepiora.week3rest.model.Car;

public class DialogConfirmDelete extends Dialog {

    public DialogConfirmDelete(Car selectedCar) {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        Label messageLabel = new Label("Are you sure delete " + selectedCar.getMark() + " " + selectedCar.getModel() + "?");
        HorizontalLayout buttons = new HorizontalLayout();
        Button buttonYes = new Button("Yes");
        Button buttonNo = new Button("No");
        buttons.add(buttonYes, buttonNo);

        buttonYes.addClickListener(buttonClickEvent -> {
            RestService restService = new RestService();
            try {
                restService.deleteCarById(selectedCar.getId());
                this.close();
                Notification.show("Car was deleted.", 2000, Notification.Position.MIDDLE);
            } catch (Exception e) {
                Notification.show("Error: " + e.getMessage() + ".\n Car was not deleted!!!", 2000, Notification.Position.MIDDLE);
            }
        });
        buttonNo.addClickListener(buttonClickEvent -> this.close());
        mainLayout.add(messageLabel, buttons);
        add(mainLayout);
    }
}
