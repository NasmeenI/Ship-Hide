package ui;

import application.GameProcess;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import logic.base.Map;
import object.DoorJail;
import static utilz.Constants.DoorJail.*;

public class PasswordPopUp {
	
	private String correctPassword;
	
	public PasswordPopUp() {
		this.correctPassword = "1234";
	}
	
	public GridPane ShowPasswordScene(){
		PasswordField passwordField = new PasswordField();
        passwordField.setEditable(false);
        
        Button[] numberButtons = new Button[10];
        for (int i = 0; i < 10; i++) {
            final int number = i;
            numberButtons[i] = new Button(String.valueOf(number));
            numberButtons[i].setStyle("-fx-background-color: white; -fx-border-color: black;");
            numberButtons[i].setFont(Font.font("Arial", 32));
            numberButtons[i].setPadding(new Insets(16));
            numberButtons[i].setOnAction(event -> passwordField.setText(passwordField.getText() + number));
        }

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(event -> passwordField.clear());
        clearButton.setFont(Font.font("Arial", 24));
        clearButton.setPadding(new Insets(8));
        clearButton.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white;");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            if (passwordField.getText().equals(correctPassword)) {
            	DoorJail.setOpened(true);
            	Map.setStageMap(X_POS_DOORJAIL, Y_POS_DOORJAIL+2, 1);
                Label passwordCorrectLabel = new Label("Password Correct");
                passwordCorrectLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
                passwordCorrectLabel.setTextFill(Color.web("#228B22"));
                passwordCorrectLabel.setTextAlignment(TextAlignment.CENTER);
                GridPane.setMargin(passwordCorrectLabel, new Insets(20, 0, 0, 0));
                GridPane.setColumnSpan(passwordCorrectLabel, 3);
                GridPane.setHalignment(passwordCorrectLabel, javafx.geometry.HPos.CENTER);
                GridPane.setValignment(passwordCorrectLabel, javafx.geometry.VPos.CENTER);
                GridPane.setFillHeight(passwordCorrectLabel, true);
                GridPane.setFillWidth(passwordCorrectLabel, true);
                GridPane.setConstraints(passwordCorrectLabel, 0, 0, 3, 6);
                ((GridPane) submitButton.getParent()).getChildren().add(passwordCorrectLabel);
            }
            passwordField.clear();
        });
        
        submitButton.setFont(Font.font("Arial", 24));
        submitButton.setPadding(new Insets(8));
        submitButton.setStyle("-fx-background-color: #228B22; -fx-text-fill: white;");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        passwordLabel.setTextFill(Color.web("#FFD700"));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setScaleX(0.7);
        gridPane.setScaleY(0.7);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #000080;");
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(numberButtons[1], 0, 2);
        gridPane.add(numberButtons[2], 1, 2);
        gridPane.add(numberButtons[3], 2, 2);
        gridPane.add(numberButtons[4], 0, 3);
        gridPane.add(numberButtons[5], 1, 3);
		gridPane.add(numberButtons[6], 2, 3);
		gridPane.add(numberButtons[7], 0, 4);
		gridPane.add(numberButtons[8], 1, 4);
		gridPane.add(numberButtons[9], 2, 4);
		gridPane.add(clearButton, 0, 5);
		gridPane.add(numberButtons[0], 1, 5);
		gridPane.add(submitButton, 2, 5);
		GridPane.setHalignment(numberButtons[2], HPos.CENTER);
		GridPane.setHalignment(numberButtons[3], HPos.RIGHT);
		GridPane.setHalignment(numberButtons[5], HPos.CENTER);
		GridPane.setHalignment(numberButtons[6], HPos.RIGHT);
		GridPane.setHalignment(numberButtons[8], HPos.CENTER);
		GridPane.setHalignment(numberButtons[9], HPos.RIGHT);
		GridPane.setHalignment(numberButtons[0], HPos.CENTER);
		GridPane.setHalignment(passwordLabel, HPos.CENTER);
		
		Button backButton = new Button("Back");
		backButton.setFont(Font.font("Arial", 24));
		backButton.setPadding(new Insets(8));
		backButton.setStyle("-fx-background-color: #000080; -fx-text-fill: white;");
		backButton.setOnAction(event -> GameProcess.removeGridRoot(gridPane));

		GridPane.setConstraints(backButton, 2, 0, 1, 1, HPos.RIGHT, VPos.TOP);
		gridPane.getChildren().add(backButton);
		
		return gridPane;
	}

	public String getCorrectPassword() {
		return correctPassword;
	}

	public void setCorrectPassword(String correctPassword) {
		this.correctPassword = correctPassword;
	}
}