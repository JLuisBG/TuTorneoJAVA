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
    JLabel Pass;

    JMenuItem conexionItem;
    JMenuItem salirItem;

    public  Login(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        int width = screenSize.width * 2 / 3; // two-thirds of the screen width
        int height = screenSize.height * 2 / 3; // two-thirds of the screen height
        this.setSize(new Dimension(width, height));
        Email.setFont(new Font(Pass.getName(), Font.PLAIN, 20));
        Pass.setFont(new Font(Pass.getName(), Font.PLAIN, 20));
        this.setLocationRelativeTo(null);
        crearMenu();
        //setLogo();
    }
    private void crearMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        salirItem = new JMenuItem("Salir");
        salirItem.setActionCommand("Salir");
        menu.add(conexionItem);
        menu.add(salirItem);
        barra.add(menu);
        this.setJMenuBar(barra);
    }

    /*private void setLogo() {
        ImageIcon icon = new ImageIcon("src/main/resources/logo.png");
        Image img = icon.getImage();
        int width = this.getWidth()/4 ;
        Image newImg = img.getScaledInstance(width, this.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);
        logo.setIcon(newImc);
    }*/
}
