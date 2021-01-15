package dara.sorts;

import static dara.utils.Utils.sleep;
import static dara.utils.Utils.swapRight;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;

import dara.utils.Runnables;
import dara.utils.Utils;

public class BubbleSort extends AbstractSort {

	public BubbleSort(List<Node> list) {
		super(list);
	}
	
	public String type() {
		return "Bubble";
	}
	
	private LongProperty access = new SimpleLongProperty();
	private LongProperty changes = new SimpleLongProperty();
	
	public void clear() {
		access.set(0);
		changes.set(0);
	}
	
	/*
	 * "increasing" bubble sort - the maximum gets moved to the right in each pass
	 * Maximum of unsorted 'sub-array' gets moved to the end of it(/start of sorted 'sub-array')
	 * on each pass
	 */
	public void sort() {
		Platform.runLater(this::clear);
		
		for (int i = 0; i < list.size() - 1; i++) {
			int j;
			for (j = 1; j < list.size() - i; j++) {
				
				double left = getHeight(j-1);
				double right = getHeight(j);
				
				Runnable updater = () -> incAccess(2);
				AtomicInteger a = new AtomicInteger(3);
				
				// Move larger rectangle to the right
				boolean update = left > right;
				if (update) {
					final int leftIndex = j - 1;
					updater = Runnables.andThen(updater, () -> {
						incChanges(2);
						a.incrementAndGet();
						swapRight(list, leftIndex, true, true);
					});
				}
				
				Platform.runLater(updater);
				
				// Sleeping should be done after queuing the updater
				if (update)
					sleep(30);
			}
			// highlight the value that was put at the end of the array
			// (the i+1'th largest value)
			//IntStream.range(j-1, list.size()).forEach(x -> getRect(x).setFill(Utils.DONE));
			getRect(j-1).setFill(Utils.DONE);
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
