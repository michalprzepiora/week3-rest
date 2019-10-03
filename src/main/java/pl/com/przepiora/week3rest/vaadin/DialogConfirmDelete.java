package pl.com.przepiora.week3rest.vaadin;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import pl.com.przepiora.week3rest.model.Car;

public class DialogConfirmDelete extends Dialog {

  public DialogConfirmDelete(Car selectedCar) {
    Label messageLabel = new Label("Are you sure delete " + selectedCar.getMark() + " " + selectedCar.getModel() + "?");

    add(messageLabel);
  }
}
