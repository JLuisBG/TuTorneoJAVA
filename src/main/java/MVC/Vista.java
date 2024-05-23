package MVC;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Vista extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    //
    public JTextField txtPrizeName;
    public JTextField txtPrizeAmount;
    public JTextField txtPrizeQty;
    public JTextField txtPrizePercentage;
    public JButton btnAddPrize;
    public JButton btnModPrize;
    public JButton btnDelPrize;
    public JTextField txtFirstNamePlayer;
    public JTextField txtLastNamePlayer;
    public JTextField txtPhonePlayer;
    public JTextField txtEmailPlayer;
    public JPasswordField passFieldPlayer;
    public JRadioButton btnEntryFeePaid;
    public DatePicker datePickerPlayerBirth;
    public JTextField txtTeamName;
    public JComboBox comboPlayers;
    public JButton btnAddPlayerTeam;
    public JButton btnDelPlayerTeam;
    public JButton btnAddTeam;
    public JButton btnModTeam;
    public JButton btnDelTeam;
    public JButton btnImportLogo;
    public JTextField txtTournamentName;
    public JComboBox comboPrize;
    public JComboBox comboTeam;
    public JButton btnAddTeamTournament;
    public JButton btnDeleteTeamTournament;
    public JButton btnAddTournament;
    public JButton btnModTournament;
    public JButton btnDelTournametn;
    public JButton btnDelPlayer;
    public JButton btnAddPlayer;
    public JButton btnModPlayer;
    public JTable tablePrize;
    public JTable tablePlayer;
    public JTable tableTeam;
    public JTable tablePlayerTeam;
    public JTable tableTeamTournament;
    public JTable tableTournament;

    //default table model
    DefaultTableModel dtmPrize;
    DefaultTableModel dtmPlayer;
    DefaultTableModel dtmTeam;
    DefaultTableModel dtmPlayerTeam;
    DefaultTableModel dtmTeamTournament;
    DefaultTableModel dtmTournament;
    //
    JMenuItem conexionItem;
    JMenuItem salirItem;


    public Vista() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(this.getWidth() +150, this.getHeight()+200));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        createMenu();
        setTableModels();
    }

    private void createMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu menu = new JMenu("Archivo");

        conexionItem = new JMenuItem("Desconectar");
        conexionItem.setActionCommand("Desconectar");

        salirItem = new JMenuItem("Salir");
        salirItem.setActionCommand("Salir");

        menu.add(conexionItem);
        menu.add(salirItem);
        barra.add(menu);
        this.setJMenuBar(barra);
    }

    private void setTableModels() {
        this.dtmPrize = new DefaultTableModel();
        this.tablePrize.setModel(dtmPrize);


        this.dtmPlayer = new DefaultTableModel();
        this.tablePlayer.setModel(dtmPlayer);

        this.dtmTeam = new DefaultTableModel();
        this.tableTeam.setModel(dtmTeam);

        this.dtmPlayerTeam = new DefaultTableModel();
        this.tablePlayerTeam.setModel(dtmPlayerTeam);
        this.dtmTeamTournament = new DefaultTableModel();
        this.tableTeamTournament.setModel(dtmTeamTournament);
        this.dtmTournament = new DefaultTableModel();
        this.tableTournament.setModel(dtmTournament);


    }
}
