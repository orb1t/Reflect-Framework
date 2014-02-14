package nth.introsepect.ui.swing.item.menu;

import java.awt.event.ActionEvent;
import java.net.URI;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

import nth.introsepect.ui.swing.icon.IconFactory;
import nth.introsepect.ui.swing.style.SwingStyleConstant;
import nth.introspect.ui.item.Item;

/**
 * {@link MenuItem} is an adapter for a {@link JMenuItem} to work with {@link Item}
 * 
 * @author Nils ten Hoeve
 * 
 */
public class MenuItem extends JMenuItem {

	private static final long serialVersionUID = -8312443239565563470L;
	private final Item item;

	public MenuItem(Item item) {
		this.item = item;
		setAction(createAction(item));
		setAccelerator(item.getHotKey());
		revalidate();
		repaint();
	}

	private Action createAction(Item item2) {
		return new AbstractAction() {
			private static final long serialVersionUID = -1400371179739795614L;

			@Override
			public void actionPerformed(ActionEvent e) {
				nth.introspect.ui.item.Item.Action action = item.getAction();
				if (action != null) {
					action.run();
				}
			}
			
			@Override
			public boolean isEnabled() {
				return item.isEnabled();
			}
		};
	}

	@Override
	public boolean isVisible() {
		return item.isVisible();
	}

	@Override
	public boolean isEnabled() {
		if (item == null) {
			return super.isEnabled();
		} else {
			return item.isEnabled();
		}
	}

	@Override
	public String getText() {
		if (item == null) {
			return super.getText();
		} else {
			String text = item.getText();
			setMnemonic(getKeyEvent(text));
			return text;
		}

	}

	private char getKeyEvent(String text) {
		if (text != null && text.trim().length() > 0) {
			char ch = Character.toUpperCase(text.charAt(0));
			if (Character.isLetter(ch)) {
				return ch;
			}
		}
		return '\0';
	}

	@Override
	public String getToolTipText() {
		return item.getDescription();
	}

	@Override
	public Icon getIcon() {
		URI iconUri = item.getIconURI();
		return IconFactory.create(iconUri,SwingStyleConstant.ICON_SIZE);
	}

}
