package dara.sorts;

import static dara.utils.Utils.sleep;
import static dara.utils.Utils.swap;

import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;

import dara.utils.Runnables;
import dara.utils.Utils;

public class SelectionSort extends AbstractSort {
	
	public SelectionSort(List<Node> list) {
		super(list);
	}
		
	public String type() {
		return "Selection";
	}
	
	private LongProperty access = new SimpleLongProperty();
	private LongProperty changes = new SimpleLongProperty();
	
	public void clear() {
		access.set(0);
		changes.set(0);
	}
	
	/*
	 * Find the minimum in the unsorted sub-array and move it to the end of
	 * the sorted sub-array
	 */
	public void sort() {
		Platform.runLater(this::clear);
		
		for (int i = 0; i < list.size() - 1; i++) {
			double minHeight = getHeight(i);
			int minPos = i;
			int acc = 1;	// accesses counter
			
			// Find the index of the minimum in the unsorted sub-array
			for (int j = i+1; j < list.size(); j++) {
				double height = getHeight(j);
				acc++;
				
				if (height < minHeight) {
					minHeight = height;
					minPos = j;
				}
			}
			
			final int ac = acc;
			Runnable updater = () -> incAccess(ac);
			
			// Append minimum to the sorted sub-array
			boolean update = minPos != i;
			if (update) {
				final int from = i;
				final int to = minPos;
				updater = Runnables.andThen(updater, () -> {
					incChanges(2);
					swap(list, from, to, true, false);
				});
			}
			
			Platform.runLater(updater);
			
			// Sleeping should be done after queuing the updater
			if (update) {
				sleep(15);
			}
			waitForRunLater();
			
			getRect(i).setFill(Utils.DONE);
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
