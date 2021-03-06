package nth.reflect.fw.ui.swing.mainwindow;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import nth.reflect.fw.ReflectApplication;
import nth.reflect.fw.layer1userinterface.UserInterfaceContainer;
import nth.reflect.fw.layer5provider.language.LanguageProvider;
import nth.reflect.fw.layer5provider.reflection.ReflectionProvider;
import nth.reflect.fw.layer5provider.reflection.info.classinfo.ClassInfo;
import nth.reflect.fw.ui.swing.UserinterfaceControllerForSwing;
import nth.reflect.fw.ui.swing.component.tabpanel.TabPane;
import nth.reflect.fw.ui.swing.component.toolbar.MaterialAppBar;
import nth.reflect.fw.ui.swing.icon.IconFactory;
import nth.reflect.fw.ui.swing.image.ReflectImage;
import nth.reflect.fw.ui.swing.style.SwingStyleConstant;
import nth.reflect.fw.ui.swing.tab.Tab;
import nth.reflect.fw.ui.swing.tab.menu.MenuList;
import nth.reflect.fw.ui.tab.Tabs;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 7688708437355470674L;
	private JSplitPane splitPanel;
	private JScrollPane menuPanel;
	private TabPane tabPane;
	private JButton menuButton;
	private final UserInterfaceContainer userInterfaceContainer;
	private final LanguageProvider languageProvider;
	private final ReflectionProvider reflectionProvider;

	public MainWindow(UserInterfaceContainer userInterfaceContainer) throws MalformedURLException {
		this.userInterfaceContainer = userInterfaceContainer;
		this.reflectionProvider = userInterfaceContainer.get(ReflectionProvider.class);
		this.languageProvider = userInterfaceContainer.get(LanguageProvider.class);
		// Set style
		try {

			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}
		setDefaultLookAndFeelDecorated(true);

		ReflectApplication application = userInterfaceContainer.get(ReflectApplication.class);
		// Set window parameters
		ClassInfo applicationInfo = reflectionProvider.getClassInfo(application.getClass());

		setTitle(application, applicationInfo);
		setIcon(application, applicationInfo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		UserinterfaceControllerForSwing userInterfaceController = userInterfaceContainer
				.get(UserinterfaceControllerForSwing.class);
		Tabs<Tab> tabs = userInterfaceController.getTabs();
		
		// Create window contents
		menuPanel = createMenuTabPanel();
		// tabPane = createContentTabPanel();
		tabPane = new TabPane(userInterfaceContainer, tabs);
		splitPanel = createSplitPanel(menuPanel, tabPane);
		getContentPane().add(splitPanel, BorderLayout.CENTER);

		menuButton = createMenuButton();
		// JButton aboutButton = createAboutButton();
		// JButton findButton = createFindButton(menuPanel);
		// JButton tabButton = createTabButton(menuPanel);
		// JToolBar toolbar = createToolBar(aboutButton, menuButton,
		// findButton,tabButton);
		JToolBar toolbar = new MaterialAppBar(userInterfaceContainer);
		getContentPane().add(toolbar, BorderLayout.NORTH);

		// add generic keyboard listener
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyboardDispatcher(tabs));

		// Display the window.
		showMenu();
		pack();
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private void setIcon(ReflectApplication application, ClassInfo applicationInfo) {
		try {
			URL iconUrl = applicationInfo.getFontIconUrl(application);// FIXME:
																		// use
																		// applicationInfo.getApplicationIconFile()
			Image image = Toolkit.getDefaultToolkit().getImage(iconUrl);
			setIconImage(image);
		} catch (Exception exception) {
		}
	}

	private void setTitle(ReflectApplication application, ClassInfo applicationInfo) {
		String title = applicationInfo.getDisplayName();
		setTitle(title);
	}

	/**
	 * Creates a button to show or hide the menu.<br>
	 * The MaterialAppBarIcon and tool tip will be set by the
	 * {@link #showMenu()} and {@link MainWindow#hideMenu()} methods
	 * 
	 * @return a button to show or hide the menu
	 */
	private JButton createMenuButton() {
		JButton button = new JButton();
		@SuppressWarnings("serial")
		Action action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isMenuVisible()) {
					hideMenu();
				} else {
					showMenu();
				}
			}
		};
		button.registerKeyboardAction(action, KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		button.setAction(action);
		return button;
	}

	// private JButton createFindButton(final MenuTabPanel menuTabPanel) {
	// JButton button = new JButton();
	// @SuppressWarnings("serial")
	// Action action = new AbstractAction() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// menuTabPanel.getMenuPanel().getSearchField().requestFocus();
	// }
	// };
	// button.setAction(action);
	// button.registerKeyboardAction(action,
	// KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0),
	// JComponent.WHEN_IN_FOCUSED_WINDOW);
	// button.setToolTipText(languageProvider.getText("Find Menu Item (F3)"));
	// try {
	// button.setIcon(IconFactory.create(new IconUriClassResource(
	// ReflectImage.EDIT_FIND).getAbsoluteURI(),
	// SwingStyleConstant.ICON_SIZE));
	// } catch (URISyntaxException e1) {
	// }
	// return button;
	// }
	//
	// private JButton createTabButton(final MenuTabPanel menuTabPanel) {
	// JButton button = new JButton();
	// @SuppressWarnings("serial")
	// Action action = new AbstractAction() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// int selectedTabIndex = getViewContainer().getSelectedIndex();
	// if (selectedTabIndex >= 0) {
	// Component selectedTabHeader = getViewContainer()
	// .getTabComponentAt(selectedTabIndex);
	// if (selectedTabHeader instanceof TabHeader) {
	// TabHeader tabHeader = (TabHeader) selectedTabHeader;
	// tabHeader.showPopupMenu(10, 10);
	// }
	// }
	// }
	// };
	// button.setAction(action);
	// button.registerKeyboardAction(action,
	// KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0),
	// JComponent.WHEN_IN_FOCUSED_WINDOW);
	// button.setToolTipText(languageProvider.getText("Show Tabs Menu (F4)"));
	// button.setIcon(IconFactory.create(ReflectImage.TABS,
	// SwingStyleConstant.ICON_SIZE));
	// return button;
	// }

	private JSplitPane createSplitPanel(Component menuTabPanel, Component contentTabPanel) {
		JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuTabPanel, contentTabPanel);
		splitPanel.setResizeWeight(0.25);
		return splitPanel;
	}

	private JScrollPane createMenuTabPanel() {
		MenuList menuList = new MenuList(userInterfaceContainer);
		JScrollPane menuPanel = new JScrollPane(menuList);
		return menuPanel;
		// return new MenuTabPanel(userInterfaceContainer);
	}

	public void hideMenu() {
		// hide menu tab panel
		menuPanel.setVisible(false);
		// hide divider
		((BasicSplitPaneUI) splitPanel.getUI()).getDivider().setVisible(false);
		// set menu button
		menuButton.setToolTipText(languageProvider.getText("Show Menu (F2)"));
		menuButton.setIcon(IconFactory.create(ReflectImage.MENU_OPENED, SwingStyleConstant.ICON_SIZE));
	}

	public void showMenu() {
		// un-hide menu tab panel
		menuPanel.setVisible(true);
		// hide divider
		((BasicSplitPaneUI) splitPanel.getUI()).getDivider().setVisible(true);
		// hide menu button
		menuButton.setToolTipText(languageProvider.getText("Hide Menu (F2)"));
		menuButton.setIcon(IconFactory.create(ReflectImage.MENU_CLOSED, SwingStyleConstant.ICON_SIZE));
	}

	public boolean isMenuVisible() {
		return menuPanel.isVisible();
	}

	public TabPane getTabContentPane() {
		return tabPane;
	}

	public void setMenuVisible(boolean menuVisible) {
		// TODO drawer style
		menuPanel.setVisible(menuVisible);
	}
}
