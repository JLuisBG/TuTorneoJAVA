package MVC;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Vector;

import BBDD.*;

/**
 * Esta clase es el controlador de la aplicación
 */
public class Controlador implements ActionListener, ItemListener, ListSelectionListener, WindowListener {
    private boolean conectado;
    private Vista vista;
    private Login login;
    private Modelo modelo;
    private boolean update;
    private FileInputStream fin;
    private boolean isAdmin;
    private Jasper jasper;

    /**
     * Constructor de la clase Controlador
     *
     * @param vista
     * @param modelo
     */
    public Controlador(Vista vista, Modelo modelo, Login login) {
        this.vista = vista;
        this.modelo = modelo;
        this.login = login;
        this.conectado = true;
        this.isAdmin = false;
        this.jasper = new Jasper();
        vista.btnAddPlayerTeam.setEnabled(false);
        vista.btnDeleteTeamTournament.setEnabled(false);
        vista.btnDelPlayerTeam.setEnabled(false);
        vista.btnAddTeamTournament.setEnabled(false);
        modelo.conectar();
        addActionListeners(this);
        addWindowListeners(this);
        clearAllFields();
        uppdateAll();
        initAll();
    }

    /**
     * Método que se encarga de procesar los eventos
     *
     * @param e the event to be processed
     */
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
            case "SalirLogin":
                modelo.desconectar();
                System.exit(0);
                break;
            case "Desconectar":
                vista.setVisible(false);
                login.setVisible(true);
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
                updatePrize();
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
                updatePlayer();

                break;

            case "btnModPrize":
                try {
                    if (voidPrize()) {
                        Herramientas.showErrorAlert("Rellena todos los campos");
                        vista.tablePrize.clearSelection();
                    } else {
                        modelo.modPrize(
                                Integer.parseInt(vista.tablePrize.getValueAt(vista.tablePrize.getSelectedRow(), 0).toString()),
                                Integer.parseInt(vista.txtPrizeQty.getText()),
                                vista.txtPrizeName.getText(),
                                Float.parseFloat(vista.txtPrizeAmount.getText()),
                                Float.parseFloat(vista.txtPrizePercentage.getText())
                        );
                    }
                } catch (NumberFormatException nfe) {
                    Herramientas.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.tablePrize.clearSelection();
                }
                clearFieldPrizes();
                updatePrize();
                break;

            case "btnDelPrize":
                modelo.deletePrize(Integer.parseInt(vista.tablePrize.getValueAt(vista.tablePrize.getSelectedRow(), 0).toString()));
                clearFieldPrizes();
                updatePrize();
                break;
            case "btnDelPlayer":
                modelo.deletePlayerTeam(Integer.parseInt(vista.tablePlayer.getValueAt(vista.tablePlayer.getSelectedRow(), 0).toString()));
                modelo.deletePlayer(Integer.parseInt(vista.tablePlayer.getValueAt(vista.tablePlayer.getSelectedRow(), 0).toString()));
                clearFieldPlayer();
                updatePlayer();
                break;
            case "btnModPlayer":
                try {
                    if (voidPlayer()) {
                        Herramientas.showErrorAlert("Rellena todos los campos");
                        vista.tablePlayer.clearSelection();
                    } else {
                        modelo.modPlayer(
                                Integer.parseInt(vista.tablePlayer.getValueAt(vista.tablePlayer.getSelectedRow(), 0).toString()),
                                Arrays.toString(vista.passFieldPlayer.getPassword()),
                                vista.txtEmailPlayer.getText(),
                                vista.txtFirstNamePlayer.getText(),
                                vista.txtLastNamePlayer.getText(),
                                vista.txtPhonePlayer.getText(),
                                vista.btnEntryFeePaid.isSelected(),
                                vista.datePickerPlayerBirth.getDate()
                        );
                    }
                } catch (NumberFormatException nfe) {
                    Herramientas.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.tablePlayer.clearSelection();
                }
                clearFieldPlayer();
                updatePlayer();
                break;
            case "btnImportLogo":
                JFileChooser selectorFichero = Herramientas.crearSelectorFicheros(null, "Imagen", "jpg");
                int opt = selectorFichero.showOpenDialog(null);
                if (opt == JFileChooser.APPROVE_OPTION) {
                    try {
                        fin = new FileInputStream(selectorFichero.getSelectedFile());
                    } catch (FileNotFoundException ex) {
                        Herramientas.showInfoAlert("La imagen no fue importada correctamente");
                        throw new RuntimeException(ex);
                    }
                    Herramientas.mensajeExitoso("Imagen importada correctamente");
                }
                break;
            case "btnAddTeam":
                if (vista.txtTeamName.getText().isEmpty()) {
                    Herramientas.showErrorAlert("Rellena todos los campos");
                } else {
                    modelo.addTeam(vista.txtTeamName.getText(), fin);
                }
                clearFieldTeam();
                updateTeam();
                break;
            case "btnModTeam":
                if (vista.txtTeamName.getText().isEmpty()) {
                    Herramientas.showErrorAlert("Rellena todos los campos");
                } else {
                    if (fin == null) {
                        modelo.modTeam(Integer.parseInt(vista.tableTeam.getValueAt(vista.tableTeam.getSelectedRow(), 0).toString()), vista.txtTeamName.getText());
                    } else {
                        modelo.modTeam(Integer.parseInt(vista.tableTeam.getValueAt(vista.tableTeam.getSelectedRow(), 0).toString()), vista.txtTeamName.getText(), fin);
                    }
                }
                clearFieldTeam();
                updateTeam();
                break;
            case "btnDelTeam":
                modelo.deleteTeamPlayer(Integer.parseInt(vista.tableTeam.getValueAt(vista.tableTeam.getSelectedRow(), 0).toString()));
                modelo.deleteTeamTournament(Integer.parseInt(vista.tableTeam.getValueAt(vista.tableTeam.getSelectedRow(), 0).toString()));
                modelo.deleteTeam(Integer.parseInt(vista.tableTeam.getValueAt(vista.tableTeam.getSelectedRow(), 0).toString()));
                clearFieldTeam();
                updateTeam();
                break;
            case "btnAddTournament":
                if (vista.txtTournamentName.getText().isEmpty() || vista.comboPrize.getSelectedIndex() == -1) {
                    Herramientas.showErrorAlert("Rellena todos los campos");
                } else {
                    String prizeId = (String.valueOf(vista.comboPrize.getSelectedItem())).split("-")[0];
                    modelo.addTournament(vista.txtTournamentName.getText(),
                            Integer.valueOf(prizeId));
                }
                clearFieldTournament();
                updateTournament();
                break;
            case "btnDelTournament":
                modelo.deleteTournamentTeam(Integer.parseInt(vista.tableTournament.getValueAt(vista.tableTournament.getSelectedRow(), 0).toString()));
                modelo.deleteTournament(Integer.parseInt(vista.tableTournament.getValueAt(vista.tableTournament.getSelectedRow(), 0).toString()));
                clearFieldTournament();
                updateTournament();
                break;

            case "btnModTournament":
                if (vista.txtTournamentName.getText().isEmpty() || vista.comboPrize.getSelectedIndex() == -1) {
                    Herramientas.showErrorAlert("Rellena todos los campos");
                } else {
                    String prizeId = (String.valueOf(vista.comboPrize.getSelectedItem())).split("-")[0];
                    modelo.modTournament(Integer.parseInt(vista.tableTournament.getValueAt(vista.tableTournament.getSelectedRow(), 0).toString()),
                            vista.txtTournamentName.getText(),
                            Integer.valueOf(prizeId));
                }
                clearFieldTournament();
                updateTournament();
                break;
            case "btnAddPlayerTeam":
                if (vista.comboPlayers.getSelectedIndex() == -1) {
                    Herramientas.showErrorAlert("Selecciona un jugador");
                } else {
                    String playerId = (String.valueOf(vista.comboPlayers.getSelectedItem())).split("-")[0];
                    modelo.addPlayerTeam(Integer.parseInt(playerId), Integer.parseInt(vista.tableTeam.getValueAt(vista.tableTeam.getSelectedRow(), 0).toString()));
                    updatePlayerTeam();
                }
                break;
            case "btnDeletePlayerTeam":
                modelo.deletePlayerTeam(Integer.parseInt(vista.tablePlayerTeam.getValueAt(vista.tablePlayerTeam.getSelectedRow(), 0).toString()));
                updatePlayerTeam();
                break;
            case "btnAddTeamTournament":
                if (vista.comboTeam.getSelectedIndex() == -1) {
                    Herramientas.showErrorAlert("Selecciona un equipo");
                } else {
                    String teamId = (String.valueOf(vista.comboTeam.getSelectedItem())).split("-")[0];
                    modelo.addTeamTournament(Integer.parseInt(teamId), Integer.parseInt(vista.tableTournament.getValueAt(vista.tableTournament.getSelectedRow(), 0).toString()));
                    System.out.println("teamId: " + teamId + " tournamentId: " + vista.tableTournament.getValueAt(vista.tableTournament.getSelectedRow(), 0).toString());
                    updateTeamTournament();
                }
                break;
            case "btnDeleteTeamTournament":
                modelo.deleteTeamTournament(Integer.parseInt(vista.tableTeamTournament.getValueAt(vista.tableTeamTournament.getSelectedRow(), 0).toString()));
                updateTeamTournament();
                break;
            case "ListarJugadores":
                jasper.reportPlayers();
                break;
            case "ListarEquipos":
                jasper.reportTeams();
                break;

            case "btnLogin":
                if (modelo.comprobarAdmin(login.txtEmailLogin.getText(), login.passfieldLogin.getPassword())) {
                    loginAsAdmin();
                } else if (modelo.comprobarUsuario(login.txtEmailLogin.getText(), login.passfieldLogin.getPassword())) {
                    loginAsUser();
                } else {
                    Herramientas.showWarningAlert("Usuario o contraseña incorrectos");
                }
                break;
                case "btnExportLogo":
                    try {
                        modelo.descargarArchivo(Integer.parseInt(vista.tableTeam.getValueAt(vista.tableTeam.getSelectedRow(), 0).toString()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;

        }

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
        vista.listarJugadoresItem.addActionListener(controlador);
        vista.listarEquiposItem.addActionListener(controlador);
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
        vista.btnExportLogo.addActionListener(controlador);
        login.btnLogin.addActionListener(controlador);
        login.salirItemLogin.addActionListener(controlador);

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
        updatePlayer();
        updatePrize();
        updateTeam();
        updateTournament();
    }

    /**
     * Update the player team
     */
    private void updatePlayerTeam() {
        try {
            vista.tablePlayerTeam.setModel(buildTableModelPlayerTeam(modelo.getTeamPlayer(Integer.parseInt(vista.tableTeam.getValueAt(vista.tableTeam.getSelectedRow(), 0).toString()))));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Update the team tournament
     */
    private void updateTeamTournament() {
        try {
            vista.tableTeamTournament.setModel(buildTableModelTournamentTeam(modelo.getTeamTournament(Integer.parseInt(vista.tableTournament.getValueAt(vista.tableTournament.getSelectedRow(), 0).toString()))));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Refresh the tournament table
     */
    private void updateTournament() {
        try {
            vista.tableTournament.setModel(buildTableModelTournament(modelo.getTournament()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        vista.comboPrize.removeAllItems();
        for (int i = 0; i < vista.dtmPrize.getRowCount(); i++) {
            vista.comboPrize.addItem(vista.dtmPrize.getValueAt(i, 0) + "-" +
                    vista.dtmPrize.getValueAt(i, 2));
        }
        vista.comboPrize.setSelectedIndex(-1);
        vista.comboTeam.removeAllItems();
        for (int i = 0; i < vista.dtmTeam.getRowCount(); i++) {
            vista.comboTeam.addItem(vista.dtmTeam.getValueAt(i, 0) + "-" +
                    vista.dtmTeam.getValueAt(i, 1));
        }
        vista.comboTeam.setSelectedIndex(-1);
    }

    /**
     * Refresh the team table
     */

    private void updateTeam() {
        try {
            vista.tableTeam.setModel(buildTableModelTeam(modelo.getTeam()));
            vista.comboTeam.removeAllItems();
            for (int i = 0; i < vista.dtmTeam.getRowCount(); i++) {
                vista.comboTeam.addItem(vista.dtmTeam.getValueAt(i, 0) + "-" +
                        vista.dtmTeam.getValueAt(i, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refresh the player table
     */
    private void updatePlayer() {
        try {
            vista.tablePlayer.setModel(buildTableModelPlayer(modelo.getPlayer()));
            vista.comboPlayers.removeAllItems();
            for (int i = 0; i < vista.dtmPlayer.getRowCount(); i++) {
                vista.comboPlayers.addItem(vista.dtmPlayer.getValueAt(i, 0) + "-"
                        + vista.dtmPlayer.getValueAt(i, 6) + "-"
                        + vista.dtmPlayer.getValueAt(i, 7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Build the DTM for the Tournament Table
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private DefaultTableModel buildTableModelTournament(ResultSet rs) throws SQLException {
        Vector<String> columnNames = new Vector<>();

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            columnNames.add(metaData.getColumnName(columnIndex));
        }
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);
        vista.dtmTournament.setDataVector(data, columnNames);
        return vista.dtmTournament;
    }

    /**
     * Build the DTM for the Player Table
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private DefaultTableModel buildTableModelPlayer(ResultSet rs) throws SQLException {
        Vector<String> columnNames = new Vector<>();

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            columnNames.add(metaData.getColumnName(columnIndex));
        }
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);
        vista.dtmPlayer.setDataVector(data, columnNames);
        return vista.dtmPlayer;
    }


    /**
     * Refresh the prize table
     */
    private void updatePrize() {
        try {
            vista.tablePrize.setModel(buildTableModelPrize(modelo.getPrize()));
            vista.comboPrize.removeAllItems();
            for (int i = 0; i < vista.dtmPrize.getRowCount(); i++) {
                vista.comboPrize.addItem(vista.dtmPrize.getValueAt(i, 0) + "-" +
                        vista.dtmPrize.getValueAt(i, 2));
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
     * Build the DTM for the Team Table
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private DefaultTableModel buildTableModelTeam(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            columnNames.add(metaData.getColumnName(columnIndex));
        }
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);
        vista.dtmTeam.setDataVector(data, columnNames);
        return vista.dtmTeam;
    }

    /**
     * Build the DTM for Player Team
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private DefaultTableModel buildTableModelPlayerTeam(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            columnNames.add(metaData.getColumnName(columnIndex));
        }
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);
        vista.dtmPlayerTeam.setDataVector(data, columnNames);
        return vista.dtmPlayerTeam;
    }

    /**
     * Build the DTM for the Team Tournament Table
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private DefaultTableModel buildTableModelTournamentTeam(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            columnNames.add(metaData.getColumnName(columnIndex));
        }
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);
        vista.dtmTeamTournament.setDataVector(data, columnNames);
        return vista.dtmTeamTournament;
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
            }
            data.add(vector);
        }
    }

    /**
     * Muestra los atributos de un objeto seleccionado y los borra una vez se deselecciona
     *
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
        ListSelectionModel cellSelectionModel2 = vista.tablePlayer.getSelectionModel();
        cellSelectionModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tablePlayer.getSelectionModel())) {
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
        vista.tableTeam.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel3 = vista.tableTeam.getSelectionModel();
        cellSelectionModel3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel3.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tableTeam.getSelectionModel())) {
                        int row = vista.tableTeam.getSelectedRow();
                        vista.txtTeamName.setText(String.valueOf(vista.tableTeam.getValueAt(row, 1)));
                        updatePlayerTeam();
                        vista.btnExportLogo.setVisible(true);
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !update) {
                        if (e.getSource().equals(vista.tablePrize.getSelectionModel())) {
                            clearFieldPrizes();
                        } else if (e.getSource().equals(vista.tablePlayer.getSelectionModel())) {
                            clearFieldPlayer();
                        } else if (e.getSource().equals(vista.tableTournament.getSelectionModel())) {
                            clearFieldTournament();
                        }
                    }
                }else {
                    vista.btnExportLogo.setVisible(false);
                }
                if (isAdmin) {
                    vista.btnAddPlayerTeam.setEnabled(true);
                }

            }
        });
        vista.tableTournament.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel4 = vista.tableTournament.getSelectionModel();
        cellSelectionModel4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel4.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tableTournament.getSelectionModel())) {
                        int row = vista.tableTournament.getSelectedRow();
                        vista.txtTournamentName.setText(String.valueOf(vista.tableTournament.getValueAt(row, 1)));
                        vista.comboPrize.setSelectedItem(String.valueOf(vista.tableTournament.getValueAt(row, 2)));
                        if (isAdmin) {
                            vista.btnAddTeamTournament.setEnabled(true);
                        }
                        updateTeamTournament();
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                        if (e.getSource().equals(vista.tablePrize.getSelectionModel())) {
                            clearFieldPrizes();
                        } else if (e.getSource().equals(vista.tablePlayer.getSelectionModel())) {
                            clearFieldPlayer();
                        } else if (e.getSource().equals(vista.tableTeam.getSelectionModel())) {
                            clearFieldTeam();
                        }
                    }
                }
            }
        });
        vista.tablePlayerTeam.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel5 = vista.tablePlayerTeam.getSelectionModel();
        cellSelectionModel5.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel5.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tablePlayerTeam.getSelectionModel())) {
                        int row = vista.tablePlayerTeam.getSelectedRow();
                        if (isAdmin) {
                            vista.btnDelPlayerTeam.setEnabled(true);
                        }
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                        if (e.getSource().equals(vista.tablePrize.getSelectionModel())) {
                            clearFieldPrizes();
                        } else if (e.getSource().equals(vista.tablePlayer.getSelectionModel())) {
                            clearFieldPlayer();
                        } else if (e.getSource().equals(vista.tableTeam.getSelectionModel())) {
                            clearFieldTeam();
                        }
                    }
                } else {
                    vista.btnDelPlayerTeam.setEnabled(false);
                }
            }

        });
        vista.tableTeamTournament.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel6 = vista.tableTeamTournament.getSelectionModel();
        cellSelectionModel6.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel6.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tableTeamTournament.getSelectionModel())) {
                        int row = vista.tableTeamTournament.getSelectedRow();
                        if (isAdmin) {
                            vista.btnDeleteTeamTournament.setEnabled(true);
                        }
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                        if (e.getSource().equals(vista.tablePrize.getSelectionModel())) {
                            clearFieldPrizes();
                        } else if (e.getSource().equals(vista.tablePlayer.getSelectionModel())) {
                            clearFieldPlayer();
                        } else if (e.getSource().equals(vista.tableTeam.getSelectionModel())) {
                            clearFieldTeam();
                        }
                    }
                } else {
                    vista.btnDeleteTeamTournament.setEnabled(false);
                }
            }

        });
    }

    /**
     * Handle the selection of a row in a table
     *
     * @param e the event that characterizes the change.
     */
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

            } else if (e.getSource().equals(vista.tablePlayerTeam.getSelectionModel())) {
                int row = vista.tablePlayerTeam.getSelectedRow();
            } else if (e.getSource().equals(vista.tableTeamTournament.getSelectionModel())) {
                int row = vista.tableTeamTournament.getSelectedRow();
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
            } else if (e.getSource().equals(vista.tableTeamTournament.getSelectionModel())) {
            } else if (e.getSource().equals(vista.tableTournament.getSelectionModel())) {
                clearFieldTournament();
            }
        }
    }

    /**
     * Limpiar los campos de login
     */
    public void clearFieldLogin() {
        login.txtEmailLogin.setText("");
        login.passfieldLogin.setText("");
    }

    /**
     * Método que se encarga de procesar los eventos para logearse como administrador
     */
    public void loginAsAdmin() {
        login.setVisible(false);
        vista.setVisible(true);
        this.isAdmin = true;
        setEnabledAllButtons(true);
        clearFieldLogin();

    }

    /**
     * Método que se encarga de procesar los eventos para logearse como usuario
     */
    public void loginAsUser() {
        this.isAdmin = false;
        login.setVisible(false);
        vista.setVisible(true);
        setEnabledAllButtons(false);
        clearFieldLogin();
    }

    /**
     * Método que se encarga de procesar los eventos para logearse
     *
     * @param state
     */
    public void setEnabledAllButtons(boolean state) {
        vista.btnAddTeam.setEnabled(state);
        vista.btnAddPrize.setEnabled(state);
        vista.btnAddTournament.setEnabled(state);
        vista.btnAddPlayer.setEnabled(state);
        vista.btnDelPlayer.setEnabled(state);
        vista.btnDelPrize.setEnabled(state);
        vista.btnDelTournametn.setEnabled(state);
        vista.btnDelTeam.setEnabled(state);
        ;
        vista.btnModPlayer.setEnabled(state);
        vista.btnModPrize.setEnabled(state);
        vista.btnModTournament.setEnabled(state);
        vista.btnModTeam.setEnabled(state);
        vista.btnImportLogo.setEnabled(state);
    }
}
