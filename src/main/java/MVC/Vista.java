package MVC;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;

public class Vista extends JFrame{
    private JTabbedPane tabbedPane1;
    private JFrame frame;
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
    public JComboBox comboPlayyes;
    public JButton btnAddPlayerTeam;
    public JList listPlayerTeam;
    public JList list2;
    public JButton añadirJugadorButton;
    public JButton eliminarJugadorButton;
    public JButton añadirButton;
    public JButton modificarButton;
    public JButton eliminarButton;
    public JList listTeam;
    public JButton btnImportLogo;
    public JTextField txtTournamentName;
    public JComboBox comboPrize;
    public JComboBox comboTeam;
    public JButton btnAddTeam;
    public JButton btnDeleteTeam;
    public JList listTeamTournament;
    public JButton btnAddTournament;
    public JButton btnModTournament;
    public JButton btnDelTournametn;
    public JList listTournament;

    //
    DefaultListModel dlmPrizes;
    DefaultListModel dlmPlayers;
    DefaultListModel dlmTeams;
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
    }

    public Vista() {
        createModels();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        int width = screenSize.width * 2 / 3; // two-thirds of the screen width
        int height = screenSize.height * 2 / 3; // two-thirds of the screen height
        this.setSize(new Dimension(width, height));
        this.setLocationRelativeTo(null);
        crearMenu();
    }
    private void crearMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu menu = new JMenu("Archivo");

        conexionItem = new JMenuItem("Conectar");
        conexionItem.setActionCommand("Conectar");

        salirItem = new JMenuItem("Salir");
        salirItem.setActionCommand("Salir");

        menu.add(conexionItem);
        menu.add(salirItem);
        barra.add(menu);
        frame.setJMenuBar(barra);
    }
}
