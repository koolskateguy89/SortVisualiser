package sv3.sorts;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Label;

public interface Sort {

	void setOriginalList(List<Node> list);

	String type();

	void sort();

	void bindAccess(Label accessText);

	void bindChange(Label changeText);

	void reset();

	void clear();

	void destroy();

}
