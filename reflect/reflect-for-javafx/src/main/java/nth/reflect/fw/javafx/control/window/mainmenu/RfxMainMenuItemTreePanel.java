package nth.reflect.fw.javafx.control.window.mainmenu;

import java.util.Collection;
import java.util.List;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.TreeItem;
import nth.reflect.fw.javafx.RfxUserinterfaceController;
import nth.reflect.fw.javafx.control.itemtreelist.RfxItemTreePanel;
import nth.reflect.fw.javafx.control.style.RfxStyleProperties;
import nth.reflect.fw.javafx.control.tab.Tab;
import nth.reflect.fw.javafx.control.window.RfxWindow;
import nth.reflect.fw.layer1userinterface.UserInterfaceContainer;
import nth.reflect.fw.layer1userinterface.item.Item;
import nth.reflect.fw.layer5provider.language.LanguageProvider;
import nth.reflect.fw.ui.item.method.MethodOwnerItem;
import nth.reflect.fw.ui.item.method.menu.MainMenuItems;
import nth.reflect.fw.ui.style.ReflectColorName;
import nth.reflect.fw.ui.tab.Tabs;

public class RfxMainMenuItemTreePanel extends RfxItemTreePanel {

	private final BooleanBinding windowExtraWideBinding;
	private final BooleanProperty mainMenuVisibleProperty;
	private final Tabs<Tab> tabs;

	/**
	 * TODO can we use RfxItemTreePanel instead of this class????
	 * @param userInterfaceContainer
	 */
	public RfxMainMenuItemTreePanel(UserInterfaceContainer userInterfaceContainer) {
		super(createRootItem(userInterfaceContainer));
		this.tabs = userInterfaceContainer.get(RfxUserinterfaceController.class).getTabs();
		RfxWindow rfxWindow = userInterfaceContainer.get(RfxWindow.class);
		this.windowExtraWideBinding = rfxWindow.getExtraWideBinding();
		this.mainMenuVisibleProperty=rfxWindow.getMainMenuVisibleProperty();
		setStyle(new RfxStyleProperties().setBackground(ReflectColorName.CONTENT.BACKGROUND()).toString());
	}
	
	
	private static TreeItem<Item> createRootItem(UserInterfaceContainer userInterfaceContainer) {
		LanguageProvider languageProvider = userInterfaceContainer.get(LanguageProvider.class);

		TreeItem<Item> rootNode = new TreeItem<>(new Item(languageProvider));
		rootNode.setExpanded(true);

		Collection<MethodOwnerItem> serviceObjectItems = new MainMenuItems(userInterfaceContainer);

		for (Item serviceObjectItem : serviceObjectItems) {
			createAndAppendTreeItemChild(rootNode, serviceObjectItem);
		}

		if (canExpandAllServiceItems(rootNode)) {
			expandAllServiceItems(rootNode);
		}

		return rootNode;
	}


	private static void createAndAppendTreeItemChild(TreeItem<Item> parentTreeItem,
			Item item) {
		TreeItem<Item> treeItem = new TreeItem<>(item);
		parentTreeItem.getChildren().add(treeItem);

		if (item instanceof MethodOwnerItem) {
			MethodOwnerItem methodOwnerItem = (MethodOwnerItem) item;
			List<Item> children = methodOwnerItem.getChildren();
			for (Item child : children) {
				//add children recursively
				createAndAppendTreeItemChild(treeItem, child);
			}
		}
	}

	private static boolean canExpandAllServiceItems(TreeItem<Item> rootNode) {
		int nrOfVisibleItems = 0;
		for (TreeItem<Item> serviceObjectNode : rootNode.getChildren()) {
			if (serviceObjectNode.getValue().isVisible()) {
				nrOfVisibleItems++;
				for (TreeItem<Item> serviceObjectMethodNode : serviceObjectNode.getChildren()) {
					if (serviceObjectMethodNode.getValue().isVisible()) {
						nrOfVisibleItems++;
					}
				}
			}
		}
		return nrOfVisibleItems < 15;
	}

	private static void expandAllServiceItems(TreeItem<Item> rootNode) {
		for (TreeItem<Item> serviceObjectNode : rootNode.getChildren()) {
			if (serviceObjectNode.getValue().isVisible()) {
				serviceObjectNode.setExpanded(true);
			}
		}
	}

	
	/**
	 * overriding method so that the menu closes automatically then the window is narrow.
	 * @param treeItem
	 */
	@Override
	public void onItemAction(TreeItem<Item> treeItem) {
		super.onItemAction(treeItem);
		if (isNarrowWindow() && tabs.size()>0) {
			hideMainMenu();				
		}
	}

	private void hideMainMenu() {
		mainMenuVisibleProperty.set(false);
	}

	private boolean isNarrowWindow() {
		return !windowExtraWideBinding.getValue();
	}

}
