package sample;

import company.SaveObjectStatus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Shop");
        primaryStage.setScene(new Scene(root, 900, 550));
        primaryStage.setOnCloseRequest(e-> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("EXIT");
            alert.setContentText("Do you want to save changes?");
            alert.setHeaderText("");
            alert.setGraphic(null);

            Optional<ButtonType> result;
            result=alert.showAndWait();

            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                SaveObjectStatus.serialSaveContainer(Controller.fasada.getContainer());
                SaveObjectStatus.serialSaveUser(Controller.user);
            }

        });
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
