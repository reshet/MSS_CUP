package app_service_quiz;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class ListGroupsElementEditor extends JTextField
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ListGroupsElementData data;
	public ListGroupsElementEditor(String name, String ID, ListGroupsElementData d) {
		this.setText(name);
		this.data = d;
		this.setEditable(true);
		this.setOpaque(true);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				//ListGroupsElementEditor.this.setBackground(Color.BLUE);
			}
		});
		addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				data.setName(ListGroupsElementEditor.this.getText());
			}
		});
	}
}
