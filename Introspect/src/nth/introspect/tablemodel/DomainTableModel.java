package nth.introspect.tablemodel;

import javax.swing.table.TableModel;

public interface DomainTableModel extends TableModel {
	public Object getDomainValue(int rowIndex);
}
