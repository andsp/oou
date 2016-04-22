package ru.andsp.oou.ui;


import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class InstanceTableModel implements TableModel {

    private List<Instance> items;

    public InstanceTableModel(HashMap<String, Instance> data) {
        this.items = new ArrayList<>();
        if (data != null) {
            data.forEach((k, v) -> items.add(v));
        }
    }


    public int getRowCount() {
        return items.size();
    }

    public int getColumnCount() {
        return 5;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: {
                return "Host";
            }
            case 1: {
                return "Port";
            }
            case 2: {
                return "User";
            }
            case 3: {
                return "Db";
            }
            case 4: {
                return "Directory result";
            }
            default:
                return null;
        }
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Instance instance = items.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                return instance.getHost();
            }
            case 1: {
                return instance.getPort();
            }
            case 2: {
                return instance.getUser();
            }
            case 3: {
                return instance.getDb();
            }
            case 4: {
                return instance.getPath();
            }
            default:
                return null;
        }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    public void addTableModelListener(TableModelListener l) {

    }

    public void removeTableModelListener(TableModelListener l) {

    }

    public Instance getItem(int rowIndex) {
        return rowIndex < items.size() & rowIndex >= 0 ? items.get(rowIndex) : null;
    }
}
