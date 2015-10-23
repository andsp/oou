package ru.andsp.oou.ui;

import javax.swing.*;
import java.util.ArrayList;
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

    public void initUI(){
        List<Instance> instances = new ArrayList<Instance>();
        instances.add(new Instance("1","2","3","4","5","6"));
        instances.add(new Instance("1","2","3","4","5","6"));
        instances.add(new Instance("1","2","3","4","5","6"));
        instances.add(new Instance("1","2","3","4","5","6"));
        instances.add(new Instance("1","2","3","4","5","6"));
        tblMain.setModel(new InstanceTableModel(instances));
    }


}
