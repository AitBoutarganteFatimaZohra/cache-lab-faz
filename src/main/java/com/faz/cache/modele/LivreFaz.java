package com.faz.cache.modele;

import javax.persistence.*;

@Entity
@Table(name = "livre_faz")
@Cacheable
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class LivreFaz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livre_faz")
    private Long id;

    @Column(name = "titre_livre_faz", length = 200)
    private String titreFaz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auteur_faz_id")
    private AuteurFaz auteurFaz;

    public LivreFaz() {}

    public LivreFaz(String titreFaz) {
        this.titreFaz = titreFaz;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitreFaz() { return titreFaz; }
    public void setTitreFaz(String titreFaz) { this.titreFaz = titreFaz; }

    public AuteurFaz getAuteurFaz() { return auteurFaz; }
    public void setAuteurFaz(AuteurFaz auteurFaz) { this.auteurFaz = auteurFaz; }

    @Override
    public String toString() {
        return "LivreFaz{id=" + id + ", titre='" + titreFaz + "'}";
    }
}