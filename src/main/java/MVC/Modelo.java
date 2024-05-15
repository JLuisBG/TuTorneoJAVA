package MVC;

import BBDD.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;

public class Modelo {
    SessionFactory sessionFactory;

    public void desconectar() {
        //Cierro la factoria de sessiones
        if(sessionFactory != null && sessionFactory.isOpen())
            sessionFactory.close();
    }

    public void conectar() {
        Configuration configuracion = new Configuration();
        //Cargo el fichero Hibernate.cfg.xml
        configuracion.configure("hibernate.cfg.xml");

        //Indico la clase mapeada con anotaciones
        configuracion.addAnnotatedClass(Admin.class);
        configuracion.addAnnotatedClass(Player.class);
        configuracion.addAnnotatedClass(Playerteam.class);
        configuracion.addAnnotatedClass(Team.class);
        configuracion.addAnnotatedClass(Tournament.class);
        configuracion.addAnnotatedClass(Teamtournament.class);
        configuracion.addAnnotatedClass(Teammatch.class);
        configuracion.addAnnotatedClass(Prize.class);


        //Creamos un objeto ServiceRegistry a partir de los parámetros de configuración
        //Esta clase se usa para gestionar y proveer de acceso a servicios
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().applySettings(
                configuracion.getProperties()).build();

        //finalmente creamos un objeto sessionfactory a partir de la configuracion y del registro de servicios
        sessionFactory = configuracion.buildSessionFactory(ssr);

    }
    public void altaPrize(Prize nuevoPrize) {
        //Obtengo una session a partir de la factoria de sesiones
        org.hibernate.Session sesion = sessionFactory.openSession();
        sesion.beginTransaction();
        sesion.save(nuevoPrize);
        sesion.getTransaction().commit();
        sesion.close();
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

    public ArrayList<Player> getPlayerTeam() {
        org.hibernate.Session sesion = sessionFactory.openSession();
        org.hibernate.query.Query query = sesion.createQuery("FROM Player");
        ArrayList<Player> lista = (ArrayList<Player>)query.getResultList();
        sesion.close();
        return lista;
    }

    public ArrayList<Team> getTeam() {
        org.hibernate.Session sesion = sessionFactory.openSession();
        org.hibernate.query.Query query = sesion.createQuery("FROM Team");
        ArrayList<Team> lista = (ArrayList<Team>)query.getResultList();
        sesion.close();
        return lista;
    }

}
