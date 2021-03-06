package sv3.sorts;

import static sv3.utils.Utils.sleep;
import static sv3.utils.Utils.swap;

import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

import sv3.utils.Runnables;
import sv3.utils.Utils;

public class QuickSort extends AbstractSort {

	public QuickSort(List<Node> list) {
		super(list);
	}

	@Override
	public String type() {
		return "Quick";
	}

	private final LongProperty access = new SimpleLongProperty();
	private final LongProperty changes = new SimpleLongProperty();

	@Override
	public void clear() {
		access.set(0);
		changes.set(0);
	}

	@Override
	public void sort() {
		quicksort(0, list.size()-1);
	}

	// end acts as pivot
	private void quicksort(int start, int end) {
		if (start >= end)
			return;

		int pivotIndex = partition(start, end);
		quicksort(start, pivotIndex-1);
		quicksort(pivotIndex+1, end);
	}

	// end acts as pivotIndex
	private int partition(int start, int end) {
		waitForRunLater();

		Rectangle pivot = getRect(end);
		pivot.setFill(Utils.HIGHLIGHT);

		double pivotHeight = pivot.getHeight();

		Platform.runLater(() -> incAccess(1));

		// traverse from start to end, trying to find proper pivot index
		// also let everything smaller than the pivot be on its left and larger on right
		int newPivotIndex = start;	// index pivot will be swapped with
		for (int i = start; i < end; i++) {
			double height = getHeight(i);
			Runnable updater = () -> incAccess(1);

			// tbh not 1000% confident on how this works yet but it does
			boolean swap = height < pivotHeight;
			if (swap) {
				final int swap1 = i;
				final int swap2 = newPivotIndex;
				if (swap1 != swap2) {
					updater = Runnables.andThen(updater, () -> {
						incChanges(1);
						swap(list, swap1, swap2, true, true);
					});
				}
				newPivotIndex++;
			}

			Platform.runLater(updater);

			if (swap && i != newPivotIndex-1)
				sleep(15);
		}

		if (newPivotIndex != end) {
			//waitForRunLater();
			final int pivotIndex = newPivotIndex;
			Platform.runLater(() -> {
				incChanges(1);
				swap(list, pivotIndex, end, true, false);
				pivot.setFill(Utils.DONE);
			});
		} else {
			pivot.setFill(Utils.DONE);
		}

		return newPivotIndex;
	}

	private void incAccess(int inc) {
		access.set(access.get() + inc);
	}

	@Override
	public void bindAccess(Label accessText) {
		bindText(accessText, access);
	}

	private void incChanges(int inc) {
		changes.set(changes.get() + inc);
	}

	@Override
	public void bindChange(Label changeText) {
		bindText(changeText, changes);
	}

}
