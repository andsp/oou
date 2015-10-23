package ru.andsp.oou.ui;

import ru.andsp.oou.ui.db.InstanceHelper;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;


public class MainForm {
    private JPanel pnMain;
    private JTable tblMain;
    private JToolBar tbMain;
    private JButton btAdd;
    private JButton btEdit;
    private JButton btRemove;
    private JButton btRun;


    public static void start() {
        JFrame frame = new JFrame("MainForm");
        MainForm form = new MainForm();
        frame.setContentPane(form.pnMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.initUI();
        frame.pack();
        frame.setVisible(true);
    }

    private List<Instance> getData() {
        try {
            InstanceHelper helper = new InstanceHelper();
            return helper.getList();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initUI() {
        tblMain.setModel(new InstanceTableModel(getData()));
    }


}
