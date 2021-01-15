package sv3.sorts;

import static sv3.utils.Utils.insert;
import static sv3.utils.Utils.sleep;

import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

import sv3.utils.Runnables;
import sv3.utils.Utils;

public class InsertionSort extends AbstractSort {

	public InsertionSort(List<Node> list) {
		super(list);
	}

	public String type() {
		return "Insertion";
	}
	
	private LongProperty access = new SimpleLongProperty();
	private LongProperty changes = new SimpleLongProperty();
	
	public void clear() {
		access.set(0);
		changes.set(0);
	}
	
	// insert next value into the sorted sub-array;
	// shift right all between insertion index and original index
	public void sort() {
		Platform.runLater(this::clear);
		
		Rectangle done = null;
		for (int i = 1; i < list.size(); i++) {
			double height = getHeight(i);
			Platform.runLater(() -> incAccess(1));

			// Loop through sorted sub-array
			for (int j = 0; j < i; j++) {
				done = getRect(j);
				double next = done.getHeight();
				Runnable updater = () -> incAccess(1);
				
				// Insert value at correct position in sub-array
				boolean exit = height < next;
				if (exit) {
					final int from = i;
					final int at = j;
					updater = Runnables.andThen(updater, () -> {
						incChanges(from - at + 1); // shifts + insertion
						insert(list, from, at, true, true);
					});
				}
				
				Platform.runLater(updater);
				
				if (exit) {
					sleep(15);
					waitForRunLater();
					break;
				}
			}
			
			getRect(i).setFill(Utils.DONE);
			if (done != null && done.getHeight() < height)
				done.setFill(Utils.DEFAULT_COLOUR);
		}
	}
	
	private void incAccess(int inc) {
		access.set(access.get() + inc);
	}
	
	public void bindAccess(Label accessText) {
		bindText(accessText, access);
	}
	
	private void incChanges(int inc) {
		changes.set(changes.get() + inc);
	}

	public void bindChange(Label changeText) {
		bindText(changeText, changes);
	}
	
}
