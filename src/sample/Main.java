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

        String[][][][] passengersArray = new String[2][31][SEATING_CAPACITY][4];
        List<String> bookedDatesList = new ArrayList<String>();

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
                    welcomeScreen(passengersArray, 1, bookedDatesList);
                    break;

                case "V":
                case "v":
                    welcomeScreen(passengersArray, 2, bookedDatesList);
                    break;

                case "E":
                case "e":
                    welcomeScreen(passengersArray, 3, bookedDatesList);
                    break;

                case "D":
                case "d":
                    deleteCustomer(passengersArray, bookedDatesList);
                    break;

                case "F":
                case "f":
                    findSeat(passengersArray, bookedDatesList);
                    break;

                case "S":
                case "s":
                    storeData(passengersArray, bookedDatesList);
                    break;

                case "L":
                case "l":
                    loadData(passengersArray);
                    break;

                case "O":
                case "o":
                    welcomeScreen(passengersArray, 8, bookedDatesList);
                    break;

                case "q":
                case "Q":
                    System.out.print("\nIf you want to store data before, you exit from the program\nPrompt \"S\" or prompt any key to Exit : ");
                    String option = sc.next();
                    if (option.equalsIgnoreCase("s")) {
                        welcomeScreen(passengersArray, 6, bookedDatesList);
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

    private void welcomeScreen(String[][][][] passengersArray, int welcomeScreenType, List bookedDatesList) {
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

        DatePicker checkInDatePicker = new DatePicker();

        Label dataLabel = new Label("Select your date");
        dataLabel.setFont(new Font("Arial", 15));
        dataLabel.setPadding(new Insets(30, 0, 0, 0));

        Button emptySpace = new Button();
        emptySpace.setStyle("-fx-background-color: rgba(0,0,0,0)");
        emptySpace.setMinSize(0, 15);

        Button continueBtn = new Button("Continue");

        continueBtn.setOnAction(event -> {
            int station;
            int pickedDate;
            String pickedDateFull;
            String date;

            try {
                pickedDate = Integer.parseInt(String.valueOf(checkInDatePicker.getValue()).substring(8, 10));
                pickedDateFull = String.valueOf(checkInDatePicker.getValue());
                date = String.valueOf(checkInDatePicker.getValue());
                bookedDatesList.add(pickedDateFull);

                if (welcomeScreenType == 1) {
                    if (comboBox.getValue().equals("Badulla to Colombo")) {
                        System.out.println("\nYou selected to book seats => Badulla - Colombo on " + checkInDatePicker.getValue());
                        station = 0;
                    } else {
                        System.out.println("\nYou selected to delete seats => Colombo - Badulla on " + checkInDatePicker.getValue());
                        station = 1;
                    }
                    addCustomer(passengersArray, station, pickedDate, date);

                } else if (welcomeScreenType == 2) {
                    if (comboBox.getValue().equals("Badulla to Colombo")) {
                        System.out.println("\nYou selected to book seats => Badulla - Colombo on " + checkInDatePicker.getValue());
                        station = 0;
                    } else {
                        System.out.println("\nYou selected to delete seats => Colombo - Badulla on " + checkInDatePicker.getValue());
                        station = 1;
                    }
                    allSeatsDisplay(passengersArray, station, pickedDate, date);

                } else if (welcomeScreenType == 3) {
                    if (comboBox.getValue().equals("Badulla to Colombo")) {
                        System.out.println("\nYou selected to book seats => Badulla - Colombo on " + checkInDatePicker.getValue());
                        station = 0;
                    } else {
                        System.out.println("\nYou selected to delete seats => Colombo - Badulla on " + checkInDatePicker.getValue());
                        station = 1;
                    }
                    emptySeatsDisplay(passengersArray, station, pickedDate, date);

                } else if (welcomeScreenType == 8) {
                    if (comboBox.getValue().equals("Badulla to Colombo")) {
                        System.out.println("\nYou selected to book seats => Badulla - Colombo on " + checkInDatePicker.getValue());
                        station = 0;
                    } else {
                        System.out.println("\nYou selected to delete seats => Colombo - Badulla on " + checkInDatePicker.getValue());
                        station = 1;
                    }
                    welcomeWindow.close();
                    alphabeticalOrder(passengersArray, station, pickedDate);
                }
                welcomeWindow.close();

            } catch (Exception notPickedDate) {
                System.out.println("\nPlease select a Date to proceed!");
            }
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
    private void alertBoxWindowTypeTwo(String title, String message, String iconType) {
        Stage alertBoxWindow = new Stage();

        //Block events to other windows
        alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
        alertBoxWindow.setTitle(title);

        if (iconType.equals("1")) {
            Image windowIcon = new Image(getClass().getResourceAsStream("alertIcon.png"));
            alertBoxWindow.getIcons().add(windowIcon);
        } else {
            Image windowIcon = new Image(getClass().getResourceAsStream("confirmIcon.png"));
            alertBoxWindow.getIcons().add(windowIcon);
        }

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
    private void seatDisplay(String[][][][] passengersArray, int station, int pickedDate, Button bookBtn, List<Integer> selectedSeats, List<Integer> seatNumbers, Stage window, VBox leftSeatsRowOne, VBox leftSeatsRowTwo, VBox RightSeatsRowOne, VBox RightSeatsRowTwo, String actionType, String date) {
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
                            bookBtn.setDisable(false);

                        } else {
                            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
                            selectedSeats.remove(Integer.valueOf(seat.getId()));
                            bookBtn.setDisable(true);
                        }
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

            if (actionType.equals("seatAction")) {
                if (seatNumbers.contains(i)) {
                    seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
                } else {
                    seat.setOnAction((e) -> {
                        if (seat.isSelected()) {
                            seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
                            selectedSeats.add(Integer.valueOf(seat.getId()));
                            bookBtn.setDisable(false);

                        } else {
                            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
                            selectedSeats.remove(Integer.valueOf(seat.getId()));
                            bookBtn.setDisable(true);
                        }
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

        for (int i = 22; i <= 31; i++) {
            ToggleButton seat = new ToggleButton("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            RightSeatsRowOne.getChildren().add(seat);
            RightSeatsRowOne.setPadding(new Insets(0, 0, 0, 75));
            RightSeatsRowOne.setSpacing(5);
            seat.setCursor(Cursor.HAND);

            if (actionType.equals("seatAction")) {
                if (seatNumbers.contains(i)) {
                    seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
                } else {
                    seat.setOnAction((e) -> {
                        if (seat.isSelected()) {
                            seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
                            selectedSeats.add(Integer.valueOf(seat.getId()));
                            bookBtn.setDisable(false);

                        } else {
                            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
                            selectedSeats.remove(Integer.valueOf(seat.getId()));
                            bookBtn.setDisable(true);
                        }
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

        for (int i = 32; i <= SEATING_CAPACITY; i++) {
            ToggleButton seat = new ToggleButton("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            RightSeatsRowTwo.getChildren().add(seat);
            RightSeatsRowTwo.setSpacing(5);
            seat.setCursor(Cursor.HAND);

            if (actionType.equals("seatAction")) {
                if (seatNumbers.contains(i)) {
                    seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
                } else {
                    seat.setOnAction((e) -> {
                        if (seat.isSelected()) {
                            seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
                            selectedSeats.add(Integer.valueOf(seat.getId()));
                            bookBtn.setDisable(false);

                        } else {
                            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
                            selectedSeats.remove(Integer.valueOf(seat.getId()));
                            bookBtn.setDisable(true);
                        }
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

                Button confirmUser = new Button("Confirm");
                confirmUser.setDisable(true);

                Button cancelBtn = new Button("Cancel");
                cancelBtn.setOnAction(event1 -> confirmationBox.hide());

                Text userNameTxt = new Text("Enter your name");
                TextField userNameTxtField = new TextField();

                Text userMobileTxt = new Text("Enter your mobile number");
                TextField userMobileTxtField = new TextField();
                userMobileTxtField.setOnMouseExited(event1 -> {
                    while (!userMobileTxtField.getText().equals("")) {
                        if (userMobileTxtField.getText().matches("[1234567890]+") && userMobileTxtField.getText().length() == 10) {
                            break;
                        } else {
                            alertBoxWindowTypeTwo("Invalid!", "Please enter a valid Mobile Number", "1");
                            userMobileTxtField.setText("");
                        }
                    }
                });

                Text userNicTxt = new Text("Enter your NIC");
                TextField userNicTxtField = new TextField();
                userNicTxtField.setOnMouseExited(event1 -> {
                    while (!userNicTxtField.getText().equals("")) {
                        if (userNicTxtField.getText().matches("[1234567890]+v") && userNicTxtField.getText().length() == 10) {
                            break;
                        } else {
                            alertBoxWindowTypeTwo("Invalid!", "Please enter a valid NIC", "1");
                            userNicTxtField.setText("");
                        }
                    }
                });
                //user must enter at least two character as the name to confirm his/her booking
                userNicTxtField.setOnKeyTyped(event1 -> {
                    if (userNicTxtField.getText().isEmpty() && userMobileTxtField.getText().isEmpty() && userNameTxtField.getText().isEmpty()) {
                        confirmUser.setDisable(true);
                    } else {
                        confirmUser.setDisable(false);
                    }
                });

                hBox2.getChildren().addAll(confirmUser, cancelBtn);
                hBox2.setPadding(new Insets(50, 0, 0, 0));
                hBox2.setSpacing(10);

                vBox.getChildren().addAll(headerConfirmationBox, userNameTxt, userNameTxtField, userMobileTxt, userMobileTxtField, userNicTxt, userNicTxtField, hBox2);
                vBox.setSpacing(10);

                flowPane2.getChildren().addAll(vBox);

                System.out.println("\n------------------");
                System.out.println("Confirmed Bookings");
                System.out.println("------------------\n");

                confirmUser.setOnAction(event2 -> {
                    for (int j : selectedSeats) {
                        passengersArray[station][pickedDate - 1][j - 1][0] = userNameTxtField.getText().toLowerCase();
                        passengersArray[station][pickedDate - 1][j - 1][1] = userMobileTxtField.getText();
                        passengersArray[station][pickedDate - 1][j - 1][2] = userNicTxtField.getText();
                        passengersArray[station][pickedDate - 1][j - 1][3] = String.valueOf(j);

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
                        alertBoxWindowTypeTwo("Booked!", "You have successfully booked Seat #" + passengersArray[station][pickedDate - 1][j - 1][3], "2");
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

    private void allSeatsDisplayAction(String[][][][] passengersArray, int station, int pickedDate, ToggleButton seat, int i) {
        List<Integer> seatNumbers = new ArrayList<>();

        for (int j = 0; j < SEATING_CAPACITY; j++) {
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

    private void emptySeatsDisplayAction(String[][][][] passengersArray, int station, int pickedDate, ToggleButton seat, int i) {
        List<Integer> seatNumbers = new ArrayList<>();

        for (int j = 0; j < SEATING_CAPACITY; j++) {
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

    private void addCustomer(String[][][][] passengersArray, int station, int pickedDate, String date) {
        List<Integer> selectedSeats = new ArrayList<>();
        List<Integer> seatNumbers = new ArrayList<>();

        System.out.println("--------------------------------------------------");

        System.out.println("\n************************");
        System.out.println("ADD A CUSTOMER TO A SEAT");
        System.out.println("************************");

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
        bookBtn.setDisable(true);

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

    private void deleteCustomer(String[][][][] passengersArray, List<String> bookedDatesList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*************");
        System.out.println("DELETE A SEAT");
        System.out.println("*************\n");

        int removedSeatNumber;
        String removedSeatName;
        String nic;

        Scanner sc = new Scanner(System.in);
        HashMap<Integer, String> passengerSeatAndNameDesOne = new HashMap<>();
        HashMap<Integer, String> passengerSeatAndNameDesTwo = new HashMap<>();
        List<String> passengerNicDesOne = new ArrayList<>();
        List<String> passengerNicDesTwo = new ArrayList<>();

        for (String o : bookedDatesList) {
            for (int i = 1; i <= SEATING_CAPACITY; i++) {
                if (passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0] != null) {
                    passengerNicDesOne.add(passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][2]);
                    passengerSeatAndNameDesOne.put(i, passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0]);
                }
                if (passengersArray[1][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0] != null) {
                    passengerNicDesTwo.add(passengersArray[1][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][2]);
                    passengerSeatAndNameDesTwo.put(i, passengersArray[1][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0]);
                }
            }
        }

        for (String s : bookedDatesList) {
            if (bookedDatesList.isEmpty()) {
                System.out.println("\nNo seats have been booked yet!");
            } else {
                System.out.print("What is the name that you prompted to book your seat/ seats (Prompt Username) : ");
                removedSeatName = sc.next();
                if (!passengerSeatAndNameDesOne.containsValue(removedSeatName) && passengerSeatAndNameDesTwo.containsValue(removedSeatName)) {
                    System.out.println("\nNo seat has been booked under " + removedSeatName);
                } else {
                    System.out.print("What is the NIC that you prompted to book your seat/ seats : ");
                    nic = sc.next();
                    if (passengerNicDesOne.contains(nic) || passengerNicDesTwo.contains(nic)) {
                        System.out.println("\nYou have booked these seats;\n");
                        if (passengerSeatAndNameDesOne.containsValue(removedSeatName) || passengerSeatAndNameDesTwo.containsValue(removedSeatName)) {
                            if (passengerNicDesOne.contains(nic)) {
                                System.out.println("on Badulla - Colombo" + s);
                                for (HashMap.Entry<Integer, String> entry : passengerSeatAndNameDesOne.entrySet()) {
                                    if (removedSeatName.equals(entry.getValue())) {
                                        System.out.println("#" + entry.getKey());
                                    }
                                }
                            }
                            if (passengerNicDesTwo.contains(nic)) {
                                System.out.println("\non Colombo - Badulla" + s);
                                for (HashMap.Entry<Integer, String> entry : passengerSeatAndNameDesTwo.entrySet()) {
                                    if (removedSeatName.equals(entry.getValue())) {
                                        System.out.println("#" + entry.getKey());
                                    }
                                }
                            }
                            System.out.println();
                        } else {
                            System.out.println("\nNo seat has been booked under " + removedSeatName);
                            break;
                        }
                    } else {
                        System.out.println("\nNo seat has been booked under " + nic);
                        break;
                    }

                    if (!passengerSeatAndNameDesOne.isEmpty()) {
                        System.out.print("\nSelect a Destination to delete seats\n01 Badulla - Colombo\n02 Colombo - Badulla\n\nPrompt 1 or 2 to proceed : ");
                        while (!sc.hasNextInt()) {
                            System.out.println("Prompt Integers to proceed!!");
                            System.out.print("Select a Destination to delete seats\n01 Badulla - Colombo\n02 Colombo - Badulla\n\nPrompt 1 or 2 to proceed : ");
                            sc.next();
                        }
                        int userOption = sc.nextInt();

                        if (userOption == 1) {
                            System.out.print("\nWhich seat do you want to delete (Prompt a Seat Number) : ");
                            while (!sc.hasNextInt()) {
                                System.out.println("Prompt Integers to proceed!!");
                                System.out.print("\nWhich seat do you want to delete (Prompt a Seat Number) : ");
                                sc.next();
                            }
                            removedSeatNumber = sc.nextInt();

                            if (passengerSeatAndNameDesOne.containsKey(removedSeatNumber)) {
                                passengersArray[0][Integer.parseInt(s.substring(8, 10)) - 1][removedSeatNumber - 1][0] = null;
                                passengersArray[0][Integer.parseInt(s.substring(8, 10)) - 1][removedSeatNumber - 1][1] = null;
                                passengersArray[0][Integer.parseInt(s.substring(8, 10)) - 1][removedSeatNumber - 1][2] = null;
                                passengersArray[0][Integer.parseInt(s.substring(8, 10)) - 1][removedSeatNumber - 1][3] = String.valueOf(0);
                                System.out.println("\nSeat #" + removedSeatNumber + " is successfully deleted!");
                                System.out.println("\n--------------------------------------------------");
                                break;
                            } else {
                                System.out.println("\nYou did not book seat #" + removedSeatNumber + " => " + "Badulla - Colombo");
                            }
                        } else {
                            if (!passengerSeatAndNameDesTwo.isEmpty()) {
                                System.out.print("\nWhich seat do you want to delete (Prompt a Seat Number) : ");
                                while (!sc.hasNextInt()) {
                                    System.out.println("Prompt Integers to proceed!!");
                                    System.out.print("\nWhich seat do you want to delete (Prompt a Seat Number) : ");
                                    sc.next();
                                }
                                removedSeatNumber = sc.nextInt();

                                if (passengerSeatAndNameDesOne.containsKey(removedSeatNumber)) {
                                    passengersArray[1][Integer.parseInt(s.substring(8, 10)) - 1][removedSeatNumber - 1][0] = null;
                                    passengersArray[1][Integer.parseInt(s.substring(8, 10)) - 1][removedSeatNumber - 1][1] = null;
                                    passengersArray[1][Integer.parseInt(s.substring(8, 10)) - 1][removedSeatNumber - 1][2] = null;
                                    passengersArray[1][Integer.parseInt(s.substring(8, 10)) - 1][removedSeatNumber - 1][3] = String.valueOf(0);
                                    System.out.println("\nSeat #" + removedSeatNumber + " is successfully deleted!");
                                    System.out.println("\n--------------------------------------------------");
                                    break;
                                } else {
                                    System.out.println("\nYou did not book seat #" + removedSeatNumber + " => " + "Colombo - Badulla");
                                }
                                break;
                            }
                        }
                    }
                }
            }
            System.out.println("\n--------------------------------------------------");
        }
    }

    private void findSeat(String[][][][] passengersArray, List<String> bookedDatesList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**************");
        System.out.println("FIND USER SEAT");
        System.out.println("**************\n");

        List<String> passengerNic = new ArrayList<>();
        List<String> passengerNames = new ArrayList<>();

        for (String o : bookedDatesList) {
            for (int i = 1; i <= SEATING_CAPACITY; i++) {
                if (passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0] != null) {
                    passengerNic.add(passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][2]);
                    passengerNames.add(passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0]);
                }
                if (passengersArray[1][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0] != null) {
                    passengerNic.add(passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][2]);
                    passengerNames.add(passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0]);
                }
            }

            Scanner sc = new Scanner(System.in);

            System.out.print("What is the name that you prompted to book your seat/ seats (Prompt Username) : ");
            String findUserName = sc.next();

            System.out.print("What is the NIC that you prompted to book your seat/ seats : ");
            String nic = sc.next();

            if (bookedDatesList.isEmpty()) {
                System.out.println("\nNo seats have been booked yet!");
            } else {
                if (passengerNic.contains(nic) && passengerNames.contains(findUserName)) {
                    for (String s : bookedDatesList) {
                        if (passengerNic.contains(nic)) {
                            System.out.println("--------------------------------------");
                            System.out.println("Bookings which were made on " + s);
                            System.out.println("--------------------------------------");
                        }
                        for (int i = 1; i <= SEATING_CAPACITY; i++) {
                            if (passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0] != null) {
                                if (passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0].equalsIgnoreCase(findUserName) && passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][2].equalsIgnoreCase(nic)) {
                                    System.out.println("Destination     - Badulla - Colombo");
                                    System.out.println("Booked Date     - " + o);
                                    System.out.println("Passenger name  - " + findUserName);
                                    System.out.println("Mobile Number   - " + passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][1]);
                                    System.out.println("NIC             - " + passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][2]);
                                    System.out.println("Seat            - #" + passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][3]);
                                    System.out.println();
                                }
                            }
                            if (passengersArray[1][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0] != null) {
                                if (passengersArray[1][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0].equalsIgnoreCase(findUserName) && passengersArray[1][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][2].equalsIgnoreCase(nic)) {
                                    System.out.println("Destination     - Colombo - Badulla");
                                    System.out.println("Booked Date     - " + o);
                                    System.out.println("Passenger name  - " + findUserName);
                                    System.out.println("Mobile Number   - " + passengersArray[1][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][1]);
                                    System.out.println("NIC             - " + passengersArray[1][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][2]);
                                    System.out.println("Seat            - #" + passengersArray[1][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][3]);
                                    System.out.println();
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("No seat has been booked under " + findUserName + " or " + nic);
                    System.out.println("\n--------------------------------------------------");
                    break;
                }
            }
        }
    }

    private void alphabeticalOrder(String[][][][] passengersArray, int station, int pickedDate) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n*************************************************");
        System.out.println("VIEW SEATS IN ORDERED ALPHABETICALLY BY USER NAME");
        System.out.println("*************************************************");

        HashMap<Integer, String> passengerNameAndSeat = new HashMap<>();
        List<String> passengersNameList = new ArrayList<>();

        for (int i = 0; i < 42; i++) {
            if (passengersArray[station][pickedDate - 1][i][3] != null) {
                passengerNameAndSeat.put((i + 1), passengersArray[station][pickedDate - 1][i][0]);
            }
        }

        for (int i = 0; i < 42; i++) {
            if (passengersArray[station][pickedDate - 1][i][3] != null) {
                if (!(passengersNameList.contains(passengersArray[station][pickedDate - 1][i][0]))) {
                    passengersNameList.add(passengersArray[station][pickedDate - 1][i][0]);
                }
            }
        }

        String temp;
        try {
            if (passengerNameAndSeat.isEmpty()) {
                System.out.println("\nNo seats have been booked yet!");
            } else {
                System.out.println();
                for (int i = 0; i < passengersNameList.size(); i++) {
                    for (int j = i + 1; j < passengersNameList.size(); j++) {
                        if (passengersNameList.get(i).compareTo(passengersNameList.get(j)) > 0) {
                            temp = passengersNameList.get(i);
                            passengersNameList.set(i, passengersNameList.get(j));
                            passengersNameList.set(j, temp);
                        }
                    }
                }
                for (String s : passengersNameList) {
                    for (Object o : passengerNameAndSeat.keySet()) {
                        if (passengerNameAndSeat.get(o).equals(s)) {
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

    private void storeData(String[][][][] passengersArray, List<String> bookedDatesList) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**********");
        System.out.println("STORE DATA");
        System.out.println("**********\n");

        HashMap<Integer, String> passengerSeatAndNameDesOneStoreData = new HashMap<>();
        HashMap<Integer, String> passengerSeatAndNameDesTwoStoreData = new HashMap<>();

        for (String o : bookedDatesList) {
            for (int i = 1; i <= SEATING_CAPACITY; i++) {
                if (passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0] != null) {
                    passengerSeatAndNameDesOneStoreData.put(i, passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0]);
                }
                if (passengersArray[1][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0] != null) {
                    passengerSeatAndNameDesTwoStoreData.put(i, passengersArray[0][Integer.parseInt(o.substring(8, 10)) - 1][i - 1][0]);
                }
            }
        }

        //new file object
        BufferedWriter bufferedWriter = null;

        if (passengerSeatAndNameDesOneStoreData.isEmpty() && passengerSeatAndNameDesTwoStoreData.isEmpty()) {
            System.out.println("No seats have been booked yet!");
        } else {
            for (String s : bookedDatesList) {
                try {
                    if (!passengerSeatAndNameDesOneStoreData.isEmpty()) {
                        File file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\BadullaToColombo.txt");
                        //create new BufferedWriter for the output file
                        bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                        //iterate map entries
                        for (int i = 0; i < SEATING_CAPACITY; i++) {
                            if (passengersArray[0][Integer.parseInt(s.substring(8, 10)) - 1][i][3] != null) {
                                bufferedWriter.write("Destination - Badulla - Colombo | Booked date - " + s +
                                        " | Passenger name - " + passengersArray[0][Integer.parseInt(s.substring(8, 10)) - 1][i][0] +
                                        " | Mobile no - " + passengersArray[0][Integer.parseInt(s.substring(8, 10)) - 1][i][1] +
                                        " | NIC - " + passengersArray[0][Integer.parseInt(s.substring(8, 10)) - 1][i][2] +
                                        " | Seat #" + passengersArray[0][Integer.parseInt(s.substring(8, 10)) - 1][i][3]);
                                bufferedWriter.newLine();
                            }
                        }
                        bufferedWriter.flush();
                    }
                    if (!passengerSeatAndNameDesTwoStoreData.isEmpty()) {
                        File file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\ColomboToBadulla.txt");
                        //create new BufferedWriter for the output file
                        bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                        //iterate map entries
                        for (int i = 0; i < SEATING_CAPACITY; i++) {
                            if (passengersArray[1][Integer.parseInt(s.substring(8, 10)) - 1][i][3] != null) {
                                bufferedWriter.write("Destination - Colombo - Badulla | Booked date - " + s +
                                        " | Passenger name - " + passengersArray[1][Integer.parseInt(s.substring(8, 10)) - 1][i][0] +
                                        " | Mobile no - " + passengersArray[1][Integer.parseInt(s.substring(8, 10)) - 1][i][1] +
                                        " | NIC - " + passengersArray[1][Integer.parseInt(s.substring(8, 10)) - 1][i][2] +
                                        " | Seat #" + passengersArray[1][Integer.parseInt(s.substring(8, 10)) - 1][i][3]);
                                bufferedWriter.newLine();
                            }
                        }
                        bufferedWriter.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        //always close the writer
                        assert bufferedWriter != null;
                        bufferedWriter.close();
                    } catch (Exception e) {
                        //error ignored
                    }
                }
            }
        }
        System.out.println("Data has been successfully stored!\n");
        System.out.println("--------------------------------------------------");
    }

    private void loadData(String[][][][] passengersArray) {
        System.out.println("--------------------------------------------------");

        System.out.println("\n**********************");
        System.out.println("LOAD PROGRAM FROM DATA");
        System.out.println("**********************\n");

        try {
            File file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\BadullaToColombo.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            //create BufferedReader object from the File
            String line;

            //read file line by line
            while ((line = bufferedReader.readLine()) != null) {
                //split the line by | and put it to a string array
                String[] parts = line.split(" \\| ");

                String[] bookedDate = parts[1].split("Booked date - ");
                String[] passengerName = parts[2].split("Passenger name - ");
                String[] mobileNumber = parts[3].split("Mobile no - ");
                String[] nic = parts[4].split("NIC - ");
                String[] seatNumber = parts[5].split("Seat #");

                passengersArray[0][(Integer.parseInt(bookedDate[1].substring(8, 10))) - 1][Integer.parseInt(seatNumber[1]) - 1][0] = passengerName[1];
                passengersArray[0][(Integer.parseInt(bookedDate[1].substring(8, 10))) - 1][Integer.parseInt(seatNumber[1]) - 1][1] = mobileNumber[1];
                passengersArray[0][(Integer.parseInt(bookedDate[1].substring(8, 10))) - 1][Integer.parseInt(seatNumber[1]) - 1][2] = nic[1];
                passengersArray[0][(Integer.parseInt(bookedDate[1].substring(8, 10))) - 1][Integer.parseInt(seatNumber[1]) - 1][3] = seatNumber[1];

            }
            try {
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file = new File("C:\\Users\\Nimendra Kariyawasam\\Desktop\\CW\\PP2 CW1\\Train Seats Booking Program (summertive)\\src\\sample\\storeData\\ColomboToBadulla.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            //create BufferedReader object from the File
            String line;

            //read file line by line
            while ((line = bufferedReader.readLine()) != null) {
                //split the line by | and put it to a string array
                String[] parts = line.split(" \\| ");

                String[] bookedDate = parts[1].split("Booked date - ");
                String[] passengerName = parts[2].split("Passenger name - ");
                String[] mobileNumber = parts[3].split("Mobile no - ");
                String[] nic = parts[4].split("NIC - ");
                String[] seatNumber = parts[5].split("Seat #");

                passengersArray[1][(Integer.parseInt(bookedDate[1].substring(8, 10))) - 1][Integer.parseInt(seatNumber[1]) - 1][0] = passengerName[1];
                passengersArray[1][(Integer.parseInt(bookedDate[1].substring(8, 10))) - 1][Integer.parseInt(seatNumber[1]) - 1][1] = mobileNumber[1];
                passengersArray[1][(Integer.parseInt(bookedDate[1].substring(8, 10))) - 1][Integer.parseInt(seatNumber[1]) - 1][2] = nic[1];
                passengersArray[1][(Integer.parseInt(bookedDate[1].substring(8, 10))) - 1][Integer.parseInt(seatNumber[1]) - 1][3] = seatNumber[1];

            }
            try {
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Stored data has been successfully loaded to the program!\nTip - Prompt \"V\" to check available seats\n");
        System.out.println("--------------------------------------------------");
    }
}



