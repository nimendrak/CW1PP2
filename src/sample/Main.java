package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

        int seatNumber = 0;

        Scanner sc = new Scanner(System.in);
        String userOption;

        System.out.println("\n***************************");
        System.out.println("TRAIN SEATS BOOKING PROGRAM");
        System.out.println("***************************");

        do {
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
                    addCustomer(users, seatNumber);
                    break;

                case "V":
                case "v":
                    displayAllSeats(users);
                    break;

                case "E":
                case "e":
                    displayEmptySeats(users);
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

                case "q":
                case"Q":
                    System.out.println("Program is now Existing..");
                    break;

                default:
                    System.out.println("You have entered a Invalid Input!");
                    System.out.println("---------------------------------");
            }
        } while(!userOption.equals("q"));
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

    private void displayEmptySeats(HashMap<String, Integer> users) {
        System.out.println("\n-----------------");
        System.out.println("DISPLAY EMPTY SEATS");
        System.out.println("-------------------");

        Stage window = new Stage();
        window.setTitle("Train Seat Booking Program");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        Scene scene = new Scene(flowPane, 600, 500);
        for (int i = 1; i <= SEATING_CAPACITY; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            flowPane.getChildren().add(seat);

            if (users.containsValue(i)) {
                seat.setDisable(true);
            } else {
                seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            }
        }
        window.setScene(scene);
        window.showAndWait();
    }

    private void addCustomer(HashMap<String, Integer> users, int seatNumber) {
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
            seat.setId(Integer.toString(i));
            flowPane.getChildren().add(seat);
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");

            if (users.containsValue(i)) {
                seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
            } else {
                seat.setOnAction(event -> {
                    if (!users.containsValue(seat.getId())) {
                        try {
                            Stage window2 = new Stage();

                            GridPane grid = new GridPane();
                            grid.setPadding(new Insets(10, 10, 10, 10));
                            grid.setMinSize(300, 300);
                            grid.setVgap(5);
                            grid.setHgap(5);

                            Text username = new Text("Enter your name : ");
                            grid.add(username, 0, 0);

                            TextField userName = new TextField();
                            grid.add(userName, 1, 0);

                            Button confirmUser = new Button("Confirm your Seat #" + Integer.valueOf(seat.getId()));
                            grid.add(confirmUser, 0, 2);

                            Button closeButton1 = new Button("Close");
                            grid.add(closeButton1, 0, 3);
                            closeButton1.setOnAction(e -> window2.close());

                            confirmUser.setOnAction(event1 -> {
                                //print the current action in console
                                System.out.println(userName.getText() + " has booked Seat #" + Integer.valueOf(seat.getId()));
                                //hashMap data
                                users.put(userName.getText(), Integer.valueOf(seat.getId()));
                                //pop alertbox
                                alertBoxWindow("Alert!", "You have successfully booked Seat #" + Integer.valueOf(seat.getId()));
                                //change color of the booked seat
                                seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
                            });

                            Scene scene2 = new Scene(grid, 400, 200);
                            window2.setScene(scene2);
                            window2.showAndWait();

                        } catch (Exception ignored) {
                            //ignoring the runtime error which occurs by JavaFX
                        }
                    }
                });
            }
        }
        window.setScene(scene1);
        window.showAndWait();
    }

    private void displayAllSeats(HashMap<String, Integer> users) {
        System.out.println("\n-----------------");
        System.out.println("DISPLAY ALL SEATS");
        System.out.println("-----------------");

        Stage window = new Stage();
        window.setTitle("Train Seat Booking Program");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        Scene scene = new Scene(flowPane, 600, 500);
        for (int i = 1; i <= SEATING_CAPACITY; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            flowPane.getChildren().add(seat);

            if (users.containsValue(i)) {
                seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
            } else {
                seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            }
        }
        window.setScene(scene);
        window.showAndWait();
    }

    private void alertBoxWindow(String title, String message) {
        Stage alertBoxWindow = new Stage();

        //Block events to other windows
        alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
        alertBoxWindow.setTitle(title);
        alertBoxWindow.setMinWidth(300);
        alertBoxWindow.setMinHeight(200);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> alertBoxWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        alertBoxWindow.setScene(scene);
        alertBoxWindow.showAndWait();
    }

}
