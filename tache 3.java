controller class:

package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import server.models.Course;
import server.models.RegistrationForm;

import java.rmi.Naming;
import java.util.List;

public class Controller
    private ObservableList <Course> courseList= FXCollections.ObservableArrayList();
    private final String serverURL= "rmi://localhost/RegistrationService";
    private RegistrationService registrationService;

    @FXML
    private ComboBox<String> sessionComboBox;
    private Button loadButton;
    private ComboBox<Course> courseComboBox;
    private TextField prenom;
    private TextField nom;
    private TextField email;
    private TextField matricule;
    private Button registerButton;
    private Text statusText;

    @FXML

    public void initialize() throws Exception{
        registrationService= (RegistrationService) Naming.lookup(serverURL);
        List<String> sessions= registrationService.getSessions();
        sessionComboBox.setItems(FXCollections.observableArrayList(sessions));
    }

    sessionComboBox.getItems().addAll(sessions);
    courseComboBox.setItems(courseList);
    courseComboBox.setDisable(true);
    registerButton.setDisable(true);
}

@FXML

void handleLoad (ActionEvent event){

    String selectedSession= sessionComboBox.getSelectionModel().getSelectedItem();

    if (selectedSession==null){
        try{

            List<Course> courses= registrationService.getCourses(selectedSession);
            courseList.setAll(courses);
            courseComboBox.setDisable(false);
            registerButton.setDisable(false);;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

@FXML

void handleRegister(ActionEvent event){
    String prenom= prenom.getText();
    String nom= nom.getText();
    String email= email.getText();
    String matricule= matricule.getText();
    Course selectedCourse= courseComboBox.getSelectionModel().getSelectedItem();

    if (selectedCourse !=null){

    RegistrationForm form= new RegistrationForm(prenom, nom, email, matricule, selectedCourse);
    try{
        String status= registrationService.register(registrationForm);
        statusText.setText(status);
    } catch (Exception e){
        e.printStackTrace();
    }
}






   
}