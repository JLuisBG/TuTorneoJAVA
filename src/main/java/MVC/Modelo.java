package MVC;

import BBDD.*;
import org.hibernate.SessionFactory;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Modelo {
    SessionFactory sessionFactory;
    private String ip;
    private String user;
    private String password;
    private String adminPassword;



    public String getIp() {
        return ip;
    }
    public String getUser() {
        return user;
    }
    public String getPassword() {
        return password;
    }
    public String getAdminPassword() {
        return adminPassword;
    }

    private Connection conexion;
    public Modelo() {
        getPropValues();
    }
    private void getPropValues() {
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = new FileInputStream(propFileName);

            prop.load(inputStream);
            ip = prop.getProperty("ip");
            user = prop.getProperty("user");
            password = prop.getProperty("pass");
            adminPassword = prop.getProperty("admin");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    void conectar() {

        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://"+ip+":3306/tutorneo",user, password);
        } catch (SQLException sqle) {
            try {
                conexion = DriverManager.getConnection(
                        "jdbc:mysql://"+ip+":3306/",user, password);

                PreparedStatement statement = null;

                String code = leerFichero();
                String[] query = code.split("--");
                for (String aQuery : query) {
                    statement = conexion.prepareStatement(aQuery);
                    statement.executeUpdate();
                }
                assert statement != null;
                statement.close();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String leerFichero() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("basedatos_java.sql")) ;
        String linea;
        StringBuilder stringBuilder = new StringBuilder();
        while ((linea = reader.readLine()) != null) {
            stringBuilder.append(linea);
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    void desconectar() {
        try {
            conexion.close();
            conexion = null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Actualiza las propiedades pasadas por parámetro del archivo de propiedades
     *
     * @param ip   ip de la bbdd
     * @param user user de la bbdd
     * @param pass contraseña de la bbdd
     * @param adminPass contraseña del administrador
     */
    void setPropValues(String ip, String user, String pass, String adminPass) {
        try {
            Properties prop = new Properties();
            prop.setProperty("ip", ip);
            prop.setProperty("user", user);
            prop.setProperty("pass", pass);
            prop.setProperty("admin", adminPass);
            OutputStream out = new FileOutputStream("config.properties");
            prop.store(out, null);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.ip = ip;
        this.user = user;
        this.password = pass;
        this.adminPassword = adminPass;
    }
    public void altaPrize(Prize nuevoPrize) {
        String sentenciaSql = "INSERT INTO prize (prizenumber, prizename, prizeamount, prizepercentage) VALUES (?, ?, ?, ?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, nuevoPrize.getPrizenumber());
            sentencia.setString(2, nuevoPrize.getPrizename());
            sentencia.setFloat(3, nuevoPrize.getPrizeamount());
            sentencia.setFloat(4, nuevoPrize.getPrizepercentage());
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    public void altaPlayer(Player nuevoPlayer) {
        //Obtengo una session a partir de la factoria de sesiones
        org.hibernate.Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.save(nuevoPlayer);
        sesion.getTransaction().commit();
        sesion.close();
    }
    public void altaTeam(Team nuevoTeam) {
        //Obtengo una session a partir de la factoria de sesiones
        org.hibernate.Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.save(nuevoTeam);
        sesion.getTransaction().commit();
        sesion.close();
    }
    public void altaTournament(Tournament nuevoTournament) {
        //Obtengo una session a partir de la factoria de sesiones
        org.hibernate.Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.save(nuevoTournament);
        sesion.getTransaction().commit();
        sesion.close();
    }
    public void modificarPrize(Prize prizeSeleccion) {
        org.hibernate.Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.saveOrUpdate(prizeSeleccion);
        sesion.getTransaction().commit();
        sesion.close();
    }
    public void modificarPlayer(Player playerSeleccion) {
        org.hibernate.Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.saveOrUpdate(playerSeleccion);
        sesion.getTransaction().commit();
        sesion.close();
    }
    public void modificarTeam(Team teamSeleccion) {
        org.hibernate.Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.saveOrUpdate(teamSeleccion);
        sesion.getTransaction().commit();
        sesion.close();
    }
    public void modificarTournament(Tournament tournamentSeleccion) {
        org.hibernate.Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.saveOrUpdate(tournamentSeleccion);
        sesion.getTransaction().commit();
        sesion.close();
    }
    public void borrarPrize(Prize prizeBorrado) {
        org.hibernate.Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.delete(prizeBorrado);
        sesion.getTransaction().commit();
        sesion.close();
    }
    public void borrarPlayer(Player playerBorrado) {
        org.hibernate.Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.delete(playerBorrado);
        sesion.getTransaction().commit();
        sesion.close();
    }
    public void borrarTeam(Team teamBorrado) {
        org.hibernate.Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.delete(teamBorrado);
        sesion.getTransaction().commit();
        sesion.close();
    }
    public void borrarTournament(Tournament tournamentBorrado) {
        org.hibernate.Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.delete(tournamentBorrado);
        sesion.getTransaction().commit();
        sesion.close();
    }

    public ArrayList<Player> getPlayer() {
        org.hibernate.Session sesion = sessionFactory.openSession();
        org.hibernate.query.Query query = sesion.createQuery("FROM player");
        ArrayList<Player> lista = (ArrayList<Player>)query.getResultList();
        sesion.close();
        return lista;
    }

    public ArrayList<Team> getTeam() {
        org.hibernate.Session sesion = sessionFactory.openSession();
        org.hibernate.query.Query query = sesion.createQuery("FROM team");
        ArrayList<Team> lista = (ArrayList<Team>)query.getResultList();
        sesion.close();
        return lista;
    }
    public ArrayList<Tournament> getTournament() {
        org.hibernate.Session sesion = sessionFactory.openSession();
        org.hibernate.query.Query query = sesion.createQuery("FROM tournament");
        ArrayList<Tournament> lista = (ArrayList<Tournament>)query.getResultList();
        sesion.close();
        return lista;
    }
    public  ArrayList<Prize> getPrize() {
        org.hibernate.Session sesion = sessionFactory.openSession();
        org.hibernate.query.Query query = sesion.createQuery("FROM prize");
        ArrayList<Prize> lista = (ArrayList<Prize>)query.getResultList();
        sesion.close();
        return lista;
    }

}
