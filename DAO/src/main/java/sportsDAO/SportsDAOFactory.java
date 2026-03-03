package sportsDAO;

import donnees.Discipline;
import donnees.Sport;

/**
 * Fabrique abstraite de DAO pour le schéma sports
 * @author Eric
 */
public abstract class SportsDAOFactory {
    
    /**
     * @return le DAO pour la classe/table Sport.
     * @throws DAOException en cas de problème
     */
    public abstract DAO<Sport> getDAOSport() throws DAOException;
    
    /**
     * @return le DAO pour la classe/table Discipline.
     * @throws DAOException en cas de problème
     */
    public abstract DAO<Discipline> getDAODiscipline() throws DAOException;
}
