package com.thamri.gestionstock.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data // L'annotation Lombok @Data génère automatiquement les méthodes getters, setters, toString(), equals() et hashCode() pour les champs de la classe.
@NoArgsConstructor // Lombok génère automatiquement un constructeur sans arguments.
@AllArgsConstructor // Lombok génère automatiquement un constructeur avec tous les arguments.
@EqualsAndHashCode(callSuper = true) // Lombok génère automatiquement les méthodes equals() et hashCode() en prenant également en compte les champs hérités de la classe parente AbstractEntity.
@Entity // Indique que cette classe est une entité JPA.
@Table(name="article") // Spécifie le nom de la table dans la base de données où cette entité sera stockée.

public class Article extends AbstractEntity {

    @Column(name="codearticle") // Spécifie que ce champ correspond à une colonne dans la table avec le nom "codearticle".
    //@Size(max= 16) // L'annotation Size n'est pas présente dans le code mais pourrait être utilisée pour définir une limite de taille pour la chaîne.
    private String codeArticle; // Champ représentant le code de l'article.

    @Column(name="designation") // Spécifie que ce champ correspond à une colonne dans la table avec le nom "designation".
    private String designation; // Champ représentant la désignation de l'article.

    @Column(name="prixunitaireht") // Spécifie que ce champ correspond à une colonne dans la table avec le nom "prixunitaireht".
    private BigDecimal prixUnitaireHt; // Champ représentant le prix unitaire hors taxe de l'article.

    @Column(name="tauxtva") // Spécifie que ce champ correspond à une colonne dans la table avec le nom "tauxtva".
    private BigDecimal tauxTva; // Champ représentant le taux de TVA de l'article.

    @Column(name="prixunitairettc") // Spécifie que ce champ correspond à une colonne dans la table avec le nom "prixunitairettc".
    private BigDecimal prixUnitaireTtc; // Champ représentant le prix unitaire toutes taxes comprises de l'article.

    @Column(name="photo") // Spécifie que ce champ correspond à une colonne dans la table avec le nom "photo".
    private String photo; // Champ représentant le chemin ou le nom de la photo de l'article.

    @ManyToOne // Indique une relation Many-to-One avec une autre entité.
    @JoinColumn(name="idcategory") // Spécifie la clé étrangère utilisée pour lier cet article à une catégorie.
    private Category category; // Champ représentant la catégorie à laquelle appartient l'article.
    
    @Column (name="identreprise")
    private Integer idEntreprise;
    
    @OneToMany(mappedBy = "article")
    private List<LigneVente> ligneVentes;
}
