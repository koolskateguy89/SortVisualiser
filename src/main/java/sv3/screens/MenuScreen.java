package sv3.screens;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import sv3.options.Order;
import sv3.options.SortType;
import sv3.options.TimerUnit;
import sv3.utils.Utils;

// This also kinda acts like a MainScreen
public class MenuScreen extends Pane {

	public MenuScreen() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/MenuScreen.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		loader.load();
	}

	//anchorPane is just a placeholder to store sortScreen
	@FXML
	private AnchorPane anchorPane;
	private SortScreen sortScreen;

	@FXML
	private Pane menuPane;

	@FXML
	private void initialize() throws IOException {
		initSortScreen();

		highlightDefaults();

		reset.disableProperty().bind(sortScreen.hasStartedSortProperty().not());

		// bind the slider's value to the length textfield, as an integer instead of double
		IntegerProperty i = new SimpleIntegerProperty();
		// bind bidirectional so slider value is always an integer
		i.bindBidirectional(slider.valueProperty());
		length.textProperty().bind(i.asString());

		slider.valueProperty().addListener((obs, oldVal, newVal) -> {
			sortScreen.setSize(i.get());
		});

		// start with default max size
		i.set((int)slider.getMax());
	}

	private void initSortScreen() throws IOException {
		sortScreen = new SortScreen();

		sortScreen.setPrefWidth(anchorPane.getPrefWidth());
		sortScreen.setPrefHeight(anchorPane.getPrefHeight());
		anchorPane.getChildren().add(sortScreen);

		sortScreen.setOnSortFinished(() -> Platform.runLater(this::onSortFinish));
	}

	private void highlightDefaults() {
		switch (SortType.getDefault()) {
			case BUBBLE    -> highlight(bubble);
			case INSERTION -> highlight(insertion);
			case QUICK     -> highlight(quick);
			//case MERGE     -> highlight(merge);
			case SELECTION -> highlight(selection);
			default -> throw new IllegalArgumentException("Not yet implemented sort: " + SortType.getDefault());
		}

		switch (Order.getDefault()) {
			case IN_ORDER -> highlight(inOrder);
			case RANDOM   -> highlight(random);
			case REVERSE  -> highlight(reverse);
		}

		switch (TimerUnit.getDefault()) {
			case SECONDS -> select(seconds);
			case MICROS  -> select(micros);
			case MILLIS  -> select(micros);
			case NANOS   -> select(nanos);
		}
	}

	// to be called upon stage showing
	public void setup() {
		sortScreen.setup();
	}


	@FXML
	private VBox sorts;
	@FXML
	private Button bubble;
	@FXML
	private Button insertion;
	@FXML
	private Button quick;
	@FXML
	private Button selection;

	@FXML
	private Button inOrder;
	@FXML
	private Button random;
	@FXML
	private Button reverse;
	@FXML
	private Button reset;

	@FXML
	private CheckBox seconds;
	@FXML
	private CheckBox micros;
	@FXML
	private CheckBox millis;
	@FXML
	private CheckBox nanos;

	@FXML
	private Slider slider;
	@FXML
	private TextField length;

	@FXML
	private Button startBtn;


	@FXML
	void bubble(ActionEvent event) {
		sortScreen.setSortType(SortType.BUBBLE);
		resetSorts();
		highlight(bubble);
    }

    @FXML
    void insertion(ActionEvent event) {
    	sortScreen.setSortType(SortType.INSERTION);
    	resetSorts();
    	highlight(insertion);
    }

    @FXML
    void quick(ActionEvent event) {
    	sortScreen.setSortType(SortType.QUICK);
    	resetSorts();
    	highlight(quick);
    }

    @FXML
    void selection(ActionEvent event) {
    	sortScreen.setSortType(SortType.SELECTION);
    	resetSorts();
    	highlight(selection);
    }

    // TODO: implement Merge sort 1
    // TODO: add Merge button to this FXML
    @FXML
    void merge(ActionEvent event) {
    	sortScreen.setSortType(SortType.MERGE);
    	resetSorts();
    	//highlight(merge);
    }


    @FXML
    void inOrder(ActionEvent event) {
    	sortScreen.enforceOrder(Order.IN_ORDER);
    	resetOrders();
    	highlight(inOrder);
    }

    @FXML
    void random(ActionEvent event) {
    	sortScreen.enforceOrder(Order.RANDOM);
    	resetOrders();
    	highlight(random);
    }

    @FXML
    void reverse(ActionEvent event) {
    	sortScreen.enforceOrder(Order.REVERSE);
    	resetOrders();
    	highlight(reverse);
    }

    @FXML
    void reset(ActionEvent event) {
    	sortScreen.reset();
    }


    /*
     * It doesn't really make much sense to select then deselect then reselect,
     * but this is the most concise way I can think of
     */

    @FXML
    void seconds(ActionEvent event) {
    	sortScreen.setUnit(TimerUnit.SECONDS);
    	resetUnits();
    	select(seconds);
    }

    @FXML
    void micros(ActionEvent event) {
    	sortScreen.setUnit(TimerUnit.MICROS);
    	resetUnits();
    	select(micros);
    }

    @FXML
    void millis(ActionEvent event) {
    	sortScreen.setUnit(TimerUnit.MILLIS);
    	resetUnits();
    	select(millis);
    }

    @FXML
    void nanos(ActionEvent event) {
    	sortScreen.setUnit(TimerUnit.NANOS);
    	resetUnits();
    	select(nanos);
    }


    private void resetSorts() {
    	reset(sorts);
    }

    private void resetOrders() {
    	reset(inOrder, random, reverse);
    }

    private void resetUnits() {
    	seconds.setSelected(false);
    	micros.setSelected(false);
    	millis.setSelected(false);
    	nanos.setSelected(false);
    }

	private static void reset(Button... buttons) {
    	for (Button b : buttons) {
    		unhighlight(b);
    	}
    }

    private static void reset(Pane p) {
    	p.getChildren().forEach(n -> {
    		if (n instanceof Button) {
    			unhighlight(n);
    		}
    	});
    }

    private static void highlight(Button button) {
    	button.getStyleClass().add("highlight");
    }

    private static void unhighlight(Node node) {
    	var sc = node.getStyleClass();
		if (sc.size() > 1)
			sc.remove(1);
    }

    private static void select(CheckBox box) {
    	box.setSelected(true);
    }

    private volatile boolean started = false;

    @FXML
	void start(ActionEvent event) {
    	if (started) {
    		stop();
    		startBtn.setStyle(null);
    		startBtn.setText("Start");
    		started = false;
    		return;
    	}

    	// disable everything but the start button - to allow it to become the stop button
    	menuPane.getChildren().forEach(node -> {
    		if (!isStartButton(node))
    			node.setDisable(true);
    	});

    	startBtn.setText("Stop");
    	startBtn.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF");

    	sortScreen.start();
    	started = true;
	}

    // TODO: learn about how threads handle being interrupted to be able to properly stop
    private void stop() {
    	sortScreen.stop();
    }

    @SuppressWarnings("preview")
	private static boolean isStartButton(Node n) {
    	return n instanceof Button b && b.getText().equals("Start");
    }

    private void onSortFinish() {
    	startBtn.setText("Start");
    	startBtn.setStyle(null);

    	menuPane.getChildren().forEach(node -> node.setDisable(false));
    	started = false;

    	Utils.deselect = Utils.DONE;
    }
}
