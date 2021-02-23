package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.awt.*;
import java.time.LocalDate;

public class Controller {

    @FXML private TextField fName;
    @FXML private TextField Username;
    @FXML private PasswordField Password;
    @FXML private TextField Email;
    @FXML private TextField PNumber;
    @FXML private DatePicker DateOfBirth;

    public void Register(ActionEvent actionEvent) {

        String username = Username.getText();
        System.out.println("The Username Entered: " + username);

        String password = Password.getText();
        System.out.println("The Password Entered: " + password);

        String name = fName.getText();
        System.out.println("The Name Entered: " + name);

        String email = Email.getText();
        System.out.println("The Email Entered: " + email);

        String phoneNumber = PNumber.getText();
        System.out.println("The PhoneNumber Entered: " + phoneNumber);

        LocalDate DOB = DateOfBirth.getValue();
        System.out.print("The Date of Birth Entered: " + DOB);


    }
}
