package com.spring.ontologies.domain;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author serjoshua
 */
@Document(collection = "ontology")
@ToString
@EqualsAndHashCode
public class Ontology {

    private String ontologyId;
    private String title;
    private String description;
    private List<String> definitionProperties;
    private List<String> synonymProperties;

    public Ontology() {
    }

    public Ontology(final String ontologyId, final String title, final String description,
        final List<String> definitionProperties, final List<String> synonymProperties) {
        this.ontologyId = ontologyId;
        this.title = title;
        this.description = description;
        this.definitionProperties = definitionProperties;
        this.synonymProperties = synonymProperties;
    }

    public String getOntologyId() {
        return ontologyId;
    }

    public void setOntologyId(String ontologyId) {
        this.ontologyId = ontologyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDefinitionProperties() {
        return definitionProperties;
    }

    public void setDefinitionProperties(List<String> definitionProperties) {
        this.definitionProperties = definitionProperties;
    }

    public List<String> getSynonymProperties() {
        return synonymProperties;
    }

    public void setSynonymProperties(List<String> synonymProperties) {
        this.synonymProperties = synonymProperties;
    }
}
