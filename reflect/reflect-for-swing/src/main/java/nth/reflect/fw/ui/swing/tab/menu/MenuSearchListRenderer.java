package nth.reflect.fw.ui.swing.tab.menu;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

import nth.reflect.fw.layer1userinterface.item.Item;
import nth.reflect.fw.ui.swing.icon.IconFactory;
import nth.reflect.fw.ui.swing.style.SwingStyleConstant;

@SuppressWarnings("serial")
public class MenuSearchListRenderer extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);		
		
		if (value instanceof Item) {
			Item item = (Item) value;
			
			setText(item.getText());
			setToolTipText(item.getDescription());
			ImageIcon icon = IconFactory.create(item.getIconURL(), SwingStyleConstant.ICON_SIZE);
			setIcon(icon);
			
		} 
		return this;
	}

}
