package MVC;

import javax.swing.*;
import java.awt.*;

/**
 * Esta es una clase que me crea la ventana de login

 */
public class Login extends JFrame {
    private JPanel panel1;
    JLabel Email;
    JTextField txtEmailLogin;
    JPasswordField passfieldLogin;
    JButton btnLogin;
    JLabel logo;
    JLabel Pass;

    JMenuItem salirItemLogin;

    /**
     * Este es el constructor de la clase Login
     */
    public  Login(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        int width = screenSize.width *1/2; // two-thirds of the screen width
        int height = screenSize.height  *3/ 5; // two-thirds of the screen height
        this.setSize(new Dimension(width, height));
        Email.setFont(new Font(Pass.getName(), Font.PLAIN, 20));
        Pass.setFont(new Font(Pass.getName(), Font.PLAIN, 20));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        crearMenu();
        //setLogo();
    }

    /**
     * Este método me crea el menú de la ventana de login
     */
    private void crearMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        salirItemLogin = new JMenuItem("Salir");
        salirItemLogin.setActionCommand("SalirLogin");
        menu.add(salirItemLogin);
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
