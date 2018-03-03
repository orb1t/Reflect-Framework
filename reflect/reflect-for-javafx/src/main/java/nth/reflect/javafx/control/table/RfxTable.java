package nth.reflect.javafx.control.table;

import java.text.Format;
import java.util.List;
import java.util.Optional;

import javax.swing.JPopupMenu;

import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.sun.javafx.collections.ObservableListWrapper;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.PopupWindow.AnchorLocation;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import nth.introspect.generic.util.TypeUtil;
import nth.introspect.layer1userinterface.UserInterfaceContainer;
import nth.introspect.layer1userinterface.controller.UserInterfaceController;
import nth.introspect.layer1userinterface.item.Item;
import nth.introspect.layer5provider.language.LanguageProvider;
import nth.introspect.layer5provider.reflection.ReflectionProvider;
import nth.introspect.layer5provider.reflection.behavior.format.impl.JavaFormatFactory;
import nth.introspect.layer5provider.reflection.info.actionmethod.ActionMethod;
import nth.introspect.layer5provider.reflection.info.actionmethod.ActionMethodInfo;
import nth.introspect.layer5provider.reflection.info.classinfo.ClassInfo;
import nth.introspect.layer5provider.reflection.info.property.PropertyInfo;
import nth.introspect.ui.item.ItemFactory;
import nth.introspect.ui.item.method.MethodOwnerItem;
import nth.introspect.ui.style.MaterialColorSetCssName;
import nth.introspect.ui.style.MaterialFont;
import nth.reflect.javafx.RfxUtils;
import nth.reflect.javafx.control.itemtreelist.RfxItemTreeCell;
import nth.reflect.javafx.control.itemtreelist.RfxItemTreeView;
import nth.reflect.javafx.control.popup.RfxPopup;
import nth.reflect.javafx.control.style.RfxStyleSelector;
import nth.reflect.javafx.control.style.RfxStyleSheet;
import nth.reflect.javafx.control.view.table.RfxTableView;

public class RfxTable extends TableView<Object> {

	// ROW_HEIGHT: Material design says 48 but we use same height as menu items
	private static final int ROW_HEIGHT = RfxItemTreeCell.ITEM_HEIGHT;
	private static final int ROW_FONT_SIZE = 14;
	private static final int HEADER_FONT_SIZE = 13;
	private UserInterfaceContainer userInterfaceContainer;
	private LanguageProvider languageProvider;
	private final RfxPopup popupMenu;
	private nth.introspect.ui.view.TableView tableView;

	public RfxTable() {
		// TODO new RfxVerticalFlingScroller(this);
		addStyleClass();
		popupMenu = new RfxPopup();
	}

	/**
	 * Constructor for creating a table that shows a {@link ActionMethod} result
	 * 
	 * @param reflectionProvider
	 * @param languageProvider
	 * @param methodOwner
	 * @param actionMethodInfo
	 * @param methodParameterValue
	 * @param tableView
	 */
	public RfxTable(RfxTableView tableView) {
		this();
		this.tableView = tableView;
		userInterfaceContainer = tableView.getuserInterfaceContainer();
		ReflectionProvider reflectionProvider = userInterfaceContainer
				.get(ReflectionProvider.class);
		languageProvider = userInterfaceContainer.get(LanguageProvider.class);

		ActionMethodInfo actionMethodInfo = tableView.getMethodInfo();

		setOnMouseClicked(this::onMouseClicked);

		Class<?> objectClass = actionMethodInfo.getGenericReturnType();

		// TODO arrays and other collections than List
		if (TypeUtil.isJavaType(objectClass) || TypeUtil.isEnum(objectClass)) {
			TableColumn<Object, String> singeColumn = new TableColumn("");
			singeColumn.setCellValueFactory(
					createCellValueFactoryForJavaTypeOrEnum(languageProvider, objectClass));
			hideHeader();
		} else {
			setItems(createObservableList(tableView));
			setContextMenu(createTestContextMenu());
			createColumns(reflectionProvider, objectClass);
			ColumnAutoSizer.autoFitTable(this);
		}

	}

	/**
	 * TODO replace with {@link JFXPopup}
	 * 
	 * @return
	 */
	private ContextMenu createTestContextMenu() {

		final ContextMenu contextMenu = new ContextMenu();
		contextMenu.setOnShowing(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent e) {
				System.out.println("showing");
			}
		});
		contextMenu.setOnShown(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent e) {
				System.out.println("shown");
			}
		});

		MenuItem item1 = new MenuItem("About");
		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("About");
			}
		});
		MenuItem item2 = new MenuItem("Preferences");
		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Preferences");
			}
		});
		contextMenu.getItems().addAll(item1, item2);
		return contextMenu;
	}

	public void onMouseClicked(javafx.scene.input.MouseEvent event) {
//			TableViewSelectionModel selectionModel = getSelectionModel();
//			ObservableList selectedCells = selectionModel.getSelectedCells();
//			TablePosition tablePosition = (TablePosition) selectedCells.get(0);
//			TableColumn tableColumn = tablePosition.getTableColumn();
//			TableRow<?> row = getRow(tablePosition.getRow());
			onRowClicked(event.getScreenX(),event.getScreenY());
	}

	public void onRowClicked(double x, double y) {
		// TODO add handler for keyboard for escape, enter and space (see main menu) that calls this method
		
//		double y = row.getBoundsInParent().getMinY();
//		double height = getHeight();
//		boolean showBelowNode = (y>(height/2));
//		
//		PopupVPosition vPosition = showBelowNode ?PopupVPosition.TOP: PopupVPosition.BOTTOM;
//		AnchorLocation anchorLocation=showBelowNode ?AnchorLocation.CONTENT_BOTTOM_LEFT: AnchorLocation.CONTENT_TOP_LEFT;
//		System.out.println(showBelowNode+":"+anchorLocation);
		
//		double x = row.getBoundsInParent().getMinX();
//		double y = row.getBoundsInParent().getMinY();
		
		showRowPopupMenu(x, y);
//		JFXPopup rowMenu = createRowMenu(row);
//		rowMenu.setAnchorLocation(anchorLocation);
//		 rowMenu.show(row, vPosition, PopupHPosition.RIGHT);
	}

	private void showRowPopupMenu(double x, double y) {
		popupMenu.getContent().clear();
		popupMenu.getContent().add(createRowMenu());
		popupMenu.show(this, x, y);
	}

	private RfxItemTreeView createRowMenu() {
		List<MethodOwnerItem> serviceObjectItems = createRowMenuItems();
		RfxItemTreeView rowMenuContent = new RfxItemTreeView(serviceObjectItems, languageProvider);
		return rowMenuContent;
	}

	private List<MethodOwnerItem> createRowMenuItems() {
		//TODO hide pop up menu when there are no visible items
		Object selectedObject = getSelectionModel().getSelectedItem();
		 List<MethodOwnerItem> items = ItemFactory.createTableViewRowMenuItems(tableView, selectedObject);
		return items;
	}

	/**
	 * @param row
	 *            Index of the row
	 * @return the corresponding row
	 */
	public TableRow<?> getRow(int row) {
		List<Node> current = getChildrenUnmodifiable();
		while (current.size() == 1) {
			current = ((Parent) current.get(0)).getChildrenUnmodifiable();
		}

		current = ((Parent) current.get(1)).getChildrenUnmodifiable();
		while (!(current.get(0) instanceof TableRow)) {
			current = ((Parent) current.get(0)).getChildrenUnmodifiable();
		}

		Node node = current.get(row);
		if (node instanceof TableRow) {
			return (TableRow<?>) node;
		} else {
			throw new RuntimeException("Expected Group with only TableRows as children");
		}
	}

	// private void showRowMenuItems() {
	// LanguageProvider languageProvider =
	// userInterfaceContainer.get(LanguageProvider.class);
	//
	// TreeItem<Item> rootNode = new TreeItem<>(new Item(languageProvider));
	// rootNode.setExpanded(true);
	// RfxItemTreeView itemTreeView = new RfxItemTreeView(rootNode);
	//
	// for (Item rowMenuItem : rowMenuItems) {
	// TreeItem<Item> rowMenuNode = new TreeItem<>(rowMenuItem);
	// rootNode.getChildren().add(rowMenuNode);
	// };
	//
	// JFXPopup popup = new JFXPopup();
	// popup.setPopupContent(itemTreeView);
	// popup.setAnchorLocation(AnchorLocation.CONTENT_TOP_RIGHT);
	// popup.show(getSe);
	// }

	private void createColumns(ReflectionProvider reflectionProvider, Class<?> objectClass) {
		ClassInfo classInfo = reflectionProvider.getClassInfo(objectClass);
		List<PropertyInfo> propertyInfos = classInfo.getPropertyInfosSortedAnsVisibleInTable();
		for (PropertyInfo propertyInfo : propertyInfos) {
			TableColumn propertyColumn = new TableColumn(propertyInfo.getDisplayName());
			propertyColumn.setMinWidth(100);
			propertyColumn
					.setCellValueFactory(new PropertyValueFactory<>(propertyInfo.getSimpleName()));
			getColumns().add(propertyColumn);
		}
	}

	private Callback<CellDataFeatures<Object, String>, ObservableValue<String>> createCellValueFactoryForJavaTypeOrEnum(
			LanguageProvider languageProvider, Class<?> objectClass) {
		JavaFormatFactory formatFactory = new JavaFormatFactory(languageProvider);
		Format format = formatFactory.create(objectClass);
		return new Callback<CellDataFeatures<Object, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Object, String> param) {
				String value = format.format(param);
				return new ReadOnlyObjectWrapper<String>(value);
			}
		};
	}

	private ObservableList<Object> createObservableList(RfxTableView tableView) {
		try {
			Object methodOwner = tableView.getMethodOwner();
			ActionMethodInfo actionMethodInfo = tableView.getMethodInfo();
			Object methodParameterValue = tableView.getMethodParameter();
			Object result = actionMethodInfo.invoke(methodOwner, methodParameterValue);
			List<Object> list = (List<Object>) result;
			// TODO create a createObservableList for all types, and that can be
			// updated when needed
			return new ObservableListWrapper<Object>(list);
		} catch (Exception e) {
			UserInterfaceController userInterfaceController = tableView.getuserInterfaceContainer()
					.get(UserInterfaceController.class);
			userInterfaceController.showErrorDialog(tableView.getViewTitle(),
					"Error getting table values.", e);
			return null;
		}
	}

	private void hideHeader() {
		Pane header = (Pane) lookup("TableHeaderRow");
		header.setVisible(false);
		setLayoutY(-header.getHeight());
		autosize();
	}

	protected void addStyleClass() {
		getStyleClass().add(RfxStyleSheet.createStyleClassName(RfxTable.class));
	}

	public static void appendStyleGroups(RfxStyleSheet styleSheet) {
		styleSheet.addStyleGroup(RfxStyleSelector.createFor(RfxTable.class)).getProperties()
				.setFont(MaterialFont.getRobotoRegular(ROW_FONT_SIZE))
				// remove focus border
				.setBackground(MaterialColorSetCssName.CONTENT.BACKGROUND());
		styleSheet
				.addStyleGroup(
						RfxStyleSelector.createFor(RfxTable.class).appendChild("column-header"))
				.getProperties().setBackground(MaterialColorSetCssName.CONTENT.BACKGROUND())
				.setBorderColor(MaterialColorSetCssName.CONTENT.TRANSPARENT(),
						MaterialColorSetCssName.CONTENT.TRANSPARENT(),
						MaterialColorSetCssName.CONTENT.BACKGROUND_HIGHLIGHTED(),
						MaterialColorSetCssName.CONTENT.TRANSPARENT())
				.setSize(ROW_HEIGHT);
		styleSheet
				.addStyleGroup(RfxStyleSelector.createFor(RfxTable.class)
						.appendChild("column-header-background"))
				.getProperties()
				// hide vertical line in header
				.setBackground(MaterialColorSetCssName.CONTENT.BACKGROUND());
		styleSheet
				.addStyleGroup(RfxStyleSelector.createFor(RfxTable.class)
						.appendChild("column-header-background").appendChild("filler"))
				.getProperties().setBackground(MaterialColorSetCssName.CONTENT.BACKGROUND())
				.setBorderColor(MaterialColorSetCssName.CONTENT.TRANSPARENT(),
						MaterialColorSetCssName.CONTENT.TRANSPARENT(),
						MaterialColorSetCssName.CONTENT.BACKGROUND_HIGHLIGHTED(),
						MaterialColorSetCssName.CONTENT.TRANSPARENT());
		styleSheet
				.addStyleGroup(RfxStyleSelector.createFor(RfxTable.class)
						.appendChild("column-header").appendChild(Label.class))
				.getProperties().setFont(MaterialFont.getRobotoMedium(HEADER_FONT_SIZE))
				.setTextFill(MaterialColorSetCssName.CONTENT.FOREGROUND2())
				.setFontWeight(FontWeight.NORMAL)
				.setProperty("-fx-alignment", "CENTER-LEFT");
		styleSheet.addStyleGroup(RfxStyleSelector.createFor(".table-column")).getProperties()
				.setBorderColor("transparent").setProperty("-fx-alignment", "CENTER-LEFT");
		styleSheet.addStyleGroup(RfxStyleSelector.createFor(".table-row-cell")).getProperties()
				.setBackground(MaterialColorSetCssName.CONTENT.BACKGROUND())
				.setTextFill(MaterialColorSetCssName.CONTENT.FOREGROUND1())
				.setBorderColor(MaterialColorSetCssName.CONTENT.TRANSPARENT(),
						MaterialColorSetCssName.CONTENT.TRANSPARENT(),
						MaterialColorSetCssName.CONTENT.BACKGROUND_HIGHLIGHTED(),
						MaterialColorSetCssName.CONTENT.TRANSPARENT())
				.setCellSize(ROW_HEIGHT);
		styleSheet.addStyleGroup(RfxStyleSelector.createFor(".table-row-cell").appendFocused())
				.getProperties().setBackground(MaterialColorSetCssName.CONTENT.FOREGROUND3())
				.setTextFill(MaterialColorSetCssName.CONTENT.FOREGROUND1())
				.setBorderColor(MaterialColorSetCssName.CONTENT.TRANSPARENT(),
						MaterialColorSetCssName.CONTENT.TRANSPARENT(),
						MaterialColorSetCssName.CONTENT.BACKGROUND_HIGHLIGHTED(),
						MaterialColorSetCssName.CONTENT.TRANSPARENT());
	}
}