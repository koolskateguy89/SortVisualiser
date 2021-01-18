package sv3.options;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import javafx.scene.Node;

import sv3.utils.Utils;

public enum Order {
	// put the list in order
	IN_ORDER(Order::putInOrder),
	// reverse the list from its CURRENT ORDER
	REVERSE (Utils::reverse),
	// put the list into a randomised order
	RANDOM  (Utils::shuffle);


	private final Consumer<List<Node>> orderer;

	Order(Consumer<List<Node>> cons) {
		orderer = cons;
	}

	public static Order getDefault() {
		return RANDOM;
	}

	// Enforce the order this enum refers to upon the list
	public void enforce(List<Node> list) {
		if (orderer != null)
			orderer.accept(list);
	}

	
	private static final Comparator<Node> RECT_COMP = (a,b) -> {
		double h1 = Utils.getRect(a).getHeight();
		double h2 = Utils.getRect(b).getHeight();

		if (h1 == h2)
			return 0;
		return h1 > h2 ? 1 : -1;
	};

	private static void putInOrder(List<Node> list) {
		List<Node> copy = new ArrayList<>(list);
		copy.sort(RECT_COMP);
		list.clear();
		list.addAll(copy);
	}

}
