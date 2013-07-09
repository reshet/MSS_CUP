package base_SP_Management;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
public class SP_ListElementEditor extends JTextField
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SP_ListElementData data;
	public SP_ListElementEditor(String name, String ID, SP_ListElementData d) {
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
				data.setName(SP_ListElementEditor.this.getText());
			}
		});
	}
}
