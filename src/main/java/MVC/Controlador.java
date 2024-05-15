package MVC;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
public class Controlador implements ActionListener, ListSelectionListener {
    private Vista vista;
    private Modelo modelo;

    public Controlador(Vista vista, Modelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        addActionListeners(this);
        addListSelectionListener(this);
    }

    private void addListSelectionListener(Controlador controlador) {
        //TOOD: Fill Method
    }


    private void addActionListeners(Controlador controlador) {
        //TOOD: Fill Method
        vista.conexionItem.addActionListener(controlador);
        vista.salirItem.addActionListener(controlador);
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
                //TODO: Fill to make it takae the list of player, teams and tournaments
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
