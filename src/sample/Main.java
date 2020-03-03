package sample;

import com.sun.javafx.binding.StringFormatter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    static final int SEATING_CAPACITY = 42;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        // Create a HashMap object called users to store userName and seatNumber
        HashMap<String, Integer> users = new HashMap<String, Integer>();
        List<String> bookedSeatsCustomersNameList = new ArrayList<>();
        List<Integer> bookedSeatsNumberList = new ArrayList<>();
        int seatNumber = 0;

        Scanner sc = new Scanner(System.in);
        String userOption;

        System.out.println("\n***************************");
        System.out.println("TRAIN SEATS BOOKING PROGRAM");
        System.out.println("***************************");

        while (true) {
            System.out.println("\nChoose a option, which mentioned below\n");
            System.out.println("Prompt \"A\" to add a customer to a seat");
            System.out.println("Prompt \"V\" to display all seats");
            System.out.println("Prompt \"E\" to display empty seats");
            System.out.println("Prompt \"D\" to delete customer from a seat");
            System.out.println("Prompt \"F\" to find the seat for a given customer");
            System.out.println("Prompt \"S\" to store program data into a file");
            System.out.println("Prompt \"L\" to load program data from a file");
            System.out.println("Prompt \"O\" to view seats in ordered alphabetically by name");
            System.out.println("Prompt \"Q\" to exist from the program");

            System.out.print("\nPrompt your option: ");
            userOption = sc.next();

            switch (userOption) {
                case "A":
                case "a":
                    addCustomer(users, bookedSeatsCustomersNameList, bookedSeatsNumberList, seatNumber);
                    break;

                case "V":
                case "v":
                    displayAllSeats();
                    break;

                case "E":
                case "e":
                    displayEmptySeats();
                    break;

                case "D":
                case "d":
                    deleteCustomer();
                    break;

                case "F":
                case "f":
                    findSeat();
                    break;

                case "S":
                case "s":
                    storeData();
                    break;

                case "L":
                case "l":
                    loadProgramFile();
                    break;

                case "O":
                case "o":
                    alphabeticalOrder();
                    break;

                default:
                    System.out.println("You have entered a Invalid Input!");
                    System.out.println("---------------------------------");
            }
        }
    }

    private void alphabeticalOrder() {

    }

    private void loadProgramFile() {

    }

    private void storeData() {

    }

    private void findSeat() {
    }

    private void deleteCustomer() {
    }

    private void displayEmptySeats() {
    }

    private void addCustomer(HashMap<String, Integer> users, List<String> bookedSeatsCustomersNameList, List<Integer> bookedSeatsNumberList, int seatNumber) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n------------------------");
        System.out.println("ADD A CUSTOMER TO A SEAT");
        System.out.println("------------------------");

        Stage window = new Stage();
        window.setTitle("Train Seat Booking Program");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        Scene scene1 = new Scene(flowPane, 600, 500);

        //close button
        Button closeBtn = new Button();
        closeBtn.setText("Close");
        closeBtn.setOnAction(e -> window.close());

        for (int i = 1; i <= SEATING_CAPACITY; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            flowPane.getChildren().add(seat);
            seat.setOnAction(event -> {
                try {
                    //set a style for the seat button
                    seat.setStyle("-fx-background-color: MediumSeaGreen");
                    //current seat number to the arrayList
                    Integer.valueOf(seat.getId());
                    bookedSeatsNumberList.add(Integer.valueOf(seat.getId()));
                } catch(Exception ignored) {
                    //ignoring the runtime error which occurs by JavaFX
                }
            });

            //book button
            Button bookBtn = new Button();
            bookBtn.setText("Book");
            flowPane.getChildren().add(bookBtn);

            //book button action
            bookBtn.setOnAction(event -> {
                try {
                    GridPane grid = new GridPane();
                    grid.setPadding(new Insets(10, 10, 10, 10));
                    grid.setMinSize(300, 300);
                    grid.setVgap(5);
                    grid.setHgap(5);

                    Scene scene2 = new Scene(grid, 600, 500);

                    Text username = new Text("Enter your name : ");
                    grid.add(username, 0, 0);

                    TextField userNameTxtField = new TextField();
                    userNameTxtField.getText();
                    userNameTxtField.setPrefColumnCount(10);
                    grid.add(userNameTxtField, 1, 0);

                    Button confirmUser = new Button("Confirm");
                    grid.add(confirmUser, 2, 0);

                    confirmUser.setOnAction(event1 -> {
                        bookedSeatsCustomersNameList.add(String.valueOf(userNameTxtField.getText()));
                        grid.add(confirmUser, 2, 0);
                        //print the current action in console
                        System.out.println(userNameTxtField.getText() + "has booked Seat #" + Integer.valueOf(seat.getId()));
                    });

                    window.setScene(scene2);
                    window.showAndWait();

                } catch (Exception ignored) {
                    //ignoring the runtime error which occurs by JavaFX
                }
            });

            //users.put(userName, seatNumber);
        }

        window.setScene(scene1);
        window.showAndWait();

        System.out.println("\nYour seat is successfully booked!");
    }

    private void displayAllSeats() {

    }

    private void alertBoxWindow(String title, String message) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(200);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
