package gui.scenes;

import java.io.FileNotFoundException;
import java.text.ParseException;

import accounts.Account;
import accounts.AccountType;
import dao.implementations.AccountDAOImpl;
import dao.implementations.UserDAOImpl;
import gui.main.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import users.User;

public class AddAccountScene {

	public static Scene getScene(User u) throws FileNotFoundException, ParseException {
		UserDAOImpl userDAO = new UserDAOImpl();

		GridPane grid = new GridPane();
		// grid.setAlignment(Pos.TOP_CENTER);
		grid.setStyle("-fx-background-color: white;");
		grid.setMaxWidth(400);
		grid.setMaxHeight(300);
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));


		Image img = new Image("logo.png");
		ImageView logo = new ImageView(img);
		grid.add(logo, 0, 0);

		AccountType type[] = { AccountType.STUDENT_CHECKING, AccountType.PERSONAL_CHECKING, AccountType.BUSINESS_CHECKING,
				AccountType.STUDENT_SAVINGS, AccountType.PERSONAL_CHECKING, AccountType.PERSONAL_SAVINGS};

		Text selectAccount = new Text("Select an Account Type Below:");
		grid.add(selectAccount, 0, 1);

		// Create a combo box

		ComboBox box = new ComboBox(FXCollections.observableArrayList(type));

		grid.add(box, 0, 2);

		Button submitButton = new Button("Submit");
		HBox hbBtn = new HBox(10);
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(submitButton);
		grid.add(hbBtn, 0, 7);
		
		
		
		String driverLicense = u.getDriversLicense();
		AccountDAOImpl dao = new AccountDAOImpl(userDAO);
		
		
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				AccountType newAccount = (AccountType) box.getValue();
				try {
					Account account = new Account(driverLicense, newAccount);
					dao.addAccount(account);
					Alert alert = new Alert(AlertType.CONFIRMATION, "New Account has made", ButtonType.OK);
					alert.showAndWait();
					Main.changeScene(DefaultScene.getScene());
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

		Button cancleButton = new Button("Cancle");
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(cancleButton);
		grid.add(hbBtn, 1, 7);

		cancleButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Main.changeScene(DefaultScene.getScene());
			}
		});

		Text notFederallyInsuredText = new Text("Not Federally insured by NCUA.");
		grid.add(notFederallyInsuredText, 0, 10);

		BorderPane bp = new BorderPane();
		bp.setCenter(grid);
		grid.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

		Scene scene = new Scene(bp, 475, 550);
		scene.getStylesheets().add(LoginScene.class.getResource("/Login.css").toExternalForm());

		return scene;
	}
}
