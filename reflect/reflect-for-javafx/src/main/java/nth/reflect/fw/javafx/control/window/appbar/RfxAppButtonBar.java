package nth.reflect.fw.javafx.control.window.appbar;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;
import javafx.stage.PopupWindow.AnchorLocation;
import nth.reflect.fw.javafx.RfxUserinterfaceController;
import nth.reflect.fw.javafx.control.button.RfxPrimaryButton;
import nth.reflect.fw.javafx.control.fonticon.FontAwesomeIconName;
import nth.reflect.fw.javafx.control.itemtreelist.RfxItemTreeView;
import nth.reflect.fw.javafx.control.style.RfxStyleProperties;
import nth.reflect.fw.javafx.control.view.form.RfxFormView;
import nth.reflect.fw.javafx.control.view.table.RfxTableView;
import nth.reflect.fw.javafx.control.window.RfxWindow;
import nth.reflect.fw.javafx.control.window.content.RfxContentPane;
import nth.reflect.fw.javafx.control.window.mainmenu.RfxMainMenuPane;
import nth.reflect.fw.layer1userinterface.UserInterfaceContainer;
import nth.reflect.fw.layer1userinterface.item.Item;
import nth.reflect.fw.layer1userinterface.view.View;
import nth.reflect.fw.layer1userinterface.view.ViewController;
import nth.reflect.fw.layer5provider.language.LanguageProvider;
import nth.reflect.fw.ui.item.tab.SelectTabItem;
import nth.reflect.fw.ui.style.MaterialColorSetCssName;

/**
 * Button bar as part of the {@link RfxAppBar} which contains the main
 * navigation buttons:
 * <ul>
 * <li>the MainMenu button (see {@link RfxMainMenuPane})</li>
 * <li>the {@link RfxtabHeader}'s that navigate to the content views (e.g. See
 * {@link RfxFormView} and {@link RfxTableView})</li>
 * <li>a tab menu button when not all {@link RfxtabHeader}'s can be displayed on
 * the {@link RfxAppButtonBar}. It shows a list of all {@link RfxtabHeader}'s
 * when clicked)</li>
 * </ul>
 * 
 * @author nilsth
 *
 */

public class RfxAppButtonBar extends Pane {

	private static final int IGNORE_BASE_LINE = 0;
	public static final int BAR_HEIGHT = 42;//must be 38 according to material design but we added a bit because to tab button onderline...
	private final BooleanProperty mainMenuVisibleProperty;
	private final JFXButton mainMenuButton;
	private final ObservableList<View> tabsProperty;
	private final RfxWindow rfxWindow;
	private final BooleanBinding extraWideBinding;
	private final ObjectProperty<View> selectedTabProperty;
	private final JFXButton tabSelectionButton;
	private RfxMainMenuPane mainMenuPane;
	private final UserInterfaceContainer userInterfaceContainer;

	public RfxAppButtonBar(UserInterfaceContainer userInterfaceContainer,
			RfxMainMenuPane mainMenuPane) {
		this.userInterfaceContainer = userInterfaceContainer;
		this.mainMenuPane = mainMenuPane;
		rfxWindow = userInterfaceContainer.get(RfxWindow.class);
		mainMenuVisibleProperty = rfxWindow.getMainMenuVisibleProperty();
		mainMenuVisibleProperty.addListener(this::onMainMenuVisibleChanged);
		extraWideBinding = rfxWindow.getExtraWideBinding();
		tabsProperty = rfxWindow.getTabsProperty();
		tabsProperty.addListener(this::onTabsChanged);
		selectedTabProperty = rfxWindow.getSelectedTabProperty();
		selectedTabProperty.addListener(this::onSelectedTabChanged);

		mainMenuButton = createMainMenuButtton();
		getChildren().add(mainMenuButton);

		tabSelectionButton = createTabSelectionButton();
		getChildren().add(tabSelectionButton);

		setMaxHeight(BAR_HEIGHT);
		// setPadding(new Insets(1));
		// HBox menuBar = createMenuBar();
		// setLeft(menuBar);
		//
		// setCenter(tabHeaderBar);
		//
		// HBox rightButtonPane = new HBox();
		// rightButtonPane.setMaxHeight(BAR_HEIGHT);
		// setRight(rightButtonPane);
		// RfxContentButton tabSelectionButton = createTabSelectionButton();
		// rightButtonPane.getChildren().add(tabSelectionButton);
		// RfxContentButton tabMenuButton = createTabMenuButton();
		// rightButtonPane.getChildren().add(tabMenuButton);
	}

	public void onMainMenuVisibleChanged(ObservableValue<? extends Boolean> observable,
			Boolean oldValue, Boolean newValue) {
		requestLayout();
	}

	public void onTabsChanged(Change<? extends View> change) {
		if (tabsProperty.size() == 0) {
			selectedTabProperty.set(null);
			updatetabHeaders();
		} else if (!change.getList().contains(selectedTabProperty.get())) {
			// TODO selectNewTab(change);
		} else {
			updatetabHeaders();
		}
	}

	public void onSelectedTabChanged(ObservableValue<? extends View> observable, View oldValue,
			View newValue) {
		requestLayout();
	}

	public void updatetabHeaders() {
		List<RfxTabHeader> tabHeader = getTabHeaders();
		addNewtabHeaders(tabHeader);
		removeOldtabHeaders(tabHeader);
		layout();
	}

	private List<RfxTabHeader> getTabHeaders() {
		List<RfxTabHeader> tabHeaders = new ArrayList<>();
		for (Node child : getChildren()) {
			if (child instanceof RfxTabHeader) {
				RfxTabHeader tabHeader = (RfxTabHeader) child;
				tabHeaders.add(tabHeader);
			}
		}
		return tabHeaders;
	}

	private List<RfxTabHeader> getTabHeader() {
		List<RfxTabHeader> tabHeaders = new ArrayList<>();
		for (Node child : getChildren()) {
			if (child instanceof RfxTabHeader) {
				RfxTabHeader tabHeader = (RfxTabHeader) child;
				tabHeaders.add(tabHeader);
			}
		}
		return tabHeaders;
	}

	
	private void addNewtabHeaders(List<RfxTabHeader> tabHeaders) {
		List<View> tabs = new ArrayList<>();
		for (RfxTabHeader tabHeader : tabHeaders) {
			tabs.add(tabHeader.getTab());
		}
		for (View tab : tabsProperty) {
			if (!tabs.contains(tab)) {
				RfxTabHeader tabHeader = new RfxTabHeader(rfxWindow, tab);
				getChildren().add(tabHeader);
			}
		}
	}

	private void removeOldtabHeaders(List<RfxTabHeader> tabHeaders) {
		for (RfxTabHeader tabHeader : tabHeaders) {
			if (!tabsProperty.contains(tabHeader.getTab())) {
				getChildren().remove(tabHeader);
			}
		}
	}

	/**
	 * Custom layout: to position its children:
	 * <ul>
	 * <li>The bar starts with a main menu button to slide the
	 * {@link RfxMainMenuPane} in and out (show or hide)</li>
	 * <li>The {@link RfxtabHeader}s:</li>
	 * <ul>
	 * <li>The number of displayed {@link RfxtabHeader}s depend on the available
	 * space.</li>
	 * <li>A tab selection button is displayed on the right of the bar when not
	 * all {@link RfxtabHeader}s fit on the bar. It will open a pop-up menu to
	 * select a tab from a list.</li>
	 * <li>The {@link RfxtabHeader}s indent to the right (above the
	 * {@link RfxContentPane} when the {@link RfxMainMenuPane} is continuously
	 * visible and the {@link RfxWindow#getExtraWideBinding()}==True
	 * ({@link RfxContentPane} next to {@link RfxMainMenuPane})</li>
	 * </ul>
	 * </ul>
	 * <br>
	 * This method is also called when then {@link #mainMenuPane} slides in or
	 * out (see
	 * {@link RfxWindow#onMenuMovingLeftOrRight(ObservableValue, Number, Number)})
	 */
	@Override
	protected void layoutChildren() {
		double width = getWidth();
		boolean snapToPixel = isSnapToPixel();
		double x = 0;
		double y = 0;

		mainMenuButton.autosize();
		double mainMenuButtonWidth = mainMenuButton.getWidth();
		double mainMenuButtonHeight = mainMenuButton.getHeight();
		positionInArea(mainMenuButton, x, y, mainMenuButtonWidth, mainMenuButtonHeight,
				IGNORE_BASE_LINE, Insets.EMPTY, HPos.LEFT, VPos.TOP, snapToPixel);

		double menuIndent = RfxWindow.MENU_WIDTH + mainMenuPane.getTranslateX();
		if (extraWideBinding.get() && menuIndent > mainMenuButton.getWidth()) {
			x = menuIndent;
		} else {
			x = mainMenuButton.getWidth();
		}

		List<RfxTabHeader> tabHeaders = getTabHeader();
		resizeTabHeaders(tabHeaders);

		tabSelectionButton.autosize();
		double tabSelectionButtonWidth = tabSelectionButton.getWidth();
		double tabSelectionButtonHeight = tabSelectionButton.getHeight();

		double availableWidth = width - x - tabSelectionButtonWidth;
		// minus tab selection button width?
		
		List<RfxTabHeader> visibletabHeaders = getVisibleTabHeaders(tabHeaders, availableWidth);

		tabSelectionButton.setVisible(visibletabHeaders.size() < tabHeaders.size());
		positionInArea(tabSelectionButton, width - tabSelectionButtonWidth, y,
				tabSelectionButtonWidth, tabSelectionButtonHeight, IGNORE_BASE_LINE, Insets.EMPTY,
				HPos.LEFT, VPos.TOP, snapToPixel);

		for (RfxTabHeader tabHeader : tabHeaders) {
			if (visibletabHeaders.contains(tabHeader)) {
				tabHeader.setVisible(true);
				double tabHeaderWidth = tabHeader.getWidth();
				double tabHeaderHeight = tabHeader.getHeight();
				double remainingWidth = width - x - tabHeaderWidth;
				if (tabSelectionButton.isVisible()) {
					remainingWidth -= tabSelectionButtonWidth;
				}
				if (remainingWidth < 0) {
					tabHeaderWidth = width - x;
					if (tabSelectionButton.isVisible()) {
						tabHeaderWidth = tabHeaderWidth - tabSelectionButtonWidth;
					}
				}
				tabHeader.resize(tabHeaderWidth, tabHeaderHeight);
				positionInArea(tabHeader, x, y, tabHeaderWidth, tabHeaderHeight, IGNORE_BASE_LINE,
						Insets.EMPTY, HPos.LEFT, VPos.TOP, snapToPixel);
				
				x += tabHeaderWidth;
			} else {
				tabHeader.setVisible(false);
			}
		}

	}


	private List<RfxTabHeader> getVisibleTabHeaders(List<RfxTabHeader> tabHeaders,
			double availableWidth) {
		List<RfxTabHeader> visibleTabHeaders = new ArrayList<>(tabHeaders);
		int selectedTabHeaderIndex = 0;
		RfxTabHeader selectedTabHeader = getTabHeader(selectedTabProperty.get());
		if (selectedTabHeader != null) {
			selectedTabHeaderIndex = visibleTabHeaders.indexOf(selectedTabHeader);
		}

		double visibleTabHeaderWidth = gettabHeadersWidth(visibleTabHeaders);

		while (visibleTabHeaderWidth > availableWidth && visibleTabHeaders.size() > 1) {
			int nrOfTabHeadersBeforeSelected = selectedTabHeaderIndex;
			int nrOfTabHeadersAfterSelected = visibleTabHeaders.size() - selectedTabHeaderIndex - 1;
			boolean hasButtonsToRemoveBeforeSelected = nrOfTabHeadersBeforeSelected > 0;
			boolean hasButtonsToRemoveAfterSelected = nrOfTabHeadersAfterSelected > 0;
			boolean removeBefore = hasButtonsToRemoveBeforeSelected
					&& ((nrOfTabHeadersBeforeSelected > nrOfTabHeadersAfterSelected)
							|| !hasButtonsToRemoveAfterSelected);
			if (removeBefore) {
				RfxTabHeader firstVisibleTabHeader = visibleTabHeaders.get(0);
				visibleTabHeaderWidth -= firstVisibleTabHeader.getWidth();
				visibleTabHeaders.remove(firstVisibleTabHeader);
			} else {
				RfxTabHeader lastVisibleTabHeader = visibleTabHeaders
						.get(visibleTabHeaders.size() - 1);
				visibleTabHeaderWidth -= lastVisibleTabHeader.getWidth();
				visibleTabHeaders.remove(lastVisibleTabHeader);
			}
		}
		return visibleTabHeaders;

	}

	private double gettabHeadersWidth(List<RfxTabHeader> visibletabHeaders) {
		double totaltabHeadersWidth = 0;
		for (RfxTabHeader tabHeader : visibletabHeaders) {
			totaltabHeadersWidth += tabHeader.getWidth();
		}
		return totaltabHeadersWidth;
	}

	private void resizeTabHeaders(List<RfxTabHeader> tabHeaders) {
		for (RfxTabHeader tabHeader : tabHeaders) {
			tabHeader.autosize(); 
		}
	}

	private RfxTabHeader getTabHeader(View tabToFind) {
		List<RfxTabHeader> tabHeaders = getTabHeader();
		for (RfxTabHeader tabHeader : tabHeaders) {
			if (tabHeader.getTab().equals(tabToFind)) {
				return tabHeader; // TODO create tabHeadersAndTabs HashMap
			}
		}
		return null;
	}

//	private RfxPrimaryButton createTabMenuButton() {
//		RfxPrimaryButton tabMenuButton = new RfxPrimaryButton(FontAwesomeIconName.ELLIPSIS_V);
//		// tabMenuButton.setOnAction(this::onMenuButtonAction);
//		return tabMenuButton;
//	}

	private JFXButton createTabSelectionButton() {
		RfxPrimaryButton tabSelectionButton = new RfxPrimaryButton(FontAwesomeIconName.FILES_ALT);
		// TODO use de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName.CLONE
		// when supported by newer version of jfoenix (fontawesomefx8.9)
		tabSelectionButton.setOnAction(this::onTabSelectionButtonAction);
		return tabSelectionButton;
	}

	public void onTabSelectionButtonAction(ActionEvent actionEvent) {
		showSelectTabPopUp();
	}

	private void showSelectTabPopUp() {
		LanguageProvider languageProvider = userInterfaceContainer.get(LanguageProvider.class);
		RfxUserinterfaceController userInterfaceController = userInterfaceContainer
				.get(RfxUserinterfaceController.class);
		ViewController<View> viewContainer = userInterfaceController.getViewController();

		TreeItem<Item> rootNode = new TreeItem<>(new Item(languageProvider));
		rootNode.setExpanded(true);
		RfxItemTreeView itemTreeView = new RfxItemTreeView(rootNode);

		for (int i = 0; i < viewContainer.getViewCount(); i++) {
			View view = viewContainer.getView(i);
			SelectTabItem selectTabItem = new SelectTabItem(languageProvider, viewContainer, view);
			TreeItem<Item> selectTabNode = new TreeItem<>(selectTabItem);
			rootNode.getChildren().add(selectTabNode);
		}
		;

		JFXPopup popup = new JFXPopup();
		popup.setPopupContent(itemTreeView);
		popup.setAnchorLocation(AnchorLocation.CONTENT_TOP_RIGHT);
		popup.show(tabSelectionButton);
	}

	private JFXButton createMainMenuButtton() {
		// TODO find out why this does not work:
		// RfxPrimaryButton mainMenuButton = new
		// RfxPrimaryButton(FontAwesomeIconName.BARS);
		// mainMenuButton.setOnAction(this::onMainMenuButton);
		// return mainMenuButton;

		JFXButton button = new JFXButton();
		FontAwesomeIcon icon = new FontAwesomeIcon();
		icon.setIcon(de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName.BARS);
		icon.setSize("17px");
		String iconStyle = icon.getStyle() + ";" + new RfxStyleProperties()
				.setFill(MaterialColorSetCssName.PRIMARY.FOREGROUND1()).toString();
		icon.setStyle(iconStyle);

		// RfxFontIcon icon=new RfxFontIcon(FontAwesomeIconName.BARS, 16,
		// Color.WHITE);
		button.setGraphic(icon);
		button.setPadding(new Insets(8, 16, 8, 17));
		// button.getStyleClass().add("button-flat");
		button.setOnAction(this::onMainMenuButton);
		return button;

	}

	private void onMainMenuButton(ActionEvent event) {
		mainMenuVisibleProperty.set(!mainMenuVisibleProperty.get());
	}

}
