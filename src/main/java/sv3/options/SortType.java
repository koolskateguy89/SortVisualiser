package sv3.options;

import java.util.List;
import java.util.function.Function;

import javafx.scene.Node;

import sv3.sorts.BubbleSort;
import sv3.sorts.InsertionSort;
import sv3.sorts.QuickSort;
import sv3.sorts.SelectionSort;
import sv3.sorts.Sort;

// This also acts as a SortFactory
public enum SortType {

	BUBBLE    (BubbleSort::new),
	INSERTION (InsertionSort::new),
	MERGE     (null),    // TODO: implement in-place merge sort
	QUICK     (QuickSort::new),
	SELECTION (SelectionSort::new);


	private final Function<List<Node>, Sort> sorter;

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
