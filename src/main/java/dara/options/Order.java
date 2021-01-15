package dara.options;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import javafx.scene.Node;

import dara.utils.Utils;

public enum Order {
	// do nothing
	IN_ORDER(Order::putInOrder),
	// reverse the list from its CURRENT ORDER
	REVERSE (Utils::reverse),
	// put the list into a randomised order
	RANDOM  (Utils::shuffle);

	
	Consumer<List<Node>> orderer;

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
	
	
	private static final Comparator<Node> COMP = (a,b) -> {
		double h1 = Utils.getRect(a).getHeight();
		double h2 = Utils.getRect(b).getHeight();
		
		if (h1 == h2)
			return 0;
		return h1 > h2 ? 1 : -1;
	};
	
	private static void putInOrder(List<Node> list) {
		List<Node> copy = new ArrayList<>(list);
		copy.sort(COMP);
		list.clear();
		list.addAll(copy);
	}
	
}
