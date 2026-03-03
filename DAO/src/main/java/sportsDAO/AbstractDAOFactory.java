package sportsDAO;

/**
 * Fabrique renvoyant une fabrique de DAO en fonction du support de persistance choisi.
 * @author Eric
 */
public class AbstractDAOFactory {

	/**
	 * Renvoie la fabrique concr�te de DAO en fonction du support de persistance.
	 * @param type le support de persistance � utiliser
	 * @return la fabrique de DAO pour ce type de persistance
	 */
    public static SportsDAOFactory getDAOFactory(PersistenceKind type) {
        if (type.equals(PersistenceKind.JDBC)) 
            return new Sports_JDBC_DAOFactory();
        if (type.equals(PersistenceKind.JDBC)) 
            return new Sports_JPA_DAOFactory();
        return null;
        }
    }
}
