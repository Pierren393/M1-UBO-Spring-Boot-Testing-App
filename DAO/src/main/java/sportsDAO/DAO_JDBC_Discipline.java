package sportsDAO;

import donnees.Discipline;
import donnees.Sport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * DAO pour la classe/table Discipline avec impl�mentation en JDBC.
 * 
 * @author Eric
 */
class DAO_JDBC_Discipline extends DAO<Discipline> {

	private Connection connection = null;

	@Override
	public Discipline find(int id) throws DAOException {
		try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("select * from discipline where code_discipline=" + id);
            if (res.next()) {
                Discipline discipline = new Discipline();
                discipline.setCodeDiscipline(res.getInt(1));
                discipline.setIntitule(res.getString(2));
                int codeSport = res.getInt(3);
				DAO_JDBC_Sport Sport = new DAO_JDBC_Sport();
				discipline.setSport(Sport.find(codeSport));       
                return discipline;
            } else {
                throw new DAOException("La discipline d'identifiant " + id + " n'existe pas");
            }
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
	}

	@Override
	public void create(Discipline disc) throws DAOException {
		try {
			Statement req = connection.createStatement();
			ResultSet res = req.executeQuery("select max(code_discipline) from discipline");
			res.next();
			int codeDisc = res.getInt(1);
			codeDisc++;
			disc.setCodeDiscipline(codeDisc);
			
			PreparedStatement reqParam = connection.prepareStatement("insert into discipline values (?,?,?)");
	        reqParam.setInt(1, disc.getCodeDiscipline());
	        reqParam.setString(2, disc.getIntitule());
	        reqParam.setInt(3,disc.getSport().getCodeSport());
			req = connection.createStatement();
			int nb = req.executeUpdate("insert into discipline values (" + codeDisc + " , '" + disc.getIntitule()
					+ "' , " + disc.getSport().getCodeSport() + ")");
		} catch (Exception e) {
			throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
		}
	}

	@Override
	public void update(Discipline data) throws DAOException {
		try {
			PreparedStatement reqParam = connection.prepareStatement("update Discipline set intitule = ? where code_discipline = ?");
			reqParam.setString(1, data.getIntitule());
			reqParam.setInt(2, data.getCodeDiscipline());
		} catch (Exception e) {
			throw new DAOException("Problème technique (" + e.getMessage() + ")");
		}
	}

	@Override
	public void delete(Discipline data) throws DAOException {
		   // on efface les disciplines du sport avant d'effacer le sport
//        PreparedStatement reqParam = connection.prepareStatement("delete from discipline where code_sport = ?");
//        reqParam.setInt(1, data.getCodeSport());
//        int nb = reqParam.executeUpdate();
//
//        // on efface le sport
//        reqParam = connection.prepareStatement("delete from sport where code_sport = ?");
//        reqParam.setInt(1, sport.getCodeSport());
//        nb = reqParam.executeUpdate();
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public DAO_JDBC_Discipline() throws DAOException {
		super();
		this.connection = SQLConnection.getConnection();
	}

}
