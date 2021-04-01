package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExceptonHandler {

    public static void show(String text){
        Stage window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);
        window.setMinHeight(150);
        window.setTitle("Exception Handler");

        Label label=new Label(text);
        HBox layout=new HBox();
        layout.getChildren().add(label);
        layout.setAlignment(Pos.CENTER);

        Scene scene=new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
