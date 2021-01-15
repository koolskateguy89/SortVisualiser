package sv3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import sv3.screens.MenuScreen;

// TODO: update and configure pom
// TODO: test running this with Maven
public class Main extends Application {
	
	//private static Main instance;

	@Override
	public void start(final Stage stage) {
		try {
			stage.setTitle("Sort Visualiser V3");
			stage.setResizable(false);
			
			MenuScreen main = new MenuScreen();

			Scene scene = new Scene(main);
			
			stage.setScene(scene);
			
			stage.setOnShown(event -> main.setup());
			
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void launch0(String[] args) {
		launch(args);
	}
}
