package MVC;
import BBDD.*;
import org.hibernate.SessionFactory;



import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class Modelo {
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
    public void modPrize(int prizeId, int prizenumber, String prizename, float prizeamount, float prizepercentage) {
        String sentenciaSql = "UPDATE prize SET prizenumber = ?, prizename = ?, prizeamount = ?, prizepercentage = ? WHERE id = ?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, prizenumber);
            sentencia.setString(2, Herramientas.formatVarchar50(prizename));
            sentencia.setFloat(3, prizeamount);
            sentencia.setFloat(4, Float.parseFloat(String.valueOf(prizepercentage).replaceAll("^\\s+|\\s+$", "")));
            sentencia.setInt(5, prizeId);
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
    public void deletePrize(int prizeId) {
        String sentenciaSql = "DELETE FROM prize WHERE id = ?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, prizeId);
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

    public void deletePlayer(int id) {
        String sentenciaSql = "DELETE FROM players WHERE id = ?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, id);
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

    public void modPlayer(int id, String pass, String email, String firstname, String lastname, String telephone, boolean isEntryFeePaid, LocalDate birthDate) {
        String sentenciaSql = "UPDATE players set  pass = ?, email = ?, isentryfeepaid = ?, birthdate = ?, telephoneno = ?, firstname = ?, lastname = ? WHERE id = ?";
        PreparedStatement sentencia = null;
        String password = Herramientas.getSha256(pass);
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, password);
            sentencia.setString(2, Herramientas.formatVarchar50(email) );
            sentencia.setBoolean(3, isEntryFeePaid);
            sentencia.setDate(4, Date.valueOf(birthDate));
            sentencia.setInt(5, Herramientas.formatString9(telephone));
            sentencia.setString(6, Herramientas.formatVarchar50(firstname));
            sentencia.setString(7, Herramientas.formatVarchar50(lastname));
            sentencia.setInt(8, id);
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
    public void addTeam(String name, FileInputStream fin){
        String sentenciaSql = "INSERT INTO team (name, logo) VALUES (?, ?)";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, Herramientas.formatVarchar50(name));
            sentencia.setBinaryStream(2,fin,fin.available());
            sentencia.executeUpdate();
        } catch (SQLException | IOException sqle) {
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
    public ResultSet getTeam(){
        String sentenciaSql = "SELECT * FROM team";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            resultado = sentencia.executeQuery();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return resultado;
    }

    public void modTeam(int id, String nameText, FileInputStream fin) {
        String sentenciaSql = "UPDATE team SET name = ?, logo = ? WHERE id = ?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, Herramientas.formatVarchar50(nameText));
            sentencia.setBinaryStream(2,fin,fin.available());
            sentencia.setInt(3, id);
            sentencia.executeUpdate();
        } catch (SQLException | IOException sqle) {
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
    public void modTeam(int id, String nameText) {
        String sentenciaSql = "UPDATE team SET name = ? WHERE id = ?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, Herramientas.formatVarchar50(nameText));
            sentencia.setInt(2, id);
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

    public void deleteTeam(int i) {
        String sentenciaSql = "DELETE FROM team WHERE id = ?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, i);
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
}
