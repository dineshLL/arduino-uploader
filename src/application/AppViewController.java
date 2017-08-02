package application;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AppViewController implements Initializable {

	private static final String TEXT_ONE = "TEXT_ONE";
	private static final String TEXT_TWO = "TEXT_TWO";
	private String inoPath = System.getProperty("user.dir") + "/src/application/app.ino";

	@FXML private TextField one;
	@FXML private TextField two;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void uploadClick() {

		if(one.getText().length() > 0 && two.getText().length() > 0) {
			Path path = Paths.get(inoPath);

			Charset charset = StandardCharsets.UTF_8;

			String content;
			try {
				content = new String(Files.readAllBytes(path), charset);
				content = content.replaceAll(TEXT_ONE, one.getText());
				content = content.replaceAll(TEXT_TWO, two.getText());

				Files.write(path, content.getBytes(charset));
				build();

				content = content.replaceAll(one.getText(), TEXT_ONE);
				content = content.replaceAll(two.getText(), TEXT_TWO);
				Files.write(path, content.getBytes(charset));

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Successfull");
			alert.setHeaderText("Text upload completed successfully");
			alert.setContentText("Entered text uploaded to the led board successfully");

			alert.showAndWait();
		}
	}



	private void build() throws IOException {
		try {

			Process p = Runtime
					.getRuntime()
					.exec("cmd /c cmd.exe /K \"cd src/application && upload.bat && exit\"");
			p.waitFor();
		} catch (IOException | InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
