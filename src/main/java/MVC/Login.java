package MVC;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JPanel panel1;
    JLabel Email;
    JTextField txtEmailLogin;
    JPasswordField passfieldLogin;
    JButton btnLogin;
    JLabel logo;

    JMenuItem conexionItem;
    JMenuItem salirItem;

    public  Login(){
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(this.getWidth()+800, this.getHeight()-200));
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
        this.setJMenuBar(barra);
    }

    private void setLogo() {
        ImageIcon icon = new ImageIcon("src/main/resources/logo.png");
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);
        logo.setIcon(newImc);
    }
}
