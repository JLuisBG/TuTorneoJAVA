package MVC;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener, ListSelectionListener {
    private Vista vista;
    private Login login;
    private Modelo modelo;

    public Controlador(Vista vista, Modelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        addActionListeners(this);
        addListSelectionListener(this);
<<<<<<< HEAD
        this.login = new Login();
    }



=======
    }



>>>>>>> 93fe94e95a55d1881959cdfe244c197f16e0fba9
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "Salir":
                modelo.desconectar();
                System.exit(0);
                break;
            case "Conectar":
                modelo.conectar();
                vista.conexionItem.setEnabled(false);
                //TODO: Fill to make it takae the list of player, teams and tournaments
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
    /**
     * Add list selection listeners to the view
     * @param controlador
     */
    private void addListSelectionListener(Controlador controlador) {
<<<<<<< HEAD
        vista.listPlayer.addListSelectionListener(controlador);
        vista.listTeam.addListSelectionListener(controlador);
        vista.listPrizes.addListSelectionListener(controlador);
        vista.listPlayerTeam.addListSelectionListener(controlador);
        vista.listTeamTournament.addListSelectionListener(controlador);
        vista.listTournament.addListSelectionListener(controlador);
=======
        //TOOD: Fill Method
>>>>>>> 93fe94e95a55d1881959cdfe244c197f16e0fba9
    }

    /**
     * Add action listeners to the view
     * @param controlador
     */
    private void addActionListeners(Controlador controlador) {
        vista.conexionItem.addActionListener(controlador);
        vista.salirItem.addActionListener(controlador);
        vista.btnAddTeam.addActionListener(controlador);
        vista.btnAddPrize.addActionListener(controlador);
        vista.btnAddTeamTournament.addActionListener(controlador);
        vista.btnAddTournament.addActionListener(controlador);
        vista.btnAddPlayerTeam.addActionListener(controlador);
        vista.btnAddPlayer.addActionListener(controlador);
        vista.btnDelPlayer.addActionListener(controlador);
        vista.btnDelPrize.addActionListener(controlador);
        vista.btnDeleteTeamTournament.addActionListener(controlador);
        vista.btnDelTournametn.addActionListener(controlador);
        vista.btnDelTeam.addActionListener(controlador);
        vista.btnDelPlayerTeam.addActionListener(controlador);
        vista.btnModPlayer.addActionListener(controlador);
        vista.btnModPrize.addActionListener(controlador);
        vista.btnModTournament.addActionListener(controlador);
        vista.btnModTeam.addActionListener(controlador);
        vista.btnImportLogo.addActionListener(controlador);
<<<<<<< HEAD
        login.btnLogin.addActionListener(controlador);

=======
>>>>>>> 93fe94e95a55d1881959cdfe244c197f16e0fba9
    }
}
