package donnees;

import java.io.Serializable;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * POJO Sportif avec annotations JPA
 */
@Entity
@Table(name = "sportif")
@NamedQueries({
    @NamedQuery(name = "Sportif.findAll", query = "SELECT s FROM Sportif s"),
    @NamedQuery(name = "Sportif.findByCodeSportif", query = "SELECT s FROM Sportif s WHERE s.codeSportif = :codeSportif"),
    @NamedQuery(name = "Sportif.findByNom", query = "SELECT s FROM Sportif s WHERE s.nom = :nom"),
    @NamedQuery(name = "Sportif.findByRue", query = "SELECT s FROM Sportif s WHERE s.rue = :rue"),
    @NamedQuery(name = "Sportif.findByVille", query = "SELECT s FROM Sportif s WHERE s.ville = :ville"),
    @NamedQuery(name = "Sportif.findByCodePostal", query = "SELECT s FROM Sportif s WHERE s.codePostal = :codePostal")})
public class Sportif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "code_sportif")
    private int codeSportif;

    @Column(name = "nom")
    private String nom;

    @Column(name = "rue")
    private String rue;

    @Column(name = "ville")
    private String ville;

    @Column(name = "code_postal")
    private String codePostal;

    @JoinTable(name = "pratique", joinColumns = {
        @JoinColumn(name = "code_sportif", referencedColumnName = "code_sportif")}, inverseJoinColumns = {
        @JoinColumn(name = "code_discipline", referencedColumnName = "code_discipline")})
    @ManyToMany
    private Set<Discipline> disciplines; // Renommé 'disciplines' pour la cohérence avec le package 'donnees'

    public Sportif() {
    }

    public int getCodeSportif() {
        return codeSportif;
    }

    public void setCodeSportif(int codeSportif) {
        this.codeSportif = codeSportif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.codeSportif;
        hash = 29 * hash + Objects.hashCode(this.nom);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Sportif other = (Sportif) obj;
        if (this.codeSportif != other.codeSportif) return false;
        return Objects.equals(this.nom, other.nom);
    }

    @Override
    public String toString() {
        return "Sportif{" + "codeSportif=" + codeSportif + ", nom=" + nom + '}';
    }
}