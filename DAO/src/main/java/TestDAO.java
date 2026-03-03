
/**
 * Fichier de test de fonctionnement de l'acc�s aux donn�es via des DAO.
 * @author Eric
 */
import donnees.*;
import sportsDAO.*;

public class TestDAO {

	public static void main(String argv[]) {
		try {

			// cr�ation des DAO via les fabriques
			SportsDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JDBC);
			DAO<Sport> daoSport = factory.getDAOSport();
			DAO<Discipline> daoDisc = factory.getDAODiscipline();

			// affichage du sport 1
			Sport sport = daoSport.find(1);
			System.out.println("Le sport d'id 1 est " + sport.getIntitule() + " et ses disciplines sont :");
			for (Discipline disc : sport.getDisciplines()) {
				System.out.println(" --> " + disc.getIntitule());
			}

			// cr�ation d'un sport et de disciplines
			Sport s = new Sport();
			s.setIntitule("Pétansedfdque");
			Discipline d1 = new Discipline();
			d1.setIntitule("Tridfdfdfgplette");
			d1.setSport(s);
			Discipline d2 = new Discipline();
			d2.setIntitule("Doubldfgdgdfdfgette");
			d2.setSport(s);
			s.addDiscipline(d1);
			s.addDiscipline(d2);

			// enregistrement des objets dans la BDD
			daoSport.create(s);
			daoDisc.create(d1);
			daoDisc.create(d2);

			System.out.println("La pétanque et ses disciplines ont été ajoutées dans la BDD.");

			// cr�ation d'une discipline et de son sport
			Discipline d = new Discipline();
			d.setIntitule("1km");
			Sport s1 = new Sport();
			s1.setIntitule("athletisme");
			s1.setCodeSport(s.getCodeSport());


			// enregistrement des objets dans la BDD
			daoSport.create(s);
			daoDisc.create(d1);
			daoDisc.create(d2);

			
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
