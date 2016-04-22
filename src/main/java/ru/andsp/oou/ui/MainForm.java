package ru.andsp.oou.ui;

import ru.andsp.oou.ui.config.ConfigHelper;

import javax.swing.*;
import java.sql.SQLException;


public class MainForm {
    private JPanel pnMain;
    private JTable tblMain;
    private JToolBar tbMain;
    private JButton btAdd;
    private JButton btEdit;
    private JButton btRemove;
    private JButton btRun;
    private JScrollPane spMain;


    public static void start() {
        JFrame frame = new JFrame("MainForm");
        MainForm form = new MainForm();
        frame.setContentPane(form.pnMain);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        form.initUI();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void refresh() {
        tblMain.setModel(new InstanceTableModel(ConfigHelper.getInstance().getInstances()));
    }

    private void initUI() {
        tblMain.setModel(new InstanceTableModel(ConfigHelper.getInstance().getInstances()));
        tblMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        btAdd.addActionListener(e -> {
            PropertyForm.start(null);
            refresh();
        });
        btEdit.addActionListener(e -> {
            PropertyForm.start(((InstanceTableModel) tblMain.getModel()).getItem(tblMain.getSelectedRow()));
            refresh();
        });
        btRemove.addActionListener(e -> {
            Instance instance = ((InstanceTableModel) tblMain.getModel()).getItem(tblMain.getSelectedRow());
            if (instance != null && !instance.isNew()) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Remove row ?", "Confirm", dialogButton);
                if (dialogResult == 0) {
                    ConfigHelper.getInstance().remove(instance);
                    refresh();
                }
            }
        });
        btRun.addActionListener(e -> {
            try {
                UploadInstance.start(((InstanceTableModel) tblMain.getModel()).getItem(tblMain.getSelectedRow()));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }


}
