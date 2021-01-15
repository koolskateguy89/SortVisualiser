package koolskateguy89;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import koolskateguy89.screens.MenuScreen;

public class Main extends Application {
	
	//private static Main instance;

	private Stage stage;

	@Override
	public void start(Stage primaryStage) {
		//instance = this;
		try {
			stage = primaryStage;
			stage.setTitle("Sort Visualiser V3");
			stage.setResizable(false);
			
			//Menu menu = new Menu();
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
