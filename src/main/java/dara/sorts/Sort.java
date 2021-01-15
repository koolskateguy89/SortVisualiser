package dara.sorts;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Label;

public interface Sort {

	public void setOriginalList(List<Node> list);
	
	public String type();

	public void sort();
	
	public void bindAccess(Label accessText);

	public void bindChange(Label changeText);
	
	public void reset();
	
	public void clear();
	
	public void destroy();
	
}
