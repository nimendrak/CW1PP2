package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
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

        String[][][][] passengersArray = new String[2][30][SEATING_CAPACITY][4];

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
            System.out.println("Prompt \"Q\" to exit from the program");

            System.out.print("\nPrompt your option : ");
            userOption = sc.next();

            switch (userOption) {
                case "A":
                case "a":
                    welcomeScreen(passengersArray, 1);
                    break;

                case "V":
                case "v":
                    welcomeScreen(passengersArray, 2);
                    break;

                case "E":
                case "e":
                    welcomeScreen(passengersArray, 3);
                    break;

                case "D":
                case "d":
                    welcomeScreen(passengersArray, 4);
                    break;

                case "F":
                case "f":
                    //findSeat(badullaToColombo, colomboToBadulla, sc, toColomboPassengersNameList, toBadullaPassengersNameList);
                    break;

                case "S":
                case "s":
                    //storeData(badullaToColombo, colomboToBadulla, sc);
                    break;

                case "L":
                case "l":
                    //loadProgramFile(badullaToColombo, colomboToBadulla, sc);
                    break;

                case "O":
                case "o":
                    //alphabeticalOrder(badullaToColombo, colomboToBadulla, sc);
                    break;

                case "q":
                case "Q":
                    System.out.print("\nIf you want to store data before, you exit from the program\nPrompt \"S\" or prompt any key to Exit : ");
                    String option = sc.next();
                    if (option.equalsIgnoreCase("s")) {
                        //storeData(badullaToColombo, colomboToBadulla, sc);
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

    private void welcomeScreen(String[][][][] passengersArray, int welcomeScreenType) {
        Stage welcomeWindow = new Stage();
        welcomeWindow.setTitle("Welcome!");

        Image windowIcon = new Image(getClass().getResourceAsStream("mainIcon.png"));
        welcomeWindow.getIcons().add(windowIcon);

        Pane pane = new Pane();
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(5);

        Label labelMain = new Label("Denuwara Manike Train Seats Booking Program");
        labelMain.setFont(new Font("Arial Bold", 18));

        Label labePrimary = new Label("v3.0");
        labePrimary.setFont(new Font("Arial", 12));

        Label destinationsLabel = new Label("Select your Destination");
        destinationsLabel.setFont(new Font("Arial", 15));
        destinationsLabel.setPadding(new Insets(30, 0, 0, 0));

        String[] destinations = {"Badulla to Colombo", "Colombo to Badulla"};
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(destinations));
        comboBox.getSelectionModel().select(0);

        Label dataLabel = new Label("Select your date");
        dataLabel.setFont(new Font("Arial", 15));
        dataLabel.setPadding(new Insets(30, 0, 0, 0));

        DatePicker checkInDatePicker = new DatePicker();

        Button emptySpace = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(0, 15);

        Button continueBtn = new Button("Continue");

        continueBtn.setOnAction(event -> {
            int station;
            int pickedDate = Integer.parseInt(String.valueOf(checkInDatePicker.getValue()).substring(8, 10));
            String date = String.valueOf(checkInDatePicker.getValue());

            if (welcomeScreenType == 1) {
                if (comboBox.getValue().equals("Badulla to Colombo")) {
                    System.out.println("\nYou selected to book seats on Badulla - Colombo => " + checkInDatePicker.getValue());
                    station = 0;
                    addCustomer(passengersArray, station, pickedDate, date);
                } else {
                    System.out.println("\nYou selected to book seats on Colombo - Badulla => " + checkInDatePicker.getValue());
                    station = 1;
                    addCustomer(passengersArray, station, pickedDate, date);
                }
            } else if (welcomeScreenType == 2) {
                if (comboBox.getValue().equals("Badulla to Colombo")) {
                    System.out.println("\nYou selected to view seats on Badulla - Colombo => " + checkInDatePicker.getValue());
                    station = 0;
                    allSeatsDisplay(passengersArray, station, pickedDate, date);
                } else {
                    System.out.println("\nYou selected to view seats on Colombo - Badulla => " + checkInDatePicker.getValue());
                    station = 1;
                    allSeatsDisplay(passengersArray, station, pickedDate, date);
                }
            } else if (welcomeScreenType == 3) {
                if (comboBox.getValue().equals("Badulla to Colombo")) {
                    System.out.println("\nYou selected to view seats on Badulla - Colombo => " + checkInDatePicker.getValue());
                    station = 0;
                    emptySeatsDisplay(passengersArray, station, pickedDate, date);
                } else {
                    System.out.println("\nYou selected to view seats on Colombo - Badulla => " + checkInDatePicker.getValue());
                    station = 1;
                    emptySeatsDisplay(passengersArray, station, pickedDate, date);
                }
            } else if (welcomeScreenType == 4) {
                if (comboBox.getValue().equals("Badulla to Colombo")) {
                    System.out.println("\nYou selected to delete seats on Badulla - Colombo => " + checkInDatePicker.getValue());
                    station = 0;
                    welcomeWindow.close();
                    deleteCustomer(passengersArray, station, pickedDate);
                } else {
                    System.out.println("\nYou selected to delete seats on Colombo - Badulla => " + checkInDatePicker.getValue());
                    station = 1;
                    welcomeWindow.close();
                    deleteCustomer(passengersArray, station, pickedDate);
                }
            }
            //welcomeWindow.close();
        });

        welcomeWindow.initModality(Modality.APPLICATION_MODAL);

        Scene mainScene = new Scene(pane, 550, 525);

        //background image
        try {
            // create a input stream
            FileInputStream input = new FileInputStream("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\mainBg.jpg");

            // create a image
            Image image = new Image(input);

            // create a background image
            BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

            // create Background
            Background background = new Background(backgroundimage);

            // set background
            pane.setBackground(background);

        } catch (Exception e) {
            //ignored
        }

        vBox.getChildren().addAll(labelMain, labePrimary, destinationsLabel, comboBox, dataLabel, checkInDatePicker, emptySpace, continueBtn);
        pane.getChildren().add(vBox);

        welcomeWindow.setScene(mainScene);
        welcomeWindow.showAndWait();
    }

    //type one alert box act as a confirmation box for the quit the current stage
    private void alertBoxWindowTypeOne(Stage window) {
        Stage alertBoxWindow = new Stage();

        //Block events to other windows
        alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
        alertBoxWindow.setTitle("Alert!");

        Image windowIcon = new Image(getClass().getResourceAsStream("alertIcon.png"));
        alertBoxWindow.getIcons().add(windowIcon);

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
        alertBoxWindow.setTitle("Booked!");

        Image windowIcon = new Image(getClass().getResourceAsStream("confirmIcon.png"));
        alertBoxWindow.getIcons().add(windowIcon);

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

    /* i've used 4 different for loops to print seats as vertical rows, so each row should contain a action
    that each seat can perform in a specific condition whether it could be book or display */
    private void seatDisplay(String[][][][] passengersArray, int station, int pickedDate, Button bookBtn, List<Integer> selectedSeats, List<Integer> seatNumbers, Stage window, VBox
            leftSeatsRowOne, VBox leftSeatsRowTwo, VBox RightSeatsRowOne, VBox RightSeatsRowTwo, String actionType, String date) {

        for (int i = 1; i <= 11; i++) {
            ToggleButton seat = new ToggleButton("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            leftSeatsRowOne.getChildren().add(seat);
            leftSeatsRowOne.setSpacing(5);
            seat.setCursor(Cursor.HAND);

            if (actionType.equals("seatAction")) {
                if (seatNumbers.contains(i)) {
                    seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
                } else {
                    seat.setOnAction((e) -> {
                        if (seat.isSelected()) {
                            seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
                            selectedSeats.add(Integer.valueOf(seat.getId()));
                        } else {
                            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
                            selectedSeats.remove(Integer.valueOf(seat.getId()));
                        }
                        System.out.println(selectedSeats);
                    });
                }
                seatBookingAction(passengersArray, station, pickedDate, bookBtn, selectedSeats, window, date);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplayAction(passengersArray, station, pickedDate, seat, i);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplayAction(passengersArray, station, pickedDate, seat, i);
            }
        }

        for (int i = 12; i <= 21; i++) {
            ToggleButton seat = new ToggleButton("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            leftSeatsRowTwo.getChildren().add(seat);
            leftSeatsRowTwo.setSpacing(5);
            seat.setCursor(Cursor.HAND);

            if (seatNumbers.contains(i)) {
                seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
            } else {
                seat.setOnAction((e) -> {
                    if (seat.isSelected()) {
                        seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
                        selectedSeats.add(Integer.valueOf(seat.getId()));
                    } else {
                        seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
                        selectedSeats.remove(Integer.valueOf(seat.getId()));
                    }
                    System.out.println(selectedSeats);
                });
            }

            if (actionType.equals("seatAction")) {
                seatBookingAction(passengersArray, station, pickedDate, bookBtn, selectedSeats, window, date);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplayAction(passengersArray, station, pickedDate, seat, i);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplayAction(passengersArray, station, pickedDate, seat, i);
            }
        }

        for (int i = 22; i <= 31; i++) {
            ToggleButton seat = new ToggleButton("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            RightSeatsRowOne.getChildren().add(seat);
            RightSeatsRowOne.setPadding(new Insets(0, 0, 0, 75));
            RightSeatsRowOne.setSpacing(5);
            seat.setCursor(Cursor.HAND);

            if (seatNumbers.contains(i)) {
                seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
            } else {
                seat.setOnAction((e) -> {
                    if (seat.isSelected()) {
                        seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
                        selectedSeats.add(Integer.valueOf(seat.getId()));
                    } else {
                        seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
                        selectedSeats.remove(Integer.valueOf(seat.getId()));
                    }
                    System.out.println(selectedSeats);
                });
            }

            if (actionType.equals("seatAction")) {
                seatBookingAction(passengersArray, station, pickedDate, bookBtn, selectedSeats, window, date);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplayAction(passengersArray, station, pickedDate, seat, i);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplayAction(passengersArray, station, pickedDate, seat, i);
            }
        }

        for (int i = 32; i <= SEATING_CAPACITY; i++) {
            ToggleButton seat = new ToggleButton("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            RightSeatsRowTwo.getChildren().add(seat);
            RightSeatsRowTwo.setSpacing(5);
            seat.setCursor(Cursor.HAND);

            if (seatNumbers.contains(i)) {
                seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
            } else {
                seat.setOnAction((e) -> {
                    if (seat.isSelected()) {
                        seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
                        selectedSeats.add(Integer.valueOf(seat.getId()));
                    } else {
                        seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
                        selectedSeats.remove(Integer.valueOf(seat.getId()));
                    }
                    System.out.println(selectedSeats);
                });
            }

            if (actionType.equals("seatAction")) {
                seatBookingAction(passengersArray, station, pickedDate, bookBtn, selectedSeats, window, date);
            }
            if (actionType.equals("emptySeats")) {
                emptySeatsDisplayAction(passengersArray, station, pickedDate, seat, i);
            }
            if (actionType.equals("allSeats")) {
                allSeatsDisplayAction(passengersArray, station, pickedDate, seat, i);
            }
        }
    }

    private void seatBookingAction(String[][][][] passengersArray, int station, int pickedDate, Button bookBtn, List<Integer> selectedSeats, Stage window, String date) {
        bookBtn.setOnAction(event -> {
            try {
                Stage confirmationBox = new Stage();
                confirmationBox.initModality(Modality.APPLICATION_MODAL);

                FlowPane flowPane2 = new FlowPane();
                flowPane2.setPadding(new Insets(30));

                Image windowIcon2 = new Image(getClass().getResourceAsStream("pendingIcon.png"));
                confirmationBox.getIcons().add(windowIcon2);

                confirmationBox.setTitle("Confirmation");

                VBox vBox = new VBox();
                HBox hBox2 = new HBox();

                Label headerConfirmationBox = new Label("Prompt Passenger's Details");
                headerConfirmationBox.setFont(new Font("Arial Bold", 22));
                headerConfirmationBox.setTextFill(Paint.valueOf("#414141"));
                headerConfirmationBox.setPadding(new Insets(0, 0, 30, 0));

                Text userNameTxt = new Text("Enter your name");
                TextField userNameTxtField = new TextField();

                Text userMobileTxt = new Text("Enter your mobile number");
                TextField userMobileTxtField = new TextField();

                Text userNicTxt = new Text("Enter your NIC");
                TextField userNicTxtTxtField = new TextField();

                Button confirmUser = new Button("Confirm");
                confirmUser.setDisable(true);

                Button cancelBtn = new Button("Cancel");
                cancelBtn.setOnAction(event1 -> confirmationBox.hide());

                hBox2.getChildren().addAll(confirmUser, cancelBtn);
                hBox2.setPadding(new Insets(50, 0, 0, 0));
                hBox2.setSpacing(10);

                vBox.getChildren().addAll(headerConfirmationBox, userNameTxt, userNameTxtField, userMobileTxt, userMobileTxtField, userNicTxt, userNicTxtTxtField, hBox2);
                vBox.setSpacing(10);

                flowPane2.getChildren().addAll(vBox);

                //user must enter at least two character as the name to confirm his/her booking
                userNicTxtTxtField.setOnKeyTyped(event1 -> {
                    if (userNicTxtTxtField.getText().isEmpty()) {
                        confirmUser.setDisable(true);
                    } else {
                        confirmUser.setDisable(false);
                    }
                });
                System.out.println("\n------------------");
                System.out.println("Confirmed Bookings");
                System.out.println("------------------\n");
                confirmUser.setOnAction(event2 -> {
                    for (int j : selectedSeats) {
                        passengersArray[station][pickedDate - 1][j - 1][0] = userNameTxtField.getText().toLowerCase();
                        passengersArray[station][pickedDate - 1][j - 1][1] = userMobileTxtField.getText();
                        passengersArray[station][pickedDate - 1][j - 1][2] = userNicTxtTxtField.getText();
                        passengersArray[station][pickedDate - 1][j - 1][3] = String.valueOf(j);

                        //System.out.println(checkInDatePicker.getValue());

                        if (station == 0) {
                            System.out.println("Destination     - Badulla to Colombo");
                        } else {
                            System.out.println("Destination     - Colombo to Badulla");
                        }

                        System.out.println("Booked Date     - " + date);
                        System.out.println("Passenger name  - " + passengersArray[station][pickedDate - 1][j - 1][0]);
                        System.out.println("Mobile Number   - " + passengersArray[station][pickedDate - 1][j - 1][1]);
                        System.out.println("NIC             - " + passengersArray[station][pickedDate - 1][j - 1][2]);
                        System.out.println("Seat            - #" + passengersArray[station][pickedDate - 1][j - 1][3]);
                        System.out.println();

                        //popup alertBox
                        alertBoxWindowTypeTwo("You have successfully booked Seat #" + passengersArray[station][pickedDate - 1][j - 1][3]);
                    }
                    confirmationBox.close();
                    window.close();
                });

                Scene confirmationBoxScene = new Scene(flowPane2, 350, 420);
                confirmationBox.setScene(confirmationBoxScene);
                confirmationBox.showAndWait();

            } catch (Exception ignored) {
                //ignoring the runtime error which occurs by JavaFX which I dont know exactly
            }
        });
    }

    private void addCustomer(String[][][][] passengersArray, int station, int pickedDate, String date) {
        List<Integer> selectedSeats = new ArrayList<>();
        List<Integer> seatNumbers = new ArrayList<>();

        System.out.println("--------------------------------------------------");

        System.out.println("\n************************");
        System.out.println("ADD A CUSTOMER TO A SEAT");
        System.out.println("************************\n");

        Stage window = new Stage();

        if (station == 0) {
            window.setTitle("Destination - Badulla to Colombo");
        } else {
            window.setTitle("Destination - Colombo to Badulla");
        }

        window.initModality(Modality.APPLICATION_MODAL);

        Image windowIcon = new Image(getClass().getResourceAsStream("seatIcon.png"));
        window.getIcons().add(windowIcon);

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
        header.setPadding(new Insets(0, 200, 25, 120));

        flowPane.getChildren().add(header);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(41, 0, 0, 135));
        hBox.setSpacing(10);

        VBox leftSeatsRowOne = new VBox();
        VBox leftSeatsRowTwo = new VBox();
        VBox RightSeatsRowOne = new VBox();
        VBox RightSeatsRowTwo = new VBox();

        Button bookBtn = new Button("Book");

        for (int i = 0; i < 42; i++) {
            if (passengersArray[station][pickedDate - 1][i][3] != null) {
                seatNumbers.add(Integer.valueOf(passengersArray[station][pickedDate - 1][i][3]));
            }
        }

        seatDisplay(passengersArray, station, pickedDate, bookBtn, selectedSeats, seatNumbers, window, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "seatAction", date);

        flowPane.getChildren().addAll(leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo);

        //close button
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> {
            alertBoxWindowTypeOne(window);
        });

        hBox.getChildren().addAll(bookBtn, closeBtn);
        flowPane.getChildren().addAll(hBox);

        window.setScene(scene);
        window.showAndWait();

        System.out.println("--------------------------------------------------");
    }

    private void allSeatsDisplayAction(String[][][][] passengersArray, int station, int pickedDate, ToggleButton seat, int i) {
        List<Integer> seatNumbers = new ArrayList<>();

        for (int j = 0; j < 42; j++) {
            if (passengersArray[station][pickedDate - 1][j][3] != null) {
                seatNumbers.add(Integer.valueOf(passengersArray[station][pickedDate - 1][j][3]));
            }
        }

        if (seatNumbers.contains(i)) {
            seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
        } else {
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        }
    }

    private void allSeatsDisplay(String[][][][] passengersArray, int station, int pickedDate, String date) {
        List<Integer> selectedSeats = new ArrayList<>();
        List<Integer> seatNumbers = new ArrayList<>();

        System.out.println("--------------------------------------------------");

        System.out.println("\n*****************");
        System.out.println("DISPLAY ALL SEATS");
        System.out.println("*****************\n");

        Stage window = new Stage();
        if (station == 0) {
            window.setTitle("Destination - Badulla to Colombo");
        } else {
            window.setTitle("Destination - Colombo to Badulla");
        }

        Image windowIcon = new Image(getClass().getResourceAsStream("seatIcon.png"));
        window.getIcons().add(windowIcon);

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

        Button bookBtn = new Button();

        seatDisplay(passengersArray, station, pickedDate, bookBtn, selectedSeats, seatNumbers, window, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "allSeats", date);

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

    private void emptySeatsDisplayAction(String[][][][] passengersArray, int station, int pickedDate, ToggleButton seat, int i) {
        List<Integer> seatNumbers = new ArrayList<>();

        for (int j = 0; j < 42; j++) {
            if (passengersArray[station][pickedDate - 1][j][3] != null) {
                seatNumbers.add(Integer.valueOf(passengersArray[station][pickedDate - 1][j][3]));
            }
        }

        if (seatNumbers.contains(i)) {
            seat.setStyle(null);
            seat.setDisable(true);
        } else {
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
        }
    }

    private void emptySeatsDisplay(String[][][][] passengersArray, int station, int pickedDate, String date) {
        List<Integer> selectedSeats = new ArrayList<>();
        List<Integer> seatNumbers = new ArrayList<>();

        System.out.println("--------------------------------------------------");

        System.out.println("\n*******************");
        System.out.println("DISPLAY EMPTY SEATS");
        System.out.println("*******************\n");

        Stage window = new Stage();
        if (station == 0) {
            window.setTitle("Destination - Badulla to Colombo");
        } else {
            window.setTitle("Destination - Colombo to Badulla");
        }

        Image windowIcon = new Image(getClass().getResourceAsStream("seatIcon.png"));
        window.getIcons().add(windowIcon);

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

        Button bookBtn = new Button();
        DatePicker checkInDatePicker = new DatePicker();

        seatDisplay(passengersArray, station, pickedDate, bookBtn, selectedSeats, seatNumbers, window, leftSeatsRowOne, leftSeatsRowTwo, RightSeatsRowOne, RightSeatsRowTwo, "emptySeats", date);

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

    public void deleteCustomer(String[][][][] passengersArray, int station, int pickedDate) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*************");
        System.out.println("DELETE A SEAT");
        System.out.println("*************\n");

        int removedSeatNumber;
        String removedSeatName;

        Scanner sc = new Scanner(System.in);
        List<Integer> seatNumbers = new ArrayList<>();
        List<String> passengerName = new ArrayList<>();

        for (int i = 0; i < 42; i++) {
            if (passengersArray[station][pickedDate - 1][i][3] != null) {
                seatNumbers.add(Integer.valueOf(passengersArray[station][pickedDate - 1][i][3]));
            }
        }

        for (int i = 0; i < 42; i++) {
            if (passengersArray[station][pickedDate - 1][i][3] != null) {
                passengerName.add(passengersArray[station][pickedDate - 1][i][0]);
            }
        }

        if (seatNumbers.isEmpty()) {
            System.out.println("\nNo seats have been booked yet!");
        } else {
            System.out.print("What is the name that you prompted to book your seat/ seats (Prompt Username) : ");
            removedSeatName = sc.next();

            if (!passengerName.contains(removedSeatName)) {
                System.out.println("\nNo seat has been booked under " + removedSeatName);
            } else {
                System.out.println("\nYou have booked these seats;");
                if (passengerName.contains(removedSeatName)) {
                    for (int seatNum : seatNumbers) {
                        System.out.print(seatNum + " ");
                    }
                }
            }

            System.out.print("\nWhich seat do you want to delete (Prompt a Seat Number) : ");
            while (!sc.hasNextInt()) {
                System.out.println("Prompt Integers!!");
                System.out.print("\nWhich seat do you want to delete (Prompt a Seat Number) : ");
                sc.next();
            }
            removedSeatNumber = sc.nextInt();

            if (seatNumbers.contains(removedSeatNumber)) {
                passengersArray[station][pickedDate - 1][removedSeatNumber - 1][0] = null;
                passengersArray[station][pickedDate - 1][removedSeatNumber - 1][1] = null;
                passengersArray[station][pickedDate - 1][removedSeatNumber - 1][2] = null;
                passengersArray[station][pickedDate - 1][removedSeatNumber - 1][3] = String.valueOf(0);
                System.out.println("\nSeat #" + removedSeatNumber + " is successfully deleted!");
            } else {
                System.out.println("\nYou did not book any seats under this seat #" + removedSeatNumber);
            }
        }
        System.out.println("\n--------------------------------------------------");
    }
}


