package sportsDAO;

import donnees.Discipline;
import donnees.Sport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * DAO pour la classe/table Sport avec impl�mentation en JDBC.
 * @author Eric
 */
public class DAO_JDBC_Sport extends DAO<Sport> {

    private Connection connection = null;

    protected Set<Discipline> getDisciplinesSport(Sport sport) throws SQLException {
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("select * from discipline where code_sport=" + sport.getCodeSport());

        //System.out.println(" Les disciplines du sport " + sport.getIntitule());
        HashSet<Discipline> disciplines = new HashSet<>();
        Discipline disc;
        while (res.next()) {
            //System.out.println(" -> " + res.getString(2));
            disc = new Discipline(res.getInt(1), res.getString(2), sport);
            disciplines.add(disc);
        }
        return disciplines;
    }

    @Override
    public Sport find(int id) throws DAOException {
        try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("select * from sport where code_sport=" + id);
            if (res.next()) {
                Sport sport = new Sport();
                sport.setCodeSport(res.getInt(1));
                sport.setIntitule(res.getString(2));
                // reste � charger les disciplines : tr�s lourd !
                sport.setDisciplines(this.getDisciplinesSport(sport));
                return sport;
            } else {
                throw new DAOException("Le sport d'identifiant " + id + " n'existe pas");
            }
        } catch (Exception e) {
            throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
        }
    }

    @Override
    public void create(Sport sport) throws DAOException {
        try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("select max(code_sport) from sport");
            res.next();
            int codeSport = res.getInt(1);
            codeSport++;
            // positionne la cl� primaire g�n�r�e du sport
            // le contenu initial de codeSport n'est donc pas pris en compte
            sport.setCodeSport(codeSport);

            PreparedStatement reqParam = connection.prepareStatement("insert into sport values (?,?)");
            reqParam.setInt(1, sport.getCodeSport());
            reqParam.setString(2, sport.getIntitule());
          //  int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
        }
    }

    @Override
    public void update(Sport sport) throws DAOException {
        try {
            PreparedStatement reqParam = connection.prepareStatement("update sport set intitule = ? where code_sport = ?");
            reqParam.setString(1, sport.getIntitule());
            reqParam.setInt(2, sport.getCodeSport());
          //  int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
        }
    }

    @Override
    public void delete(Sport sport) throws DAOException {
        try {
            // on efface les disciplines du sport avant d'effacer le sport
            PreparedStatement reqParam = connection.prepareStatement("delete from discipline where code_sport = ?");
            reqParam.setInt(1, sport.getCodeSport());
       	    int nb = reqParam.executeUpdate();

            // on efface le sport
            reqParam = connection.prepareStatement("delete from sport where code_sport = ?");
            reqParam.setInt(1, sport.getCodeSport());
            nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
        }
    }

    public DAO_JDBC_Sport() throws DAOException {
    	super();
        this.connection = SQLConnection.getConnection();
    }
}
