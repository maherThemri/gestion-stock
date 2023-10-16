package com.thamri.gestionstock.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data // L'annotation Lombok @Data génère automatiquement les méthodes getters, setters, toString(), equals(), et hashCode() pour les champs de la classe.
@MappedSuperclass // Indique que cette classe est une classe mère pour les entités JPA, elle ne sera pas mappée directement dans la base de données.
@EntityListeners(AuditingEntityListener.class) // Utilisé pour écouter les événements du cycle de vie des entités, comme la création et la mise à jour.

public class AbstractEntity implements Serializable {

    @Id // Indique que le champ 'id' est la clé primaire de l'entité.
    @GeneratedValue // Indique que la valeur de la clé primaire ('id') sera générée automatiquement par la base de données.

    private Integer id; // Champ représentant l'identifiant unique de l'entité.

    @CreatedDate // Indique que le champ 'creationDate' doit être automatiquement rempli avec la date et l'heure de création de l'entité.
    @Column(name = "creationDate", nullable = false, updatable = false) // Spécifie que ce champ correspond à une colonne dans la base de données avec le nom "creationDate", et qu'il ne peut pas être nul.
    @JsonIgnore // Indique que ce champ ne doit pas être inclus lors de la sérialisation en JSON, utile pour masquer ce détail dans les réponses JSON.

    private Instant creationDate; // Champ stockant la date et l'heure de création de l'entité.

    @LastModifiedDate // Indique que le champ 'lastModifiedDate' doit être automatiquement rempli avec la date et l'heure de la dernière modification de l'entité.
    @Column(name = "lastModifiedDate") // Spécifie que ce champ correspond à une colonne dans la base de données avec le nom "lastModifiedDate".
    @JsonIgnore // Indique que ce champ ne doit pas être inclus lors de la sérialisation en JSON, utile pour masquer ce détail dans les réponses JSON.

    private Instant lastModifiedDate; // Champ stockant la date et l'heure de la dernière modification de l'entité.

}
