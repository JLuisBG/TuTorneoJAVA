package MVC;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import BBDD.*;

public class Controlador implements ActionListener, ListSelectionListener {
    private Vista vista;
    private Login login;
    private Modelo modelo;

    public Controlador(Vista vista, Modelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        addActionListeners(this);
        addListSelectionListener(this);
        this.login = new Login();
    }


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
                break;
            //TODO: Fill to make it takae the list of player, teams and tournaments
        }
    }

    /**
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            if (e.getSource() == vista.listPlayer) {
                Player player = (Player) vista.listPlayer.getSelectedValue();
                vista.txtFirstNamePlayer.setText(player.getFirstname());
                vista.txtLastNamePlayer.setText(player.getLastname());
                vista.txtEmailPlayer.setText(player.getEmail());
                vista.txtPhonePlayer.setText(player.getTelephoneno().toString());
                vista.passFieldPlayer.setText("********");
                vista.datePickerPlayerBirth.setDate(player.getBirthdate());
                vista.btnEntryFeePaid.setSelected(player.getIsentryfeepaid());

            }
            if (e.getSource() == vista.listPrizes){
                Prize prize = (Prize) vista.listPrizes.getSelectedValue();
                vista.txtPrizeName.setText(prize.getPrizename());
                vista.txtPrizeAmount.setText(prize.getPrizeamount().toString());
                vista.txtPrizeQty.setText(prize.getPrizenumber().toString());
                vista.txtPrizePercentage.setText(prize.getPrizepercentage().toString());
            }
            if(e.getSource() == vista.listTeam){
                Team team = (Team) vista.listTeam.getSelectedValue();
                vista.txtTeamName.setText(team.getName());
            }
            if(e.getSource() == vista.listTournament){
                Tournament tournament = (Tournament) vista.listTournament.getSelectedValue();
                vista.txtTournamentName.setText(tournament.getTournamentname());
                //vista.comboPrize.setSelectedItem(tournament.));
                //TODO: Hacer el combo de premios con el ide traermelo de la tabla de premios y ponerlo en el combo
            }
        }
    }
    /**
     * Clear the fields of the player
     */
    public void clearAllFields(){
        clearFieldPlayer();
        clearFieldPrizes();
        clearFieldTeam();
        clearFieldTournament();
    }
    /**
     * Clear the fields of the player
     */
    public void clearFieldPrizes(){
        vista.txtPrizeName.setText("");
        vista.txtPrizeAmount.setText("");
        vista.txtPrizeQty.setText("");
        vista.txtPrizePercentage.setText("");
    }

    /**
     * Clear the fields of the player
     */
    public void clearFieldPlayer(){
        vista.txtFirstNamePlayer.setText("");
        vista.txtLastNamePlayer.setText("");
        vista.txtEmailPlayer.setText("");
        vista.txtPhonePlayer.setText("");
        vista.passFieldPlayer.setText("");
        vista.datePickerPlayerBirth.setDate(null);
        vista.btnEntryFeePaid.setSelected(false);
    }
    /**
     * Clear the fields of the team
     */
    public void clearFieldTeam(){
        vista.txtTeamName.setText("");
        vista.comboPlayers.setSelectedIndex(-1);
    }
    /**
     * Clear the fields of the team
     */
    public void clearFieldTournament(){
        vista.txtTournamentName.setText("");
        vista.comboPrize.setSelectedIndex(-1);
        vista.comboTeam.setSelectedIndex(-1);
    }
    /**
     * List the players in the view
     * @param playerList
     */
    public void listPlayers(List<Player> playerList){
        vista.dlmPlayers.clear();
        for (Player player : playerList) {
            vista.dlmPlayers.addElement(player);
        }
    }

    /**
     * List the teams in the view
     * @param teamList
     */
    public void listTeams(List<Team> teamList){
        vista.dlmTeams.clear();
        for (Team team : teamList) {
            vista.dlmTeams.addElement(team);
        }
    }
    /**
     * List the tournaments in the view
     * @param prizeList
     */
    public void listPrizes(List<Prize> prizeList){
        vista.dlmPrizes.clear();
        for (Prize prize : prizeList) {
            vista.dlmPrizes.addElement(prize);
        }
    }
    /**
     * List the tournaments teams in the view
     * @param tournamentList
     */
    public void listTournaments(List<Tournament> tournamentList){
        vista.dlmTournaments.clear();
        for (Tournament tournament : tournamentList) {
            vista.dlmTournaments.addElement(tournament);
        }

    }

    /**
     * List the players in the tournament
     * @param teamList
     */
    public void listTeamTournament(List<Team> teamList){

    }
    /**
     * List the players in the team
     * @param playerList
     */
    public void listPlayerTeam(List<Player> playerList){
        vista.dlmPlayerTeam.clear();
        for (Player player : playerList) {
            vista.dlmPlayerTeam.addElement(player);
        }
    }
    /**
     * List the prizes in the combo
     * @param prizeList
     */
    public void listPrizesCombo(List<Prize> prizeList){
        vista.comboPrize.removeAllItems();
        for (Prize prize : prizeList) {
            vista.dlmPrizes.addElement(prize);
        }
        vista.comboPrize.setSelectedIndex(-1);
    }
    /**
     * List the teams in the combo
     * @param teamList
     */
    public void listTeamCombo(List<Team> teamList){
        vista.comboTeam.removeAllItems();
        for (Team team : teamList) {
            vista.dlmTeams.addElement(team);
        }
        vista.comboTeam.setSelectedIndex(-1);
    }

    public void listComboPlayer(List<Player> playerList){
        vista.comboPlayers.removeAllItems();
        for (Player player : playerList) {
            vista.dlmPlayers.addElement(player);
        }
        vista.comboPlayers.setSelectedIndex(-1);
    }
    /**
     * Add list selection listeners to the view
     *
     * @param controlador
     */
    private void addListSelectionListener(Controlador controlador) {
        vista.listPlayer.addListSelectionListener(controlador);
        vista.listTeam.addListSelectionListener(controlador);
        vista.listPrizes.addListSelectionListener(controlador);
        vista.listPlayerTeam.addListSelectionListener(controlador);
        vista.listTeamTournament.addListSelectionListener(controlador);
        vista.listTournament.addListSelectionListener(controlador);
    }

    /**
     * Add action listeners to the view
     *
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
        login.btnLogin.addActionListener(controlador);

    }
}
