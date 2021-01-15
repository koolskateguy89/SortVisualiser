package koolskateguy89.options;

import java.util.List;
import java.util.function.Function;

import javafx.scene.Node;

import koolskateguy89.sorts.BubbleSort;
import koolskateguy89.sorts.InsertionSort;
import koolskateguy89.sorts.QuickSort;
import koolskateguy89.sorts.SelectionSort;
import koolskateguy89.sorts.Sort;

// might rename this 'Sorts'
public enum SortType {
	
	BUBBLE    (BubbleSort::new),
	INSERTION (InsertionSort::new),
	MERGE     (null),    // TODO: implement in-place merge sort
	QUICK     (QuickSort::new),
	SELECTION (SelectionSort::new);

	
	Function<List<Node>, Sort> sorter;

	SortType(Function<List<Node>, Sort> func) {
		sorter = func;
	}

	public static SortType getDefault() {
		return SELECTION;
	}

	// Return the sort type this enum refers to
	public Sort get(List<Node> list) {
		return sorter != null ? sorter.apply(list) : null;
	}
}
