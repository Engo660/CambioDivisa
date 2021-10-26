package dad.cambiodivisa;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

	private TextField origenText;
	private TextField destinoText;

	private ComboBox<String> origenComboBox;
	private ComboBox<String> destinoComboBox;

	private Button cambiarButton;

	private VBox root;

	private HBox origenHBox;
	private HBox destinoHBox;

	private Alert alert;

	private Double base = 0.0;

	private String origenString;
	private String destinoString;

	private Divisa euro = new Divisa("Euro", 1.0);
	private Divisa libra = new Divisa("Libra", 0.8873);
	private Divisa dolar = new Divisa("Dolar", 1.2007);
	private Divisa yen = new Divisa("Yen", 133.59);
	private Divisa origen;
	private Divisa destino;

	@Override
	public void start(Stage primaryStage) throws Exception {

		origenText = new TextField("0");
		origenText.setPrefColumnCount(5);

		destinoText = new TextField();
		destinoText.setPrefColumnCount(5);
		destinoText.setEditable(false);

		cambiarButton = new Button();
		cambiarButton.setAlignment(Pos.CENTER);
		cambiarButton.setText("Cambiar");
		cambiarButton.setDefaultButton(true);
		cambiarButton.setOnAction(e -> cambioDivisa());

		origenComboBox = new ComboBox<String>();
		origenComboBox.getItems().addAll("EUR", "YEN", "LIB", "USD");
		origenComboBox.getSelectionModel().selectFirst();

		destinoComboBox = new ComboBox<String>();
		destinoComboBox.getItems().addAll("YEN", "EUR", "LIB", "USD");
		destinoComboBox.getSelectionModel().selectFirst();

		origenHBox = new HBox(5);
		origenHBox.setAlignment(Pos.CENTER);

		destinoHBox = new HBox(5);
		destinoHBox.setAlignment(Pos.CENTER);

		origenHBox.getChildren().addAll(origenText, origenComboBox);
		destinoHBox.getChildren().addAll(destinoText, destinoComboBox);

		root = new VBox(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(origenHBox, destinoHBox, cambiarButton);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setTitle("Cambio de divisa");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void cambioDivisa() {
		origenString = origenComboBox.getSelectionModel().getSelectedItem().toString();
		destinoString = destinoComboBox.getSelectionModel().getSelectedItem().toString();
		errorNada();
		origen = auxDiv(origenString);
		destino = auxDiv(destinoString);
		destinoText.setText("" + destino.fromEuro(origen.toEuro(base)));

	}

	public Divisa auxDiv(String a) {
		Divisa aux = new Divisa("", 0.0);
		switch (a) {
		case "EUR":
			aux = euro;
			break;
		case "YEN":
			aux = yen;
			break;
		case "LIB":
			aux = libra;
			break;
		case "USD":
			aux = dolar;
			break;
		}
		return aux;
	}

	public void errorNada() {
		try {
			if (origenText.getText().equals("") || (origenText.getText().equals("0"))) {
				throw new Exception();
			} else {
				errorNum();
			}
		}

		catch (Exception e) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("No se ha introducido ningun valor");
			alert.showAndWait();
		}
	}

	public void errorNum() {
		try {
			base = Double.parseDouble(origenText.getText());
		} catch (Exception e) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Solo se pueden introducir numeros");
			alert.showAndWait();
		}
	}

	public static void main(String args[]) {
		launch(args);
	}
}
