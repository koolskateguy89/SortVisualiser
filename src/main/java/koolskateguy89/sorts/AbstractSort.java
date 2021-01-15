package koolskateguy89.sorts;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.NumberExpression;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.shape.Rectangle;

import koolskateguy89.utils.Utils;

abstract class AbstractSort implements Sort {
	
	protected List<Node> list;
	protected List<Node> copy;
	
	protected AbstractSort(List<Node> list) {
		this.list = list;
		setOriginalList(list);
	}
	
	// Copy the original list ABOUT to be sorted
	public void setOriginalList(List<Node> l) {
		// an unmodifiable copy needs to be created to keep integrity
		this.copy = List.copyOf(l);
	}
	
	@Override
	public void reset() {
		list.clear();
		list.addAll(copy);
	}
	
	protected double getHeight(int i) {
		return getRect(i).getHeight();
	}
	
	protected Rectangle getRect(int i) {
		return Utils.getRect(list.get(i));
	}
	
	protected static <T extends Labeled> void bindText(T text, NumberExpression prop) {
		Utils.bindText(text, prop);
		addBinding(text);
	}
	
	protected static void waitForRunLater() {
		try {
			Utils.waitForRunLater();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static List<Labeled> bound = new ArrayList<>();
	
	private static <T extends Labeled> void addBinding(T bounded) {
		bound.add(bounded);
	}
	
	@Override
	public void destroy() {
		clear();
		bound.forEach(l -> l.textProperty().unbind());
		bound.clear();
		list = null;
		copy = null;
	}

}
