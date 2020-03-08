package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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

import java.io.*;
import java.util.*;

public class Main extends Application {

    static final int SEATING_CAPACITY = 42;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        // Create a HashMap object called users to store userName and seatNumber
        HashMap<Integer, String> passengers = new HashMap<Integer, String>();

        // Converting HashMap values into ArrayList
        List<String> userNameList = new ArrayList<>(passengers.values());

        Scanner sc = new Scanner(System.in);

        String userOption;

        System.out.println("\n***************************************************");
        System.out.println("*** DENUWARA MANIKE TRAIN SEATS BOOKING PROGRAM ***");
        System.out.println("***************************************************\n");

        do {
            System.out.println("Choose a option, which mentioned below\n");
            System.out.println("Prompt \"A\" to add a customer to a seat");
            System.out.println("Prompt \"V\" to display all seats");
            System.out.println("Prompt \"E\" to display available seats");
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
                    addCustomer(passengers, userNameList);
                    break;

                case "V":
                case "v":
                    displayAllSeats(passengers, userNameList);
                    break;

                case "E":
                case "e":
                    displayAvailableSeats(passengers, userNameList);
                    break;

                case "D":
                case "d":
                    deleteCustomer(passengers, sc);
                    break;

                case "F":
                case "f":
                    findSeat(passengers, sc, userNameList);
                    break;

                case "S":
                case "s":
                    storeData(passengers);
                    break;

                case "L":
                case "l":
                    loadProgramFile(passengers, userNameList);
                    break;

                case "O":
                case "o":
                    alphabeticalOrder(passengers, userNameList);
                    break;

                case "q":
                case "Q":
                    System.out.print("\nIf you want to store data before you exit from the program\nPrompt \"S\" or prompt any key to Exit : ");
                    String option = sc.next();
                    if (option.equalsIgnoreCase("s")) {
                        storeData(passengers);
                    } else {
                        System.out.println("\nProgram is now Exiting..");
                    }
                    break;

                default:
                    System.out.println("\nYou have entered a Invalid Input!");
                    System.out.println("---------------------------------\n");
            }
        } while (!userOption.equals("q"));
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

    private void loadProgramFile(HashMap<Integer, String> passengers, List<String> userNameList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**********************");
        System.out.println("LOAD PROGRAM FROM DATA");
        System.out.println("**********************\n");

        BufferedReader bufferedReader = null;

        try {

            //create file object
            File file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\hashMapData.txt");

            //create BufferedReader object from the File
            bufferedReader = new BufferedReader(new FileReader(file));

            String line;

            //read file line by line
            while ((line = bufferedReader.readLine()) != null) {

                //split the line by :
                String[] parts = line.split(" has booked seat #");

                //first part is name, second is age
                String passengerName = parts[0].trim();
                Integer passengerSeat = Integer.parseInt(parts[1].trim());

                //put name, age in HashMap if they are not empty
                if (!passengerName.equals("") && !passengerSeat.equals(""))
                    passengers.put(passengerSeat, passengerName);
                userNameList.add(passengerName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //Always close the BufferedReader
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    System.out.println("Stored data has been successfully loaded to the program!\nTip - Prompt \"E\" to check available seats");
                } catch (Exception e) {
                }
            }
        }

        System.out.println("\n--------------------------------------------------");
    }

    private void storeData(HashMap<Integer, String> passengers) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**********");
        System.out.println("STORE DATA");
        System.out.println("**********\n");

        //new file object
        File file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\hashMapData.txt");
        BufferedWriter bufferedWriter = null;

        if (passengers.isEmpty()) {
            System.out.println("No seats have been booked yet!");
        } else {
            try {
                //create new BufferedWriter for the output file
                bufferedWriter = new BufferedWriter(new FileWriter(file));
                //iterate map entries
                for (Map.Entry<Integer, String> entry : passengers.entrySet()) {
                    //put key and value
                    bufferedWriter.write(entry.getValue() + " has booked seat #" + entry.getKey());
                    //new line
                    bufferedWriter.newLine();
                }

                bufferedWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    System.out.println("Data has been successfully stored!");
                    //always close the writer
                    bufferedWriter.close();
                } catch (Exception e) {
                    //error ignored
                }
            }
        }
        System.out.println("\n--------------------------------------------------");
    }

    private void allSeatsDisplay(HashMap<Integer, String> passengers, int i, Button seat) {
        if (passengers.containsKey(i)) {
            seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
        } else {
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        }
    }

    private void emptySeatsDisplay(HashMap<Integer, String> passengers, int i, Button seat) {
        if (passengers.containsKey(i)) {
            seat.setStyle(null);
            seat.setDisable(true);
        } else {
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        }
    }

    private void seatAction(Button seat, HashMap<Integer, String> passengers, int i, List<String> userNameList) {
        seat.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        });

        seat.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
        });

        if (passengers.containsKey(i)) {
            seat.setStyle(null);
            seat.setDisable(true);
        } else {
            seat.setOnAction(event -> {
                if (!passengers.containsKey(seat.getId())) {
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
                            if (userNameTxtField.getText().isEmpty()) {
                                userNameTxtField.setStyle("-fx-faint-focus-color: transparent;");
                                confirmUser.setDisable(true);
                            } else {
                                userNameTxtField.setStyle("-fx-border-color: #00A69C; -fx-border-width: 2px;");
                                confirmUser.setDisable(false);
                            }
                        });

                        confirmUser.setOnAction(event2 -> {
                            //print the current action in console
                            System.out.println(userNameTxtField.getText().toLowerCase() + " has booked Seat #" + seat.getId());

                            //if user name already added to the userNameList it won't added again
                            if (userNameList.contains(userNameTxtField.getText().toLowerCase())) {
                                //put data to the hashMap
                                passengers.put(Integer.valueOf(seat.getId()), userNameTxtField.getText().toLowerCase());
                            } else {
                                passengers.put(Integer.valueOf(seat.getId()), userNameTxtField.getText().toLowerCase());
                                //add names to the userNameList
                                userNameList.add(userNameTxtField.getText().toLowerCase());
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

    private void seatDisplay(HashMap<Integer, String> passengers, List<String> userNameList, VBox
            leftSeatsRowOne, VBox leftSeatsRowTwo, VBox RightSeatsRowOne, VBox RightSeatsRowTwo, String actionType) {
        for (int i = 1; i <= 11; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            leftSeatsRowOne.getChildren().add(seat);
            leftSeatsRowOne.setSpacing(5);
            seat.setCursor(Cursor.HAND);

            if (actionType.equals("seatAction")) {
                seatAction(seat, passengers, i, userNameList);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplay(passengers, i, seat);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplay(passengers, i, seat);
            }
        }

        for (int i = 12; i <= 21; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            leftSeatsRowTwo.getChildren().add(seat);
            leftSeatsRowTwo.setSpacing(5);
            seat.setCursor(Cursor.HAND);

            if (actionType.equals("seatAction")) {
                seatAction(seat, passengers, i, userNameList);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplay(passengers, i, seat);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplay(passengers, i, seat);
            }
        }

        for (int i = 22; i <= 31; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            RightSeatsRowOne.getChildren().add(seat);
            RightSeatsRowOne.setSpacing(5);
            RightSeatsRowOne.setPadding(new Insets(0, 0, 0, 75));
            seat.setCursor(Cursor.HAND);

            if (actionType.equals("seatAction")) {
                seatAction(seat, passengers, i, userNameList);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplay(passengers, i, seat);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplay(passengers, i, seat);
            }
        }

        for (int i = 32; i <= SEATING_CAPACITY; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            RightSeatsRowTwo.getChildren().add(seat);
            RightSeatsRowTwo.setSpacing(5);
            seat.setCursor(Cursor.HAND);

            if (actionType.equals("seatAction")) {
                seatAction(seat, passengers, i, userNameList);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplay(passengers, i, seat);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplay(passengers, i, seat);
            }
        }
    }

    private void addCustomer(HashMap<Integer, String> passengers, List<String> userNameList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n************************");
        System.out.println("ADD A CUSTOMER TO A SEAT");
        System.out.println("************************\n");

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Train Seats Booking Program");

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

        seatDisplay(passengers, userNameList, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "seatAction");

        flowPane.getChildren().addAll(leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo);

        Button emptySpace = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(450, 10);

        Button emptySpace1 = new Button();
        emptySpace1.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace1.setMinSize(150, 10);

        //close button
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> {
            alertBoxWindowTypeOne(window);
        });

        flowPane.getChildren().addAll(emptySpace, emptySpace1, closeBtn);

        window.setScene(scene);
        window.showAndWait();

        System.out.println("\n--------------------------------------------------");
    }

    private void displayAvailableSeats(HashMap<Integer, String> passengers, List<String> userNameList) {
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

        seatDisplay(passengers, userNameList, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "emptySeats");

        flowPane.getChildren().addAll(leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo);

        Button emptySpace = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(450, 10);

        Button colorOneButton = new Button();
        colorOneButton.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        colorOneButton.setMinSize(33, 10);

        Label colorOneLabel = new Label("Available Seats");

        Button emptySpace2 = new Button();
        emptySpace2.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace2.setMinSize(163, 10);

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> window.close());

        flowPane.getChildren().addAll(emptySpace, colorOneButton, colorOneLabel, emptySpace2, closeBtn);
        window.setScene(scene);
        window.showAndWait();

        System.out.println("--------------------------------------------------");
    }

    private void displayAllSeats(HashMap<Integer, String> passengers, List<String> userNameList) {
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

        seatDisplay(passengers, userNameList, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "allSeats");

        flowPane.getChildren().addAll(leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo);

        Button emptySpace = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(450, 10);

        Button emptySpace2 = new Button();
        emptySpace2.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace2.setMinSize(20, 10);

        Button colorOneButton = new Button();
        colorOneButton.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        colorOneButton.setMinSize(33, 10);

        Label colorOneLabel = new Label("Available Seats");

        Button colorTwoButton = new Button();
        colorTwoButton.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
        colorTwoButton.setMinSize(33, 10);

        Label colorTwoLabel = new Label("Booked Seats");

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> window.close());

        flowPane.getChildren().addAll(emptySpace, colorOneButton, colorOneLabel, colorTwoButton, colorTwoLabel, emptySpace2, closeBtn);

        window.setScene(scene);
        window.showAndWait();

        System.out.println("--------------------------------------------------");
    }

    private void findSeat(HashMap<Integer, String> passengers, Scanner sc, List<String> userNameList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**************");
        System.out.println("FIND USER SEAT");
        System.out.println("**************\n");

        if (passengers.isEmpty()) {
            System.out.println("No seats have been booked yet!");
        } else {
            System.out.println("Prompt your name to find the seat : ");
            String findUserName = sc.next();
            if (passengers.containsValue(findUserName)) {
                for (String s : userNameList) {
                    if (s.equalsIgnoreCase(findUserName)) {
                        for (Object o : passengers.keySet()) {
                            if (passengers.get(o).equalsIgnoreCase(findUserName)) {
                                System.out.println("\n" + s + " has booked Seat #" + o);
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

    public void deleteCustomer(HashMap<Integer, String> passengers, Scanner sc) {
        System.out.println("--------------------------------------------------");
        int removedSeatNumber;
        String removedSeatName;

        System.out.println("\n*************");
        System.out.println("DELETE A SEAT");
        System.out.println("*************\n");

        if (passengers.isEmpty()) {
            System.out.println("No seats have been booked yet!");
        } else {
            System.out.print("What is the name that you prompted to book your seat (Prompt Username) : ");
            removedSeatName = sc.next();

            if (!passengers.containsValue(removedSeatName)) {
                System.out.println("\nNo seat has been booked under " + removedSeatName);
            } else {
                System.out.println("\nYou have booked these seats;");
                if (passengers.containsValue(removedSeatName)) {
                    for (HashMap.Entry<Integer, String> entry : passengers.entrySet()) {
                        if (removedSeatName.equals(entry.getValue())) {
                            System.out.print(entry.getKey() + " ");
                        }
                    }

                    System.out.print("\nWhich seat do you want to delete (Prompt Seat Number) : ");
                    while (!sc.hasNextInt()) {
                        System.out.println("Prompt Integers!!");
                        System.out.print("\nWhich seat do you want to delete (Prompt Seat Number) : ");
                        sc.next();
                    }
                    removedSeatNumber = sc.nextInt();

                    if (passengers.containsKey(removedSeatNumber)) {
                        passengers.remove(removedSeatNumber);
                        System.out.println("\nSeat #" + removedSeatNumber + " is successfully deleted!");
                    } else {
                        System.out.println("\nYou did not book any seats under this seat #" + removedSeatNumber);
                    }
                }
            }
        }
        System.out.println("\n--------------------------------------------------");
    }

    private void alphabeticalOrder(HashMap<Integer, String> passengers, List<String> userNameList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*************************************************");
        System.out.println("VIEW SEATS IN ORDERED ALPHABETICALLY BY USER NAME");
        System.out.println("*************************************************\n");

        String temp;

        try {
            if (passengers.isEmpty()) {
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
                    for (Object o : passengers.keySet()) {
                        if (passengers.get(o).equalsIgnoreCase(s)) {
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
