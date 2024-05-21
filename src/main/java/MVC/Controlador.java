package MVC;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import BBDD.*;


public class Controlador implements ActionListener, ItemListener, ListSelectionListener, WindowListener {
    private boolean conectado;
    private Vista vista;
    //private Login login;
    private Modelo modelo;
    private boolean update;

    public Controlador(Vista vista, Modelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.conectado = true;
        modelo.conectar();
        addActionListeners(this);
        addWindowListeners(this);
        clearAllFields();
        uppdateAll();
        initAll();

        //this.login = new Login();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (!conectado && !comando.equalsIgnoreCase("Conectar")) {
            JOptionPane.showMessageDialog(null, "No has conectado con la BBDD",
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (comando) {
            case "Salir":
                modelo.desconectar();
                System.exit(0);
                break;
            case "Desconectar":
                modelo.desconectar();
                conectado = false;
                break;
            case "btnAddPrize":
                if (voidPrize()) {
                    Herramientas.showErrorAlert("Rellena todos los campos");
                } else {
                    Prize prize = new Prize();
                    prize.setPrizename(vista.txtPrizeName.getText());
                    prize.setPrizeamount(Float.parseFloat(vista.txtPrizeAmount.getText()));
                    prize.setPrizenumber(Integer.parseInt(vista.txtPrizeQty.getText()));
                    prize.setPrizepercentage(Float.parseFloat(vista.txtPrizePercentage.getText()));
                    modelo.altaPrize(prize);
                }
                clearFieldPrizes();
                break;
            case "btnAddPlayer":
                if (voidPlayer()) {
                    Herramientas.showErrorAlert("Rellena todos los campos");
                } else {
                    modelo.altaPlayer(Arrays.toString(vista.passFieldPlayer.getPassword()),
                            vista.txtEmailPlayer.getText(),
                            vista.btnEntryFeePaid.isSelected(),
                            vista.datePickerPlayerBirth.getDate(),
                            vista.txtPhonePlayer.getText(),
                            vista.txtFirstNamePlayer.getText(),
                            vista.txtLastNamePlayer.getText()
                    );
                }
                clearFieldPlayer();

                break;

            //TODO: Fill to make it takae the list of player, teams and tournaments
        }
        clearAllFields();
        //updateField();
    }


    /**
     * Clear the fields of the player
     */
    public void clearAllFields() {
        clearFieldPlayer();
        clearFieldPrizes();
        clearFieldTeam();
        clearFieldTournament();
    }

    /**
     * Clear the fields of the player
     */
    public void clearFieldPrizes() {
        vista.txtPrizeName.setText("");
        vista.txtPrizeAmount.setText("");
        vista.txtPrizeQty.setText("");
        vista.txtPrizePercentage.setText("");
    }

    /**
     * Clear the fields of the player
     */
    public void clearFieldPlayer() {
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
    public void clearFieldTeam() {
        vista.txtTeamName.setText("");
        vista.comboPlayers.setSelectedIndex(-1);
    }

    /**
     * Clear the fields of the team
     */
    public void clearFieldTournament() {
        vista.txtTournamentName.setText("");
        vista.comboPrize.setSelectedIndex(-1);
        vista.comboTeam.setSelectedIndex(-1);
    }

    /**
     * Añade WindowListeners a la vista
     *
     * @param listener WindowListener que se añade
     */
    private void addWindowListeners(WindowListener listener) {
        vista.addWindowListener(listener);
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
        //login.btnLogin.addActionListener(controlador);

    }

    /**
     * Check if the fields are empty
     */
    private boolean voidPlayer() {
        return vista.txtFirstNamePlayer.getText().isEmpty() || vista.txtLastNamePlayer.getText().isEmpty() ||
                vista.txtEmailPlayer.getText().isEmpty() || vista.txtPhonePlayer.getText().isEmpty() ||
                vista.passFieldPlayer.getPassword().length == 0 || vista.datePickerPlayerBirth.getDate() == null;
    }

    /**
     * Check if the fields are empty
     */
    private boolean voidPrize() {
        return vista.txtPrizeName.getText().isEmpty() || vista.txtPrizeAmount.getText().isEmpty() ||
                vista.txtPrizeQty.getText().isEmpty() || vista.txtPrizePercentage.getText().isEmpty();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    /**
     * Refresh the tables
     */
    private void uppdateAll() {
        updatePrize();
        updatePlayer();


        //updateTeam(); TODO:
        //updateTournament();TODO:
    }

    private void updatePlayer() {
        try {
            vista.tablePlayer.setModel(buildTableModelPlayer(modelo.getPlayer()));
            vista.comboPlayers.removeAllItems();
            for (int i = 0; i < vista.dtmPlayer.getRowCount(); i++) {
                vista.comboPlayers.addItem(vista.dtmPlayer.getValueAt(i, 1) + "-"
                        + vista.dtmPlayer.getValueAt(0, 2) + "-"
                        + vista.dtmPlayer.getValueAt(0, 3) + "-"
                        + vista.dtmPlayer.getValueAt(0, 4) + "-"
                        + vista.dtmPlayer.getValueAt(0, 5) + "-"
                        + vista.dtmPlayer.getValueAt(0, 6) + "-"
                        + vista.dtmPlayer.getValueAt(0, 7) + "-"
                        + vista.dtmPlayer.getValueAt(0, 8));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private TableModel buildTableModelPlayer(ResultSet rs) throws SQLException {
        Vector<String> columnNames = new Vector<>();

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            columnNames.add(metaData.getColumnName(columnIndex));
        }
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);
        vista.dtmPrize.setDataVector(data, columnNames);
        return vista.dtmPrize;
    }


    /**
     * Refresh the prize table
     */
    private void updatePrize() {
        try {
            vista.tablePrize.setModel(buildTableModelPrize(modelo.getPrize()));
            vista.comboPrize.removeAllItems();
            for (int i = 0; i < vista.dtmPrize.getRowCount(); i++) {
                vista.comboPrize.addItem(vista.dtmPrize.getValueAt(i, 2)) ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Build the DTM for the Prize Table
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private DefaultTableModel buildTableModelPrize(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            columnNames.add(metaData.getColumnName(columnIndex));
        }
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);
        vista.dtmPrize.setDataVector(data, columnNames);
        return vista.dtmPrize;
    }

    /**
     * Refresh the tables
     *
     * @param rs
     * @param columnCount
     * @param data
     * @throws SQLException
     */
    private void setDataVector(ResultSet rs, int columnCount, Vector<Vector<Object>> data) throws SQLException {
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
                System.out.println(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
    }
    /*
     * Muestra los atributos de un objeto seleccionado y los borra una vez se deselecciona
     * @param e Evento producido en una lista
     */

    private void initAll() {
        vista.tablePrize.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = vista.tablePrize.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tablePrize.getSelectionModel())) {
                        int row = vista.tablePrize.getSelectedRow();
                        vista.txtPrizeQty.setText(String.valueOf(vista.tablePrize.getValueAt(row, 1)));
                        vista.txtPrizeName.setText(String.valueOf(vista.tablePrize.getValueAt(row, 2)));
                        vista.txtPrizeAmount.setText(String.valueOf(vista.tablePrize.getValueAt(row, 3)));
                        vista.txtPrizePercentage.setText(String.valueOf(vista.tablePrize.getValueAt(row, 4)));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !update) {
                        if (e.getSource().equals(vista.tablePlayer.getSelectionModel())) {
                            clearFieldPlayer();
                        } else if (e.getSource().equals(vista.tableTeam.getSelectionModel())) {
                            clearFieldTeam();
                        } else if (e.getSource().equals(vista.tableTournament.getSelectionModel())) {
                            clearFieldTournament();
                        }
                    }
                }
            }
        });
        vista.tablePlayer.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel2 =  vista.tablePlayer.getSelectionModel();
        cellSelectionModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if  (e.getSource().equals(vista.tablePlayer.getSelectionModel())) {
                        int row = vista.tablePlayer.getSelectedRow();
                        vista.txtFirstNamePlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 6)));
                        vista.txtLastNamePlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 7)));
                        vista.txtEmailPlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 2)));
                        vista.txtPhonePlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 5)));
                        vista.passFieldPlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 1)));
                        vista.datePickerPlayerBirth.setDate((Date.valueOf(String.valueOf(vista.tablePlayer.getValueAt(row, 4)))).toLocalDate());
                        vista.btnEntryFeePaid.setSelected((boolean) vista.tablePlayer.getValueAt(row, 3));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !update) {
                        if (e.getSource().equals(vista.tablePrize.getSelectionModel())) {
                            clearFieldPrizes();
                        } else if (e.getSource().equals(vista.tableTeam.getSelectionModel())) {
                            clearFieldTeam();
                        } else if (e.getSource().equals(vista.tableTournament.getSelectionModel())) {
                            clearFieldTournament();
                        }
                    }
                }
            }
        });
    }

   /* @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()
                && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
            if (e.getSource().equals(vista.tablePrize.getSelectionModel())) {
                int row = vista.tablePrize.getSelectedRow();
                vista.txtPrizeQty.setText(String.valueOf(vista.tablePrize.getValueAt(row, 1)));
                vista.txtPrizeName.setText(String.valueOf(vista.tablePrize.getValueAt(row, 2)));
                vista.txtPrizeAmount.setText(String.valueOf(vista.tablePrize.getValueAt(row, 3)));
                vista.txtPrizePercentage.setText(String.valueOf(vista.tablePrize.getValueAt(row, 4)));
            } else if (e.getSource().equals(vista.tablePlayer.getSelectionModel())) {
                int row = vista.tablePlayer.getSelectedRow();
                vista.txtFirstNamePlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 1)));
                vista.txtLastNamePlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 2)));
                vista.txtEmailPlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 3)));
                vista.txtPhonePlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 4)));
                vista.passFieldPlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 5)));
                vista.datePickerPlayerBirth.setDate(Date.valueOf(String.valueOf(vista.tablePlayer.getValueAt(row, 6))).toLocalDate());
                vista.btnEntryFeePaid.setSelected((boolean) vista.tablePlayer.getValueAt(row, 7));
            } else if (e.getValueIsAdjusting()
                    && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !update) {
                if (e.getSource().equals(vista.tablePlayer.getSelectionModel())) {
                    clearFieldPlayer();
                } else if (e.getSource().equals(vista.tableTeam.getSelectionModel())) {
                    clearFieldTeam();
                } else if (e.getSource().equals(vista.tableTournament.getSelectionModel())) {
                    clearFieldTournament();
                }
            }
        }
    }*/
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
            if (e.getSource().equals(vista.tablePrize.getSelectionModel())) {
                int row = vista.tablePrize.getSelectedRow();
                vista.txtPrizeName.setText(String.valueOf(vista.tablePrize.getValueAt(row, 0)));
                vista.txtPrizeAmount.setText(String.valueOf(vista.tablePrize.getValueAt(row, 1)));
                vista.txtPrizeQty.setText(String.valueOf(vista.tablePrize.getValueAt(row, 2)));
                vista.txtPrizePercentage.setText(String.valueOf(vista.tablePrize.getValueAt(row, 3)));
            } else if (e.getSource().equals(vista.tablePlayer.getSelectionModel())) {
                int row = vista.tablePlayer.getSelectedRow();
                vista.txtFirstNamePlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 0)));
                vista.txtLastNamePlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 1)));
                vista.txtPhonePlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 2)));
                vista.txtEmailPlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 3)));
                vista.passFieldPlayer.setText(String.valueOf(vista.tablePlayer.getValueAt(row, 4)));
                vista.btnEntryFeePaid.setSelected(Boolean.parseBoolean(String.valueOf(vista.tablePlayer.getValueAt(row, 5))));
                vista.datePickerPlayerBirth.setDate(Date.valueOf(String.valueOf(vista.tablePlayer.getValueAt(row, 6))).toLocalDate());
            } else if (e.getSource().equals(vista.tableTeam.getSelectionModel())) {
                int row = vista.tableTeam.getSelectedRow();
                vista.txtTeamName.setText(String.valueOf(vista.tableTeam.getValueAt(row, 0)));
                // Assume comboPlayers is related to players in the team
                vista.comboPlayers.setSelectedItem(String.valueOf(vista.tableTeam.getValueAt(row, 1)));
            } else if (e.getSource().equals(vista.tablePlayerTeam.getSelectionModel())) {
                int row = vista.tablePlayerTeam.getSelectedRow();
                // Handle selection in player-team table
                // Fill the appropriate fields if necessary
            } else if (e.getSource().equals(vista.tableTeamTournament.getSelectionModel())) {
                int row = vista.tableTeamTournament.getSelectedRow();
                // Handle selection in team-tournament table
                // Fill the appropriate fields if necessary
            } else if (e.getSource().equals(vista.tableTournament.getSelectionModel())) {
                int row = vista.tableTournament.getSelectedRow();
                vista.txtTournamentName.setText(String.valueOf(vista.tableTournament.getValueAt(row, 0)));
                vista.comboPrize.setSelectedItem(String.valueOf(vista.tableTournament.getValueAt(row, 1)));
                vista.comboTeam.setSelectedItem(String.valueOf(vista.tableTournament.getValueAt(row, 2)));
            }
        } else if (e.getValueIsAdjusting() && ((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
            if (e.getSource().equals(vista.tablePrize.getSelectionModel())) {
                clearFieldPrizes();
            } else if (e.getSource().equals(vista.tablePlayer.getSelectionModel())) {
                clearFieldPlayer();
            } else if (e.getSource().equals(vista.tableTeam.getSelectionModel())) {
                clearFieldTeam();
            } else if (e.getSource().equals(vista.tablePlayerTeam.getSelectionModel())) {
                // Clear fields related to player-team
            } else if (e.getSource().equals(vista.tableTeamTournament.getSelectionModel())) {
                // Clear fields related to team-tournament
            } else if (e.getSource().equals(vista.tableTournament.getSelectionModel())) {
                clearFieldTournament();
            }
        }
    }
}
