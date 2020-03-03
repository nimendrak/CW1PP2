package sample;

import javafx.application.Application;
import javafx.beans.binding.ObjectExpression;
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

import java.util.*;

public class Main extends Application {

    static final int SEATING_CAPACITY = 42;
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        // Create a HashMap object called users to store userName and seatNumber
        HashMap<Integer, String> users = new HashMap<Integer, String>();
        int seatNumber = 0;
        Object value = null;

        String userOption;

        System.out.println("\n***************************");
        System.out.println("DENUWARA MANIKE TRAIN SEATS BOOKING PROGRAM");
        System.out.println("***************************\n");

        do {
            System.out.println("-------------------------------------------------------------");
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
            System.out.println("-------------------------------------------------------------");

            switch (userOption) {
                case "A":
                case "a":
                    addCustomer(users);
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
                    deleteCustomer(users);
                    break;

                case "F":
                case "f":
                    findSeat(users, seatNumber);
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
                    alphabeticalOrder(users);
                    break;

                case "q":
                case "Q":
                    System.out.println("Program is now Existing..");
                    break;

                default:
                    System.out.println("You have entered a Invalid Input!");
                    System.out.println("---------------------------------");
            }
        } while (!userOption.equals("q"));
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

    private void loadProgramFile() {

    }

    private void storeData() {

    }

    private void addCustomer(HashMap<Integer, String> users) {
        System.out.println("\n------------------------");
        System.out.println("ADD A CUSTOMER TO A SEAT");
        System.out.println("------------------------\n");

        Stage window = new Stage();
        window.setTitle("Train Seat Booking Program");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));

        Scene scene1 = new Scene(flowPane, 445, 500);

        //close button
        Button closeBtn = new Button();
        closeBtn.setText("Close");
        closeBtn.setOnAction(e -> window.close());

        for (int i = 1; i <= SEATING_CAPACITY; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(String.valueOf(i));
            flowPane.getChildren().add(seat);
            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");

/*            seat.addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
                        }
                    });

            seat.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
                        }
                    });*/

            if (users.containsKey(i)) {
                seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
            } else {
                seat.setOnAction(event -> {
                    if (!users.containsKey(seat.getId())) {
                        try {
                            Stage window2 = new Stage();

                            window2.setTitle("Confirmation");

                            GridPane grid = new GridPane();
                            grid.setPadding(new Insets(10, 10, 10, 10));
                            grid.setMinSize(300, 300);
                            grid.setVgap(5);
                            grid.setHgap(5);

                            Text username = new Text("Enter your name : ");
                            grid.add(username, 0, 0);

                            TextField userName = new TextField();
                            grid.add(userName, 1, 0);

                            Button confirmUser = new Button("Confirm your Seat #" + seat.getId());
                            grid.add(confirmUser, 0, 2);

                            Button closeButton1 = new Button("Close");
                            grid.add(closeButton1, 0, 3);
                            closeButton1.setOnAction(e -> {
                                window.close();
                                window2.close();
                            });

                            confirmUser.setOnAction(event1 -> {
                                //print the current action in console
                                System.out.println(userName.getText() + " has booked Seat #" + seat.getId() + "\n");
                                //hashMap data
                                users.put(Integer.valueOf(seat.getId()), userName.getText());
                                //popup alertBox
                                alertBoxWindow("Alert!", "You have successfully booked Seat #" + seat.getId());
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

    private void displayEmptySeats(HashMap<Integer, String> users) {
        System.out.println("\n-------------------");
        System.out.println("DISPLAY EMPTY SEATS");
        System.out.println("-------------------");

        Stage window = new Stage();
        window.setTitle("Train Seat Booking Program");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));

        Scene scene = new Scene(flowPane, 445, 500);

        for (int i = 1; i <= SEATING_CAPACITY; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(String.valueOf(i));
            flowPane.getChildren().add(seat);

            if (users.containsKey(i)) {
                seat.setDisable(true);
            } else {
                seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            }
        }
        window.setScene(scene);
        window.showAndWait();
    }

    private void displayAllSeats(HashMap<Integer, String> users) {
        System.out.println("\n-----------------");
        System.out.println("DISPLAY ALL SEATS");
        System.out.println("-----------------");

        Stage window = new Stage();
        window.setTitle("Train Seat Booking Program");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));

        Scene scene = new Scene(flowPane, 445, 500);

        for (int i = 1; i <= SEATING_CAPACITY; i++) {
            Button seat = new Button("Seat " + String.format("%02d", i));
            seat.setId(Integer.toString(i));
            flowPane.getChildren().add(seat);

            if (users.containsKey(i)) {
                seat.setStyle("-fx-background-color: rgba(227,35,109,0.8)");
            } else {
                seat.setStyle("-fx-background-color: rgba(0,166,156,0.8)");
            }
        }
        window.setScene(scene);
        window.showAndWait();
    }

    private void findSeat(HashMap<Integer, String> users, int seatNumber) {
        System.out.println("\n--------------");
        System.out.println("FIND USER SEAT");
        System.out.println("--------------\n");

        System.out.print("Which seat do you need to find (Prompt Username) : ");
        String userName = sc.next();
        for (HashMap.Entry<Integer, String> entry : users.entrySet()) {
            if (userName.equals(entry.getValue())) {
                System.out.println(userName + " already booked seat #" + entry.getKey());
                alertBoxWindow("Alert", userName + " already booked seat #" + entry.getKey());
            } else {
                System.out.println("No seat has been booked under " + userName);
            }
        }
    }

    public void deleteCustomer(HashMap<Integer, String> users) {
        System.out.println("\n-------------");
        System.out.println("DELETE A SEAT");
        System.out.println("-------------\n");

        System.out.print("Which seat do you want to delete (Seat #) : ");
        int removedSeat = sc.nextInt();
        if (users.containsKey(removedSeat)) {
            users.remove(removedSeat);
            System.out.println("Seat #" + removedSeat + " is successfully deleted!");
            alertBoxWindow("Alert", "Seat #" + removedSeat + " is successfully deleted!");
        } else {
            System.out.println("No seat has been booked for this seat number");
        }
    }

    private void alphabeticalOrder(HashMap<Integer, String> users) {
/*        System.out.println("\n-------------------------------------------------");
        System.out.println("VIEW SEATS IN ORDERED ALPHABETICALLY BY USER NAME");
        System.out.println("-------------------------------------------------\n");

        // Converting HashMap keys into ArrayList
        List<String> userNameList = new ArrayList<String>(users.values());
        System.out.println("==> Size of Key list: " + userNameList.size());

        for (int i = userNameList.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (userNameList.get(j + 1) == null) {
                    continue;
                }
                if (userNameList.get(j) == null || userNameList.get(j + 1).compareTo(userNameList.get(j)) < 0) {
                    String temp = userNameList.get(j + 1);
                    userNameList.get(j + 1) = userNameList.get(j);
                    userNameList.set(j) = temp;
                }
            }
        }
        System.out.println(Arrays.toString(userNameList));*/

    }
}
