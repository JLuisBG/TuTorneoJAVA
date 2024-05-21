package MVC;
import BBDD.*;
import org.hibernate.SessionFactory;



import java.io.*;
import java.sql.*;
import java.time.LocalDate;
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
            sentencia.setString(2, Herramientas.formatVarchar50(nuevoPrize.getPrizename()));
            sentencia.setFloat(3, nuevoPrize.getPrizeamount());
            sentencia.setFloat(4, Float.parseFloat(nuevoPrize.getPrizepercentage().toString().replaceAll("^\\s+|\\s+$", "")));
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
    public void altaPlayer(String pass, String email, boolean isEntryFeePaid, LocalDate birthdate, String telephoneno, String firstname, String lastname) {
        String sentenciaSql = "INSERT INTO players (pass, email, isentryfeepaid, birthdate, telephoneno, firstname, lastname) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;
        String password = Herramientas.getSha256(pass);
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, password);
            sentencia.setString(2, Herramientas.formatVarchar50(email) );
            sentencia.setBoolean(3, isEntryFeePaid);
            sentencia.setDate(4, Date.valueOf(birthdate));
            sentencia.setInt(5, Herramientas.formatString9(telephoneno));
            sentencia.setString(6, Herramientas.formatVarchar50(firstname));
            sentencia.setString(7, Herramientas.formatVarchar50(lastname));
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

    public ResultSet getPrize() throws SQLException {
        String sentenciaSql = "SELECT id as 'ID Prize', " +
                "prizenumber as 'Numero', " +
                "prizename as 'Nombre', " +
                "prizeamount as 'Cantidad'," +
                "prizepercentage as 'Porcentaje' " +
                "FROM prize";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = conexion.prepareStatement(sentenciaSql);
        resultado = sentencia.executeQuery();
        return resultado;
    }

    public ResultSet getPlayer() throws SQLException {
        String sentenciaSql = "SELECT * FROM players";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = conexion.prepareStatement(sentenciaSql);
        resultado = sentencia.executeQuery();
        return resultado;
    }
}
