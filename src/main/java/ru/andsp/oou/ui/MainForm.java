package ru.andsp.oou.ui;

import ru.andsp.oou.service.ProgressCallBack;
import ru.andsp.oou.ui.config.ConfigHelper;

import javax.swing.*;
import java.sql.SQLException;


public class MainForm implements ProgressCallBack {
    private JPanel pnMain;
    private JTable tblMain;
    private JToolBar tbMain;
    private JButton btAdd;
    private JButton btEdit;
    private JButton btRemove;
    private JButton btRun;
    private JScrollPane spMain;
    private JProgressBar progressBar;


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

    private void startLoad(ProgressCallBack progressCallBack) {
        progressBar.setValue(0);
        progressBar.setString(null);
        new Thread(() -> {
            try {
                UploadInstance.start(((InstanceTableModel) tblMain.getModel()).getItem(tblMain.getSelectedRow()), progressCallBack);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void initUI() {
        tblMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refresh();
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
        btRun.addActionListener(e -> this.startLoad(this));
        progressBar.setMinimum(0);
        progressBar.setStringPainted(true);
    }


    @Override
    public void setStartCount(int count) {
        progressBar.setMaximum(count);
    }

    @Override
    public void reportOfFinished(String name) {
        synchronized (this) {
            progressBar.setValue(progressBar.getValue() + 1);
            progressBar.setString(name);
        }
    }
}
