package gui.scenes;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import accounts.Account;
import dao.implementations.AccountDAOImpl;
import dao.implementations.UserDAOImpl;
import gui.main.Main;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import users.User;

/**
 * 
 * @author Taeho Kim
 *
 */
public class GetAccountScene {
	/**
	 * Get the user object from account Account Scene and return scene which has
	 * list of every account the user has. Also if the user double click the
	 * account, then back to corresponding account Scene
	 * 
	 * @param u
	 * @return Scene
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static Scene getScene(User u) throws FileNotFoundException, ParseException {
		String driverLicense = u.getDriversLicense();

		List<Account> arr = new ArrayList<>();
		UserDAOImpl userDAO = new UserDAOImpl();
		AccountDAOImpl dao = new AccountDAOImpl(userDAO);
		System.out.println(driverLicense);
		arr = dao.getUserAccounts(driverLicense);

		TableView tableView = new TableView();

		TableColumn<String, Account> column1 = new TableColumn<>("AccountNumber");
		column1.setCellValueFactory(new PropertyValueFactory<>("accountId"));

		TableColumn<String, Account> column2 = new TableColumn<>("Balance");
		column2.setCellValueFactory(new PropertyValueFactory<>("accountBalance"));

		column1.setPrefWidth(220);
		column2.setPrefWidth(300);

		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);

		for (Account a : arr) {
			tableView.getItems().add(a);
		}

		tableView.setOnMouseClicked((MouseEvent event) -> {
			if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				try {
					Account account = (Account) tableView.getSelectionModel().getSelectedItem();
					Main.changeScene(AccountScene.getScene(account.getAccountId()));
				} catch (FileNotFoundException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		VBox vbox = new VBox(tableView);

		Scene scene = new Scene(vbox, 500, 400);

		return scene;
	}
}
