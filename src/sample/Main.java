package sample;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    static final int SEATING_CAPACITY = 42;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        // Create a HashMap object called users to store userName and seatNumber
        HashMap<Integer, String> users = new HashMap<Integer, String>();

        // Converting HashMap values into ArrayList
        List<String> userNameList = new ArrayList<>(users.values());

        // Converting HashMap values into ArrayList
        List<Integer> userNumberList = new ArrayList<>(users.keySet());

        Scanner sc = new Scanner(System.in);

        Button seat = new Button();

        String actionType = new String();
        String name = new String();

        String userOption;

        System.out.println("\n***************************************************");
        System.out.println("*** DENUWARA MANIKE TRAIN SEATS BOOKING PROGRAM ***");
        System.out.println("***************************************************\n");

        do {
            System.out.println("Choose a option, which mentioned below\n");
            System.out.println("Prompt \"A\" to add a customer to a seat");
            System.out.println("Prompt \"V\" to display all seats");
            System.out.println("Prompt \"E\" to display empty seats");
            System.out.println("Prompt \"D\" to delete customer from a seat");
            System.out.println("Prompt \"F\" to find the seat for a given customer");
            System.out.println("Prompt \"S\" to store program data into a file");
            System.out.println("Prompt \"L\" to load program data from a file");
            System.out.println("Prompt \"O\" to view seats in ordered alphabetically by name");
            System.out.println("Prompt \"Q\" to exist from the program");

            System.out.print("\nPrompt your option : ");
            userOption = sc.next();

            switch (userOption) {
                case "A":
                case "a":
                    addCustomer(users, userNameList, userNumberList);
                    break;

                case "V":
                case "v":
                    displayAllSeats(users, userNameList, userNumberList);
                    break;

                case "E":
                case "e":
                    displayEmptySeats(users, userNameList, userNumberList);
                    break;

                case "D":
                case "d":
                    deleteCustomer(users, sc);
                    break;

                case "F":
                case "f":
                    findSeat(users, sc, userNameList);
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
                    alphabeticalOrder(users, userNameList);
                    break;

                case "q":
                case "Q":
                    System.out.println("Program is now Existing..");
                    break;

                default:
                    System.out.println("You have entered a Invalid Input!");
                    System.out.println("---------------------------------\n");
            }
        } while (!userOption.equals("q"));

        //create a mongodb
        MongoClient mongoClient = MongoClients.create();
        MongoCollection mongoCollection;
        MongoDatabase mongoDatabase = mongoClient.getDatabase("TrainSeatsBookingsProgram");
        mongoCollection = mongoDatabase.getCollection("Seats");
    }

    //type one alert box act as a confirmation box for the quit the current stage
    private void alertBoxWindowTypeOne(Stage window) {
        Stage alertBoxWindow = new Stage();

        //Block events to other windows
        alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
        alertBoxWindow.setTitle("Alert!");
        alertBoxWindow.setMinWidth(300);
        alertBoxWindow.setMinHeight(150);

        VBox layout = new VBox(10);
        GridPane gridPane = new GridPane();
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        alertBoxWindow.setScene(scene);

        Button okBtn = new Button("OK");
        Button cancelBtn = new Button("Cancel");

        Label label = new Label("Do you want to exit?");

        okBtn.setOnAction(event -> {
            alertBoxWindow.close();
            window.close();
        });

        cancelBtn.setOnAction(event -> alertBoxWindow.hide());

        layout.getChildren().add(label);
        layout.getChildren().add(gridPane);

        gridPane.add(okBtn, 0, 0);
        gridPane.add(cancelBtn, 1, 0);

        gridPane.setPadding(new Insets(0, 0, 0, 83));
        gridPane.setHgap(10);

        alertBoxWindow.showAndWait();
    }

    //type two alert box popup a new window and show messages according to the the parameters
    private void alertBoxWindowTypeTwo(String message) {
        Stage alertBoxWindow = new Stage();

        //Block events to other windows
        alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
        alertBoxWindow.setTitle("Alert!");
        alertBoxWindow.setMinWidth(300);
        alertBoxWindow.setMinHeight(150);

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

    private void loadProgramFile() {

    }

    private void storeData() {

    }

    private void allSeatsDisplay(HashMap<Integer, String> users, int i, Button seat) {
        if (users.containsKey(i)) {
            seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
        } else {
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        }
    }

    private void emptySeatsDisplay(HashMap<Integer, String> users, int i, Button seat) {
        if (users.containsKey(i)) {
            seat.setStyle(null);
            seat.setDisable(true);
        } else {
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        }
    }

    private void seatAction(Button seat, HashMap<Integer, String> users, int i, List<String> userNameList, List<Integer> userNumberList) {
        seat.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        });

        seat.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
        });

        if (users.containsKey(i)) {
            seat.setStyle(null);
            seat.setDisable(true);
        } else {
            seat.setOnAction(event -> {
                if (!users.containsKey(seat.getId())) {
                    try {
                        Stage confirmationBox = new Stage();
                        confirmationBox.initModality(Modality.APPLICATION_MODAL);

                        confirmationBox.setTitle("Confirmation");

                        VBox vBox = new VBox();
                        vBox.setAlignment(Pos.CENTER);

                        Text userNameTxt = new Text("Enter your name : ");
                        TextField userNameTxtField = new TextField();

                        Button confirmUser = new Button("Confirm");
                        confirmUser.setDisable(true);

                        vBox.getChildren().addAll(userNameTxt, userNameTxtField, confirmUser);
                        vBox.setPadding(new Insets(20));
                        vBox.setSpacing(10);

                        //user must enter at least one character as the name to confirm his/her booking
                        userNameTxtField.setOnKeyTyped(event1 -> {
                            confirmUser.setDisable(false);
                        });

                        confirmUser.setOnAction(event2 -> {
                            userNumberList.add(Integer.valueOf(seat.getId()));
                            //print the current action in console
                            System.out.println(userNameTxtField.getText() + " has booked Seat #" + seat.getId());

                            //if user name already added to the userNameList it won't added again
                            if (userNameList.contains(userNameTxtField.getText())) {
                                //put data to the hashMap
                                users.put(Integer.valueOf(seat.getId()), userNameTxtField.getText());
                            } else {
                                users.put(Integer.valueOf(seat.getId()), userNameTxtField.getText());
                                //add names to the userNameList
                                userNameList.add(userNameTxtField.getText());
                            }

                            //popup alertBox
                            alertBoxWindowTypeTwo("You have successfully booked Seat #" + seat.getId());
                            //change color of the booked seat and disable it
                            seat.setStyle(null);
                            seat.setDisable(true);
                            confirmationBox.close();
                        });

                        Scene confirmationBoxScene = new Scene(vBox, 300, 150);
                        confirmationBox.setScene(confirmationBoxScene);
                        confirmationBox.showAndWait();

                    } catch (Exception ignored) {
                        //ignoring the runtime error which occurs by JavaFX which I dont know exactly
                    }
                }
            });
        }
    }

    private void seatDisplay(HashMap<Integer, String> users, List<String> userNameList, List<Integer> userNumberList, VBox leftSeatsRowOne, VBox leftSeatsRowTwo, VBox RightSeatsRowOne, VBox RightSeatsRowTwo, String actionType) {
        for (int i = 1; i <= 11; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            leftSeatsRowOne.getChildren().add(seat);
            leftSeatsRowOne.setSpacing(5);

            if (actionType.equals("seatAction")) {
                seatAction(seat, users, i, userNameList, userNumberList);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplay(users, i, seat);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplay(users, i, seat);
            }
        }

        for (int i = 12; i <= 21; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            leftSeatsRowTwo.getChildren().add(seat);
            leftSeatsRowTwo.setSpacing(5);

            if (actionType.equals("seatAction")) {
                seatAction(seat, users, i, userNameList, userNumberList);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplay(users, i, seat);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplay(users, i, seat);
            }
        }

        for (int i = 22; i <= 31; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            RightSeatsRowOne.getChildren().add(seat);
            RightSeatsRowOne.setSpacing(5);
            RightSeatsRowOne.setPadding(new Insets(0, 0, 0, 75));

            if (actionType.equals("seatAction")) {
                seatAction(seat, users, i, userNameList, userNumberList);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplay(users, i, seat);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplay(users, i, seat);
            }
        }

        for (int i = 32; i <= SEATING_CAPACITY; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            RightSeatsRowTwo.getChildren().add(seat);
            RightSeatsRowTwo.setSpacing(5);

            if (actionType.equals("seatAction")) {
                seatAction(seat, users, i, userNameList, userNumberList);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplay(users, i, seat);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplay(users, i, seat);
            }
        }
    }

    private void addCustomer(HashMap<Integer, String> users, List<String> userNameList, List<Integer> userNumberList) {

        System.out.println("--------------------------------------------------");

        System.out.println("\n************************");
        System.out.println("ADD A CUSTOMER TO A SEAT");
        System.out.println("************************\n");

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Train Seat Booking Program");

        window.setOnCloseRequest(event -> {
            event.consume();
            alertBoxWindowTypeOne(window);
        });

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));

        Scene scene = new Scene(flowPane, 442, 600);

        Label header = new Label("Select a Seat");
        header.setFont(new Font("Arial Bold", 22));
        header.setTextFill(Paint.valueOf("#414141"));
        header.setPadding(new Insets(0, 200, 25, 125));

        flowPane.getChildren().add(header);

        VBox leftSeatsRowOne = new VBox();
        VBox leftSeatsRowTwo = new VBox();
        VBox RightSeatsRowOne = new VBox();
        VBox RightSeatsRowTwo = new VBox();

        seatDisplay(users, userNameList, userNumberList, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "seatAction");

        flowPane.getChildren().addAll(leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo);

        Button emptySpace = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(450, 10);

        Button emptySpace1 = new Button();
        emptySpace1.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace1.setMinSize(150, 10);

        //close button
        Button closeBtn = new Button();
        closeBtn.setText("Close");
        closeBtn.setOnAction(e -> {
            alertBoxWindowTypeOne(window);
        });

        flowPane.getChildren().addAll(emptySpace, emptySpace1, closeBtn);

        window.setScene(scene);
        window.showAndWait();

        System.out.println("\n--------------------------------------------------");
    }

    private void displayEmptySeats(HashMap<Integer, String> users, List<String> userNameList, List<Integer> userNumberList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*******************");
        System.out.println("DISPLAY EMPTY SEATS");
        System.out.println("*******************\n");

        Stage window = new Stage();
        window.setTitle("Train Seat Booking Program");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));

        Scene scene = new Scene(flowPane, 442, 600);

        Label header = new Label("Check Available Seats");
        header.setFont(new Font("Arial Bold", 22));
        header.setTextFill(Paint.valueOf("#414141"));
        header.setPadding(new Insets(0, 150, 25, 80));

        flowPane.getChildren().addAll(header);

        VBox leftSeatsRowOne = new VBox();
        VBox leftSeatsRowTwo = new VBox();
        VBox RightSeatsRowOne = new VBox();
        VBox RightSeatsRowTwo = new VBox();

        seatDisplay(users, userNameList, userNumberList, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "emptySeats");

        flowPane.getChildren().addAll(leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo);

        Button emptySpace = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(450, 10);

        Button colorOneButton = new Button();
        colorOneButton.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        colorOneButton.setMinSize(33, 10);

        Label colorOneLabel = new Label("Available Seats");

        Button emptySpace2 = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(450, 10);

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> window.close());

        flowPane.getChildren().addAll(emptySpace, colorOneButton, colorOneLabel, closeBtn);
        window.setScene(scene);
        window.showAndWait();

        System.out.println("\n--------------------------------------------------");
    }

    private void displayAllSeats(HashMap<Integer, String> users, List<String> userNameList, List<Integer> userNumberList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*****************");
        System.out.println("DISPLAY ALL SEATS");
        System.out.println("*****************\n");

        Stage window = new Stage();
        window.setTitle("Train Seat Booking Program");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));

        Scene scene = new Scene(flowPane, 442, 600);

        Label header = new Label("All Seats");
        header.setFont(new Font("Arial Bold", 22));
        header.setTextFill(Paint.valueOf("#414141"));
        header.setPadding(new Insets(0, 165, 25, 145));

        flowPane.getChildren().addAll(header);

        VBox leftSeatsRowOne = new VBox();
        VBox leftSeatsRowTwo = new VBox();
        VBox RightSeatsRowOne = new VBox();
        VBox RightSeatsRowTwo = new VBox();

        seatDisplay(users, userNameList, userNumberList, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "allSeats");

        flowPane.getChildren().addAll(leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo);

        Button emptySpace = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(450, 10);

        Button colorOneButton = new Button();
        colorOneButton.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        colorOneButton.setMinSize(33, 10);

        Label colorOneLabel = new Label("Available Seats");

        Button colorTwoButton = new Button();
        colorTwoButton.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
        colorTwoButton.setMinSize(33, 10);

        Label colorTwoLabel = new Label("Booked Seats");

        flowPane.getChildren().addAll(emptySpace, colorOneButton, colorOneLabel, colorTwoButton, colorTwoLabel);

        window.setScene(scene);
        window.showAndWait();

        System.out.println("\n--------------------------------------------------");
    }

    private void findSeat(HashMap<Integer, String> users, Scanner sc, List<String> userNameList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**************");
        System.out.println("FIND USER SEAT");
        System.out.println("**************\n");

        if (users.isEmpty()) {
            System.out.println("No seats have been booked yet!");
        } else {
            System.out.print("Prompt your name to find the seat : ");
            String findUserName = sc.next();
            if (users.containsValue(findUserName)) {
                for (String s : userNameList) {
                    if (s.equalsIgnoreCase(findUserName)) {
                        for (Object o : users.keySet()) {
                            if (users.get(o).equalsIgnoreCase(findUserName)) {
                                System.out.println(s + "-" + o);
                            }
                        }
                    }
                }
            } else {
                System.out.println("\nNo seat has been booked under " + findUserName);
            }
        }
        System.out.println("\n--------------------------------------------------");
    }

    public void deleteCustomer(HashMap<Integer, String> users, Scanner sc) {
        System.out.println("--------------------------------------------------");
        int removedSeatNumber;
        String removedSeatName;

        System.out.println("\n*************");
        System.out.println("DELETE A SEAT");
        System.out.println("*************\n");

        if (users.isEmpty()) {
            System.out.println("No seats have been booked yet!");
        } else {
            System.out.print("What is the name that you prompted to book your seat (Prompt Username) : ");
            removedSeatName = sc.next();

            if (!users.containsValue(removedSeatName)) {
                System.out.println("\nNo seat has been booked under " + removedSeatName);
            } else {
                System.out.println("\nYou have booked these seats;");
                if (users.containsValue(removedSeatName)) {
                    for (HashMap.Entry<Integer, String> entry : users.entrySet()) {
                        if (removedSeatName.equals(entry.getValue())) {
                            System.out.print(entry.getKey() + " ");
                        }
                    }

                    System.out.print("Which seat do you want to delete (Prompt Seat Number) : ");
                    while (!sc.hasNextInt()) {
                        System.out.println("Prompt Integers!!");
                        System.out.print("\nWhich seat do you want to delete (Prompt Seat Number) : ");
                        sc.next();
                    }
                    removedSeatNumber = sc.nextInt();

                    if (users.containsKey(removedSeatNumber)) {
                        users.remove(removedSeatNumber);
                        System.out.println("\nSeat #" + removedSeatNumber + " is successfully deleted!");
                    }
                }
            }
            System.out.println("\n--------------------------------------------------");
        }
    }

    private void alphabeticalOrder(HashMap<Integer, String> users, List<String> userNameList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*************************************************");
        System.out.println("VIEW SEATS IN ORDERED ALPHABETICALLY BY USER NAME");
        System.out.println("*************************************************\n");

        String temp;

        try {
            if (users.isEmpty()) {
                System.out.println("No seats have been booked yet!");
            } else {
                for (int i = 0; i < userNameList.size(); i++) {
                    for (int j = i + 1; j < userNameList.size(); j++) {
                        if (userNameList.get(i).compareTo(userNameList.get(j)) > 0) {
                            temp = userNameList.get(i);
                            userNameList.set(i, userNameList.get(j));
                            userNameList.set(j, temp);
                        }
                    }
                }

                for (String s : userNameList) {
                    for (Object o : users.keySet() ) {
                        if (users.get(o).equalsIgnoreCase(s)) {
                            System.out.println(s + " has booked seat #" + o);
                        }
                    }
                }
            }
        } catch (Exception ignored) {
            //ignored
        }

        System.out.println("\n--------------------------------------------------");
    }
}
