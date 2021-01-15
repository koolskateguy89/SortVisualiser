package sv3.utils;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Platform;
import javafx.beans.binding.NumberExpression;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class Utils {

	private Utils() { }

	public static int randInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max);
	}

	public static int randIntClosed(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max+1);
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException nfe) {
			try {
				double d = Double.parseDouble(s);
				return Math.rint(d) == d;
			} catch (NumberFormatException nfe1) {
				return false;
			}
		}
	}

	// Wait (in the called thread) for all queued Runnables to complete
	public static void waitForRunLater() throws InterruptedException {
		Semaphore semaphore = new Semaphore(0);
		Platform.runLater(semaphore::release);
		semaphore.acquire();
	}

	public static Rectangle getRect(Node node) {
		return (Rectangle)node;
	}

	public static void shuffle(List<?> list) {
		final int size = list.size();
		for (int i = 0; i < size-1; i++) {
			int swap = randInt(i+1, size);
			swap0(list, i, swap);
		}
	}

	public static void reverse(List<?> list) {
		final int size = list.size();
		for (int i = 0, j = size-1; i < size/2; i++, j--) {
			swap0(list, i, j);
		}
	}


	private static final Color NICE_GREEN = Color.valueOf("#55FF00");

	public static final Color DEFAULT_COLOUR = Color.WHITESMOKE;
	public static final Color HIGHLIGHT = Color.RED;
	public static final Color DONE = NICE_GREEN;

	// maybe lock it with synchronized ??????
	public static volatile Color deselect = DEFAULT_COLOUR;

	// FIXME: Fix colour selecting & deselecting, especially time delay (see Deselector)


	public static void reset(Node node) {
		Deselector.queue(getRect(node));
	}

	public static void reset(List<Node> list, int i) {
		reset(list.get(i));
	}

	private static void select(Rectangle... rects) {
		for (Rectangle r : rects) {
			r.setFill(HIGHLIGHT);
		}
	}

	// Queue the deselection of rectangles for Deselector
	private static void queueDeselect(Rectangle... rects) {
		Deselector.queueAll(rects);
	}

	// Duplicates aren't allowed so I have to use this workaround :(
	public static void swap(List<Node> list, int p1, int p2, boolean deselectP1, boolean deselectP2) {
		if (p1 == p2)
			return;

		Rectangle uno = getRect(list.get(p1));
		Rectangle dos = getRect(list.get(p2)); // i would make this list.remove(p2) put that wouldn't show it when it's highlighted
		select(uno, dos);

		swap0(list, p1, p2);

		if (deselectP1 && deselectP2)
			queueDeselect(uno, dos);
		else if (deselectP1)
			queueDeselect(uno);
		else if (deselectP2)
			queueDeselect(dos);

	}

	// Doesn't highlight rectangles
	private static <T> void swap0(List<T> list, int p1, int p2) {
		if (p1 == p2)
			return;

		//list.add(p2, list.set(p2 < p1 ? p1-1 : p1, list.remove(p2)) );

		T uno;
		if (p1 < p2) {
			uno = list.set(p1, list.remove(p2));
		} else { // p2 < p1
			uno = list.set(p1-1, list.remove(p2));
		}
		list.add(p2, uno);
	}

	// swap the node at {i} with the node on its right
	public static void swapRight(List<Node> list, int i, boolean deselectLeft, boolean deselectRight) {

		Rectangle left = getRect(list.get(i));
		Rectangle right = getRect(list.get(i+1));
		select(left, right);

		list.remove(i+1); // remove right
		list.add(i, right); // insert right before left

		if (deselectLeft && deselectRight)
			queueDeselect(left, right);
		else if (deselectLeft)
			queueDeselect(left);
		else if (deselectRight)
			queueDeselect(right);
	}

	// swap the node at {i} with the node on its left,
	// equivalent to swapping the node at {i-1} with the node on its right
	public static void swapLeft(List<Node> list, int i, boolean deselectLeft, boolean deselectRight) {
		swapRight(list, i-1, deselectLeft, deselectRight);
	}

	// shifts everything right of {at} right
	public static void insert(List<Node> list, int from, int at, boolean deselectFrom, boolean deselectAt) {
		if (from == at)
			return;

		Rectangle fr = getRect(list.get(from));
		Rectangle t = getRect(list.get(at));
		select(fr, t);

		list.remove(from);
		if (from < at)
			list.add(at-1, fr);
		else
			list.add(at, fr);

		if (deselectFrom && deselectAt)
			queueDeselect(fr, t);
		else if (deselectFrom)
			queueDeselect(fr);
		else if (deselectAt)
			queueDeselect(t);
	}

	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public static <T extends Labeled> void bindText(T label, NumberExpression prop) {
		label.textProperty().bind(prop.asString());
	}

}
