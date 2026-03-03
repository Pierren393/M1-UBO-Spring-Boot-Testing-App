package sportsDAO;

import donnees.Discipline;
import donnees.Sport;

/**
 * Fabrique concrète de DAO pour le schéma relationnel sports avec une implémentation en JDBC.
 * @author Eric
 */
public class Sports_JDBC_DAOFactory extends SportsDAOFactory {
    
	/**
	 * Le DAO concret en JDBC pour la table Sport
	 */
    private DAO_JDBC_Sport daoSport = null;
    
    /**
     * Le DAO concret en JDBC pour la table Discipline
     */
    private DAO_JDBC_Discipline daoDiscipline = null;
        
    @Override
    public DAO<Sport> getDAOSport() throws DAOException {
        if (daoSport == null) daoSport = new DAO_JDBC_Sport();
        return daoSport;
    }

    @Override
    public DAO<Discipline> getDAODiscipline() throws DAOException {
        if (daoDiscipline == null) daoDiscipline = new DAO_JDBC_Discipline();
        return daoDiscipline;
    }
}
