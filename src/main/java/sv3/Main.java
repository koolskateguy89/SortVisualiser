package sv3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import sv3.screens.MenuScreen;

public class Main extends Application {
	
	@Override
	public void start(final Stage stage) {
		try {
			stage.setTitle("Sort Visualiser V3");
			stage.setResizable(false);
			
			MenuScreen mainScreen = new MenuScreen();

			Scene scene = new Scene(mainScreen);
			
			stage.setScene(scene);
			
			stage.setOnShown(event -> mainScreen.setup());
			
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
