package com.thamri.gestionstock.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="category") // Spécifie le nom de la table dans la base de données où cette entité sera stockée.

public class Category extends AbstractEntity {

    @Column(name="code") // Spécifie que ce champ correspond à une colonne dans la table avec le nom "code".
    private String code; // Champ représentant le code de la catégorie.

    @Column(name="designation") // Spécifie que ce champ correspond à une colonne dans la table avec le nom "designation".
    private String designation; // Champ représentant la désignation de la catégorie.

    @Column(name = "identreprise")
    private Integer idEntreprise;
    
    @OneToMany(mappedBy = "category") // Indique une relation One-to-Many avec une autre entité (Article), où "category" est le nom de l'attribut dans la classe Article qui représente la catégorie.
    private List<Article> articles; // Champ représentant la liste d'articles associés à cette catégorie.
}
