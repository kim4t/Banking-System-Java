package gui.scenes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

import accounts.Account;
import dao.implementations.AccountDAOImpl;
import dao.implementations.UserDAOImpl;
import dao.interfaces.AccountDAO;
import gui.main.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
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
/**
 * 
 * @author Taeho Kim 
 *
 */
public class AccountScene {
	/**
	 * Get the account number from Default scene and 
	 * return the the scene which has Withdraw, deposit, close, cancel, show every account button
	 * 
	 * @param accountNum
	 * @return scene
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static Scene getScene(String accountNum) throws FileNotFoundException, ParseException {

		GridPane grid = new GridPane();
		// grid.setAlignment(Pos.TOP_CENTER);
		grid.setStyle("-fx-background-color: white;");
		grid.setMaxWidth(400);
		grid.setMaxHeight(300);
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		UserDAOImpl userDAO = new UserDAOImpl();
		AccountDAOImpl dao = new AccountDAOImpl(userDAO);
		Account a = dao.getAccount(accountNum);

		Image img = new Image("logo.png");
		ImageView logo = new ImageView(img);
		grid.add(logo, 0, 0);

		Text balance = new Text("Account Balance:\n$" + a.getAccountBalance());
		grid.add(balance, 0, 1);

		TextField accountField = new TextField();
		grid.add(accountField, 0, 2);

		Button withdrawButton = new Button("Withdraw");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(withdrawButton);
		grid.add(hbBtn, 1, 0);

		withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				a.setAccountBalance(a.getAccountBalance().subtract(new BigDecimal(accountField.getText())));
				AccountDAO accountDAOImpl = new AccountDAOImpl(userDAO);
				try {
					accountDAOImpl.updateAccount(a);
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				balance.setText("Account Balance:\n$" + a.getAccountBalance());

			}
		});

		Button depositButton = new Button("Deposit");
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(depositButton);
		grid.add(hbBtn, 1, 1);

		depositButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				a.setAccountBalance(a.getAccountBalance().add(new BigDecimal(accountField.getText())));
				AccountDAO accountDAOImpl = new AccountDAOImpl(userDAO);
				try {
					accountDAOImpl.updateAccount(a);
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				balance.setText("Account Balance:\n$" + a.getAccountBalance());
			}
		});

		Button closeButton = new Button("Close");
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(closeButton);
		grid.add(hbBtn, 1, 2);

		closeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				
				AccountDAO accountDAOImpl = new AccountDAOImpl(userDAO);
				
					try {
						if (accountDAOImpl.removeAccount(a.getAccountId()) == false) {
							Alert alert = new Alert(AlertType.ERROR, "Error removing account!", ButtonType.OK);
							alert.showAndWait();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Alert alert = new Alert(AlertType.CONFIRMATION, "Account has deleted!", ButtonType.OK);
					alert.showAndWait();
					//balance.setText("This Account has deleted please go back");
					Main.changeScene(DefaultScene.getScene());
			}
		});


		Button accountButton = new Button("Show Every Account List");
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(accountButton);
		grid.add(hbBtn, 0, 4);
		
		String driversLicense = a.getDriversLicense();
		User u = userDAO.getUser(driversLicense);
		
		
		accountButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Main.changeScene(GetAccountScene.getScene(u));
					System.out.println(u);
				} catch (FileNotFoundException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Button addAccountButton = new Button("Add New Account");
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(addAccountButton);
		grid.add(hbBtn, 0, 5);
		
		
		
		addAccountButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Main.changeScene(AddAccountScene.getScene(u));
				} catch (FileNotFoundException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		Button cancleButton = new Button("Cancle");
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(cancleButton);
		grid.add(hbBtn, 0, 7);

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
