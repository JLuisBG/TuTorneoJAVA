package MVC;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
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
    public JList listPrizes;
    public JTextField txtFirstNamePlayer;
    public JTextField txtLastNamePlayer;
    public JTextField txtPhonePlayer;
    public JTextField txtEmailPlayer;
    public JPasswordField passFieldPlayer;
    public JRadioButton btnEntryFeePaid;
    public DatePicker datePickerPlayerBirth;
    public JTextField txtTeamName;
    public JComboBox comboPlayers;
    public JList listPlayerTeam;
    public JList list2;
    public JButton btnAddPlayerTeam;
    public JButton btnDelPlayerTeam;
    public JButton btnAddTeam;
    public JButton btnModTeam;
    public JButton btnDelTeam;
    public JList listTeam;
    public JButton btnImportLogo;
    public JTextField txtTournamentName;
    public JComboBox comboPrize;
    public JComboBox comboTeam;
    public JButton btnAddTeamTournament;
    public JButton btnDeleteTeamTournament;
    public JList listTeamTournament;
    public JButton btnAddTournament;
    public JButton btnModTournament;
    public JButton btnDelTournametn;
    public JList listTournament;
    public JList listPlayer;
    public JButton btnDelPlayer;
    public JButton btnAddPlayer;
    public JButton btnModPlayer;

    //
    DefaultListModel dlmPrizes;
    DefaultListModel dlmPlayers;
    DefaultListModel dlmTeams;
    DefaultListModel dlmTournaments;
    DefaultListModel dlmPlayerTeam;
    DefaultListModel dlmTeamTournament;
    //
    JMenuItem conexionItem;
    JMenuItem salirItem;

    /**
     * Método que crea los modelos de las listas
     */
    private void createModels() {
        dlmPrizes = new DefaultListModel();
        listPrizes.setModel(dlmPrizes);

        dlmPlayers = new DefaultListModel();
        list2.setModel(dlmPlayers);

        dlmTeams = new DefaultListModel();
        listTeam.setModel(dlmTeams);

        dlmTournaments = new DefaultListModel();
        listTournament.setModel(dlmTournaments);

        dlmPlayerTeam = new DefaultListModel();
        listPlayerTeam.setModel(dlmPlayerTeam);

        dlmTeamTournament = new DefaultListModel();
        listTeamTournament.setModel(dlmTeamTournament);
    }

    public Vista(){
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(this.getWidth()+800, this.getHeight()-200));
        this.setLocationRelativeTo(null);
        createMenu();
        createModels();
    }

    private void createMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu menu = new JMenu("Archivo");

        conexionItem = new JMenuItem("Conectar");
        conexionItem.setActionCommand("Conectar");

        salirItem = new JMenuItem("Salir");
        salirItem.setActionCommand("Salir");

        menu.add(conexionItem);
        menu.add(salirItem);
        barra.add(menu);
        this.setJMenuBar(barra);
    }
}
