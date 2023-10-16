package com.thamri.gestionstock.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data // L'annotation Lombok @Data génère automatiquement les méthodes getters, setters, toString(), equals() et hashCode() pour les champs de la classe.
//@Builder // L'annotation Lombok @Builder génère automatiquement un constructeur de type « builder » pour simplifier la création d'instances de la classe avec un modèle de construction fluide.
@NoArgsConstructor // Lombok génère automatiquement un constructeur sans arguments.
@AllArgsConstructor // Lombok génère automatiquement un constructeur avec tous les arguments.
@EqualsAndHashCode // Lombok génère automatiquement les méthodes equals() et hashCode() pour la classe.

@Embeddable // Indique que cette classe est une classe embarquée (embedded) qui peut être utilisée comme un composant dans d'autres entités JPA.

public class Adresse implements Serializable {

    @Column(name = "adresse1") // Spécifie que ce champ correspond à une colonne dans la base de données avec le nom "adresse1".
    private String adresse1; // Champ représentant la première ligne de l'adresse.

    @Column(name = "adresse2") // Spécifie que ce champ correspond à une colonne dans la base de données avec le nom "adresse2".
    private String adresse2; // Champ représentant la deuxième ligne de l'adresse.

    @Column(name = "ville") // Spécifie que ce champ correspond à une colonne dans la base de données avec le nom "ville".
    private String ville; // Champ représentant la ville de l'adresse.

    @Column(name = "codepostale") // Spécifie que ce champ correspond à une colonne dans la base de données avec le nom "codepostale".
    private String codePostale; // Champ représentant le code postal de l'adresse.

    @Column(name = "pays") // Spécifie que ce champ correspond à une colonne dans la base de données avec le nom "pays".
    private String pays; // Champ représentant le pays de l'adresse.
}
