import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Displayer  extends JFrame {
    private JScrollPane jsp;
    private JScrollPane scrollPaneTable;
    private JDBCConnector connector=new JDBCConnector();
    private JTable tableDevs=new JTable();
    private JLabel jlFile=new JLabel("Field for the data");
    private JLabel jlName=new JLabel("Field for the company");
    private JLabel jlCode=new JLabel("Field for the code");
    private JLabel jlIns=new JLabel("Field for the data");

    private JButton jbId=new JButton("Enter id");
    private JButton jbName=new JButton("Enter");
    private JButton jbcode=new JButton("Enter code");
    private JButton jbInsert=new JButton("Enter");

    private JTextField txtId=new JTextField();
    private JTextField txtName=new JTextField();
    private JTextField txtCode=new JTextField();
    private JTextField txtIns=new JTextField();

    private JMenuItem mtDevices=new JMenuItem("Print devices");
    private JMenuItem mtCouriers=new JMenuItem("Print couriers");
    private JMenuItem mtOrders=new JMenuItem("Print orders");
    private JMenuItem editClear=new JMenuItem("Clear");
    private JMenuItem help=new JMenuItem("How does it works?");

    private JMenuItem checkIdCour=new JCheckBoxMenuItem("Display orders for courier");
    private JMenuItem checkNameManu=new JCheckBoxMenuItem("Display devices for manufacturer");
    private JMenuItem checkDelOrder=new JCheckBoxMenuItem("Delete order by id");
    private JMenuItem checkInsert=new JCheckBoxMenuItem("Insert");

    private JMenuBar mb1=new JMenuBar();
    private DefaultListModel lItems =new DefaultListModel();
    private JList lst=new JList(lItems);
    private JMenu
            file=new JMenu("File"),
            edit=new JMenu("Edit"),
            f=new JMenu("Menu"),
            men2=new JMenu("Help"),
            menAdv=new JMenu("Advanced");

    String[] columnNamesDevices = {
            "Id",
            "Name",
            "Manufacturer"
    };

    String[] columnNamesCouriers = {
            "Id",
            "Name"
    };

    String[] columnNamesOrders = {
            "Code",
            "Device",
            "Courier"
    };


    private ActionListener clearJSP=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            lItems.clear();
        }
    };

    private ActionListener displayDevs=new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            hideAll();
            jsp.setVisible(false);
            scrollPaneTable.setVisible(true);
            DefaultTableModel tm=new DefaultTableModel();
            tm.setColumnIdentifiers(columnNamesDevices);
            lItems.clear();
            List<String> result=connector.displayDevices();
            String[][] data =new String[result.size()][3];
            for(int i=0;i<result.size();i++){
                String[] items=result.get(i).split(" ");
                for(int j=0;j<3;j++){
                    data[i][j]=items[j];
                }
            }
            for(int i=0;i<data.length;i++){
                tm.addRow(data[i]);
            }
           tableDevs.setModel(tm);
        }
    };
    private ActionListener displayCour=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            hideAll();
            jsp.setVisible(false);
            scrollPaneTable.setVisible(true);
            DefaultTableModel tm=new DefaultTableModel();
            tm.setColumnIdentifiers(columnNamesCouriers);
            lItems.clear();
            List<String> result=connector.displayCouriers();
            String[][] data =new String[result.size()][2];
            for(int i=0;i<result.size();i++){
                String[] items=result.get(i).split(" ");
                for(int j=0;j<2;j++){
                    data[i][j]=items[j];
                }
            }
            for(int i=0;i<data.length;i++){
                tm.addRow(data[i]);
            }
            tableDevs.setModel(tm);
        }
    };
    private ActionListener displayOrders=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            hideAll();
            jsp.setVisible(false);
            scrollPaneTable.setVisible(true);
            DefaultTableModel tm=new DefaultTableModel();
            tm.setColumnIdentifiers(columnNamesOrders);
            lItems.clear();
            List<String> result=connector.displayOrders();
            String[][] data =new String[result.size()][3];
            for(int i=0;i<result.size();i++){
                String[] items=result.get(i).split(" ");
                for(int j=0;j<3;j++){
                    data[i][j]=items[j];
                }
            }
            for(int i=0;i<data.length;i++){
                tm.addRow(data[i]);
            }
            tableDevs.setModel(tm);
        }
    };

    private ActionListener displayOrdersCourElems=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            boolean isSelected=checkIdCour.isSelected();
            if(isSelected) {
                jsp.setVisible(true);
                scrollPaneTable.setVisible(false);
                jlFile.setBounds(txtId.getX(),txtId.getY()-15,100,15);
                jlFile.setVisible(true);
                jbId.setVisible(true);
                jlFile.setText("Field for the id");
                txtId.setVisible(true);
            }
            else {
                scrollPaneTable.setVisible(false);
                jbId.setVisible(false);
                jlFile.setVisible(false);
                txtId.setVisible(false);
            }
        }
    };

    private ActionListener displayManuElems=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            boolean isSelected=checkNameManu.isSelected();
            if(isSelected) {
                jsp.setVisible(true);
                scrollPaneTable.setVisible(false);
                jlName.setBounds(txtName.getX(),txtName.getY()-15,150,15);
                jlName.setVisible(true);
                jbName.setVisible(true);
                jlName.setText("Field for the company");
                txtName.setVisible(true);
            }
            else {
                scrollPaneTable.setVisible(false);
                jbName.setVisible(false);
                jlName.setVisible(false);
                txtName.setVisible(false);
            }
        }
    };

    private ActionListener displayOrdersId=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            lItems.clear();
            List<String> result = connector.displayOrdersForCour(Integer.parseInt(txtId.getText()));
            for (int i = 0; i < result.size(); i++) {
                lItems.addElement(result.get(i));
            }
        }
    };

    private ActionListener displayDevsManu=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            lItems.clear();
            List<String> result = connector.displayDevsForManu(txtName.getText());
            for (int i = 0; i < result.size(); i++) {
                lItems.addElement(result.get(i));
            }
        }
    };

    private ActionListener displayHelp=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message="Choose item in menus and click it.";
            JOptionPane.showMessageDialog(null,message,"Help",JOptionPane.INFORMATION_MESSAGE);
        }
    };

    private ActionListener displayDeleteOrder=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isSelected=checkDelOrder.isSelected();
            if(isSelected) {
                jbcode.setVisible(true);
                jlCode.setVisible(true);
                txtCode.setVisible(true);
                jlCode.setBounds(txtCode.getX(),txtCode.getY()-15,150,15);
            }
            else {
                jbcode.setVisible(false);
                jlCode.setVisible(false);
                txtCode.setVisible(false);
            }
        }
    };

    private ActionListener deleteOrder=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            connector.delOrder(Integer.parseInt(txtCode.getText()));
        }
    };

    private ActionListener displayInsert=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isSelected=checkInsert.isSelected();
            if(isSelected) {
                jbInsert.setVisible(true);
                jlIns.setVisible(true);
                txtIns.setVisible(true);
                jlIns.setBounds(txtIns.getX(),txtIns.getY()-15,150,15);
            }
            else {
                jbInsert.setVisible(false);
                jlIns.setVisible(false);
                txtIns.setVisible(false);
            }
        }
    };

    private ActionListener insert=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text=txtIns.getText();
            int what=text.charAt(text.length()-1)-'0';
            connector.insert(text,what);
        }
    };

    public Displayer()
    {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
        setLayout(null);
        tableDevs.setBounds(0,0,300,150);

        editClear.addActionListener(clearJSP);
        mtDevices.addActionListener(displayDevs);
        mtCouriers.addActionListener(displayCour);
        mtOrders.addActionListener(displayOrders);

        checkIdCour.addActionListener(displayOrdersCourElems);
        checkNameManu.addActionListener(displayManuElems);
        checkDelOrder.addActionListener(displayDeleteOrder);
        checkInsert.addActionListener(displayInsert);

        jbId.addActionListener(displayOrdersId);
        jbName.addActionListener(displayDevsManu);
        jbcode.addActionListener(deleteOrder);
        jbInsert.addActionListener(insert);

        help.addActionListener(displayHelp);

        mb1.add(file);
        mb1.add(edit);
        mb1.add(f);
        mb1.add(men2);
        mb1.add(menAdv);

        f.add(mtDevices);
        f.add(mtCouriers);
        f.add(mtOrders);

        menAdv.add(checkIdCour);
        menAdv.add(checkNameManu);
        menAdv.add(checkDelOrder);
        menAdv.add(checkInsert);


        men2.add(help);

        edit.add(editClear);

        setJMenuBar(mb1);
        jsp=new JScrollPane(lst);
        scrollPaneTable = new JScrollPane(tableDevs);
        jsp.setBounds(10,10,900,150);
        scrollPaneTable.setBounds(10,10,900,150);

        txtId.setBounds(10,190,100,30);
        txtName.setBounds(jsp.getWidth()-100,190,100,30);
        txtCode.setBounds(jsp.getWidth()/2,190,100,30);
        txtIns.setBounds(jsp.getWidth()/2,txtCode.getY()+100,100,30);

        txtId.setToolTipText("Enter id for query");
        txtName.setToolTipText("Enter name of the company");
        txtCode.setToolTipText("Enter code of order");
        txtIns.setToolTipText("Enter values separated by spaces and 1 for devices," +
                " 2 for couriers, 3 for orders");

        jbId.setBounds(txtId.getX(),txtId.getY()+txtId.getHeight(),100,50);
        jbName.setBounds(txtName.getX(),txtName.getY()+txtName.getHeight(),100,50);
        jbcode.setBounds(txtCode.getX(),txtCode.getY()+txtCode.getHeight(),100,50);
        jbInsert.setBounds(txtIns.getX(),txtIns.getY()+txtIns.getHeight(),100,50);

        add(jsp);
        add(scrollPaneTable);
        add(jlFile);
        add(jlName);
        add(jlCode);
        add(jlIns);

        add(txtId);
        add(txtName);
        add(txtCode);
        add(txtIns);

        add(jbId);
        add(jbName);
        add(jbcode);
        add(jbInsert);

        jsp.setVisible(false);
        scrollPaneTable.setVisible(false);
        hideAll();

    }

    public void hideAll(){
        jlFile.setVisible(false);
        jlName.setVisible(false);
        jlCode.setVisible(false);
        jlIns.setVisible(false);

        txtId.setVisible(false);
        txtName.setVisible(false);
        txtIns.setVisible(false);
        txtCode.setVisible(false);

        jbId.setVisible(false);
        jbName.setVisible(false);
        jbcode.setVisible(false);
        jbInsert.setVisible(false);


    }

    public static void main(String[] args)
    {
        SwingConsole.run(new Displayer(),1000,500);
    }

}

