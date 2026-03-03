import donnees.Sport;
import donnees.Discipline;

import javax.persistence.*;

public class TestJPA {

   public static void main(String argv[]) throws Exception {

      // charge le gestionnaire d'entités lié à l'unité de persistance "SportsPU"
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("SportsPU");
      EntityManager em = emf.createEntityManager();
      System.out.println("PU chargée");

      // récupère le sport d'identifiant 1, affiche son intitulé et ses disciplines
      int cle = 1;
      Sport sport = em.find(Sport.class, cle);
      System.out.println("Le sport " + cle + " est " + sport.getIntitule());
      for (Discipline disc : sport.getDisciplines())
         System.out.println(" -> " + disc.getIntitule());
   }
}