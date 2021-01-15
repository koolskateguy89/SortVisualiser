package sv3.screens;

import static sv3.utils.Utils.sleep;

import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import sv3.options.Order;
import sv3.options.SortType;
import sv3.options.TimerUnit;
import sv3.sorts.Sort;
import sv3.utils.Utils;

//933 max width
public class SortScreen extends AnchorPane {
	
	public SortScreen() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/SortScreen.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		loader.load();
	}
	
	@FXML
	private GridPane variablesGrid;
	
	@FXML
	private Label length;

    @FXML
    private Label access;
    
    @FXML
    private Label change;
    
    // timed in nanoseconds, display mode set by user
    @FXML
    private Label time;
    private DoubleProperty timer = new SimpleDoubleProperty();
    
    @FXML
    private Text type;

    @FXML
    private HBox box;
    
    private List<Node> list;
    
	// Default size 10
	private int size = 10;
	public void setSize(int s) {
		size = s;
		length.setText(Integer.toString(size));
		
		backToDefault();
		
		setup();
	}
	
	// Default selection sort
	private Sort sort;
	
	public void setSortType(SortType sortType) {
	   if (sort != null)
		   sort.destroy();
	   
	   sort = sortType.get(list);
	   sort.bindAccess(access);
	   sort.bindChange(change);
	   
	   type.setText(sort.type());
	   
	   backToDefault();
	}
	
	// random
	private Order order = Order.getDefault();
	
	public void enforceOrder(Order order) {
		this.order = order;
		order.enforce(list);
		
		backToDefault();
	}
   
	public void setUnit(TimerUnit unit) {
		time.textProperty().unbind();
		time.textProperty().bind(timer.divide(unit.divisor()).asString().concat(' ' + unit.toString()));
	}

	/*
	 * Stores whether this current sort has started sorting (incl. has finished sorting)
	 * 
	 * Is false upon:
	 * 				- list size change
	 * 				- sort type change
	 * 				- Order change
	 */
	private BooleanProperty hasStartedSort = new SimpleBooleanProperty(false);
	
	public boolean hasStartedSort() {
		return hasStartedSortProperty().get();
	}
	public BooleanProperty hasStartedSortProperty() {
		return hasStartedSort;
	}
	
	private void backToDefault() {
		if (hasStartedSort.get()) {
			list.forEach(node -> Utils.getRect(node).setFill(Utils.DEFAULT_COLOUR));
			hasStartedSort.set(false);
		}
	}
	
	public void reset() {
		if (sort != null)
			sort.reset();
		
		timer.set(0);
		backToDefault();
	}
    
    @FXML
    private void initialize() {
    	setFocusTraversable(false);
    	setMouseTransparent(true);
    	
    	list = box.getChildren();
    	
    	// selection sort
    	setSortType(SortType.getDefault());
    	// milliseconds
    	setUnit(TimerUnit.getDefault());
    	
    	box.prefWidthProperty().bind(this.widthProperty().multiply(.9));
    	box.prefHeightProperty().bind(this.heightProperty().multiply(.65));
    	
    	box.relocate(5, box.getLayoutY());
    }
    
    /*
     * Populate the box with rectangles
     * Has to be called on JavaFX Application Thread
     */
    public void setup() {
    	if (!Platform.isFxApplicationThread()) {
    		Platform.runLater(this::setup);
    		return;
    	}
    	
    	double width = this.getPrefWidth() / size;
    	
    	list.clear();
    	for (int i = 1; i <= size; i++) {
    		double height = i * (box.getHeight() / size);
    		
    		Rectangle rect = new Rectangle(width, height, Utils.DEFAULT_COLOUR);
    		list.add(rect);
    		
    		// TODO: implement some sort of visual way to see the actual position of each
    		//       rectangle
    	}
    	order.enforce(list);
    }
    
    private Thread sortThread;
    private Thread timerThread;
    
    public void start() {
    	stop = false;
    	sort.setOriginalList(list);
    	
    	// if sorting again right after sorting
    	if (hasStartedSort.get())
    		list.forEach(node -> ((Rectangle)node).setFill(Utils.DEFAULT_COLOUR));
    	
    	sortThread = new Thread(this::startSort, "Sort thread");
    	sortThread.setDaemon(true);
    	sortThread.start();
    	
    	timerThread = new Thread(this::startTimer, "Timer thread");
    	timerThread.setDaemon(true);
    	timerThread.start();
    }
    
    private volatile boolean sorting = false;
    private volatile boolean stop = false;
    
    public void stop() {
    	stop = true;
    	sorting = false;
    	
    	if (sortThread != null && sortThread.isAlive())
    		sortThread.interrupt();
    	
    	if (timerThread != null && timerThread.isAlive())
    		timerThread.interrupt();
    	
    	if (onSortFinished != null)
    		onSortFinished.run();
    }
    
    private void startSort() {
    	sleep(500);
    	
    	hasStartedSort.set(true);
    	
    	if (stop)
    		return;
    	
    	sorting = true;
    	sort.sort();
    	sorting = false;
    	
    	if (stop)
    		return;
    	
    	list.forEach(node -> Utils.getRect(node).setFill(Utils.DONE));
    	
    	if (onSortFinished != null)
    		onSortFinished.run();
    }
    
    private void startTimer() {
    	// wait to start sorting
    	while (!sorting) {
    		sleep(40);
    	}
    	
    	final long start = System.nanoTime();
    	while (!stop && sorting) {
    		long now = System.nanoTime();
    		Platform.runLater(() -> timer.set((double)now - start));
    		sleep(10);
    	}
    }
    
    private Runnable onSortFinished;
    
    public void setOnSortFinished(Runnable r) {
    	onSortFinished = r;
    }
}
