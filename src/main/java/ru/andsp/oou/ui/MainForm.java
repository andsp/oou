package ru.andsp.oou.ui;

import ru.andsp.oou.ui.db.InstanceHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

    private List<Instance> getData() {
        try {
            InstanceHelper helper = new InstanceHelper();
            return helper.getList();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void refresh() {
        try (InstanceHelper helper = new InstanceHelper()) {
            List<Instance> instances = helper.getList();
            tblMain.setModel(new InstanceTableModel(instances));
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        tblMain.setModel(new InstanceTableModel(getData()));
        tblMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PropertyForm.start(null);
                refresh();
            }
        });
        btEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PropertyForm.start(((InstanceTableModel) tblMain.getModel()).getItem(tblMain.getSelectedRow()));
                refresh();
            }
        });
        btRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (InstanceHelper helper = new InstanceHelper()) {
                    Instance instance = ((InstanceTableModel) tblMain.getModel()).getItem(tblMain.getSelectedRow());
                    if (instance != null && !instance.isNew()) {
                        helper.remove(instance.getId());
                    }
                } catch (ClassNotFoundException | SQLException | IOException e1) {
                    e1.printStackTrace();
                }
                refresh();
            }
        });
    }


}
