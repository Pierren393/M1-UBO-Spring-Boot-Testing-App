package sportsDAO;

/**
 * DAO abstrait et g�n�rique pour tout type de donn�es.
 * 
 * @author Eric
 * @param <D> la classe param�trant le DAO
 */
public abstract class DAO<D> {

	/**
	 * Retourne � partir du support de persistance un objet en fonction de son
	 * identifiant.
	 * 
	 * @param id identifiant de l'objet
	 * @return l'instance de l'objet
	 * @throws DAOException en cas de probl�me
	 */
	public abstract D find(int id) throws DAOException;

	/**
	 * Rend persistant un objet qui n'avait pas encore de r�pr�sentation sur le
	 * support de persistance.
	 * 
	 * @param data l'objet � rendre persistant
	 * @throws DAOException en cas de probl�me
	 */
	public abstract void create(D data) throws DAOException;

	/**
	 * Met � jour le contenu correspondant � l'objet sur le support persistant
	 * (l'objet avait d�j� une repr�sentation sur le support persistant).
	 * 
	 * @param data l'objet modifi� dont le contenu est � mettre � jour
	 * @throws DAOException en cas de probl�me
	 */
	public abstract void update(D data) throws DAOException;

	/**
	 * Efface du support persistant le contenu �quivalent � l'objet.
	 * 
	 * @param data l'objet � supprimer
	 * @throws DAOException en cas de probl�me
	 */
	public abstract void delete(D data) throws DAOException;

	/**
	 * Construit le DAO pour la classe param�tr�e.
	 * 
	 * @throws DAOException en cas de probl�me
	 */
	public DAO() throws DAOException {
	}
}
