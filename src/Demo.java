import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.Scanner;

public class Demo extends Application {

    public static void main(String[] args) {
        launch();

    }

    @Override
    public void start (Stage primaryStage) throws Exception{
        String[][][][] data = new String[2][30][42][3];

        Stage primary = new Stage();
        FlowPane flowPane = new FlowPane();
        DatePicker datePicker = new DatePicker();
        int pickedDate = Integer.parseInt(String.valueOf(datePicker.getValue()).substring(8, 10));
        //System.out.println(day);
        //int pickedDate = Integer.parseInt(String.valueOf(datePicker.getValue()).substring(8, 10));
        System.out.println(pickedDate);

        flowPane.getChildren().add(datePicker);
        Scene scene = new Scene(flowPane,500,500);
        primary.setScene(scene);
        primary.showAndWait();

        Scanner sc = new Scanner(System.in);
        System.out.println("1 for to badulla");
        System.out.println("2 for to colombo");
        System.out.println("Enter here");
        int inout = sc.nextInt();

        Stage second = new Stage();
        FlowPane flowPane1 = new FlowPane();

        for (int i=1; i<=42; i++){
            Button button = new Button(String.valueOf(i));
            button.setId(String.valueOf(i));
            flowPane1.getChildren().add(button);

            button.setOnAction((e)->{
                Stage stage = new Stage();
                FlowPane flowPane2 = new FlowPane();
                TextField textField = new TextField();
                Button button1 = new Button("go");
                button1.setOnAction((x)->{
                    String name= textField.getText();
                    String seat = button.getId();
                    //data[inout][][][] = name;

                });


                flowPane2.getChildren().add(textField);
                flowPane2.getChildren().add(button1);

                Scene scene1 = new Scene(flowPane2);
                stage.setScene(scene1);
                stage.showAndWait();

            });
        }

        Scene scene1=new Scene(flowPane1);
        second.setScene(scene1);
        second.showAndWait();





    }

}
