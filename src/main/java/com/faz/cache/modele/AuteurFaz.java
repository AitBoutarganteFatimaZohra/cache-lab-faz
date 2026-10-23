package com.faz.cache.modele;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auteur_faz")
@NamedEntityGraph(
        name = "AuteurFaz.livres",
        attributeNodes = @NamedAttributeNode("livresFaz")
)
public class AuteurFaz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auteur_faz")
    private Long id;

    @Column(name = "nom_auteur_faz", length = 100)
    private String nomFaz;

    @OneToMany(mappedBy = "auteurFaz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LivreFaz> livresFaz = new ArrayList<>();

    public AuteurFaz() {}

    public AuteurFaz(String nomFaz) {
        this.nomFaz = nomFaz;
    }

    public void ajouterLivreFaz(LivreFaz livreFaz) {
        livresFaz.add(livreFaz);
        livreFaz.setAuteurFaz(this);
    }

    public void retirerLivreFaz(LivreFaz livreFaz) {
        livresFaz.remove(livreFaz);
        livreFaz.setAuteurFaz(null);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomFaz() { return nomFaz; }
    public void setNomFaz(String nomFaz) { this.nomFaz = nomFaz; }

    public List<LivreFaz> getLivresFaz() { return livresFaz; }
    public void setLivresFaz(List<LivreFaz> livresFaz) { this.livresFaz = livresFaz; }

    @Override
    public String toString() {
        return "AuteurFaz{id=" + id + ", nom='" + nomFaz + "'}";
    }
}