import MVC.*;

public class Main {
    public static void main(String[] args) {
       Vista vista = new Vista();
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(vista, modelo);
       // Login login = new Login();
       // Vista vista = new Vista();
        //  System.out.println("pass: "+Util.getSha256("admin"));
        //System.out.println("user: "+Util.getSha256("user"));
    }
}
