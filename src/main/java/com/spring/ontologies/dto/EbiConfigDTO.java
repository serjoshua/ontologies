package com.spring.ontologies.dto;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author serjoshua
 */
@ToString
@EqualsAndHashCode
public class EbiConfigDTO {

    private String title;
    private String description;
    private List<String> definitionProperties;
    private List<String> synonymProperties;

    public EbiConfigDTO() {
    }

    public EbiConfigDTO(String title, String description,
        List<String> definitionProperties, List<String> synonymProperties) {
        this.title = title;
        this.description = description;
        this.definitionProperties = definitionProperties;
        this.synonymProperties = synonymProperties;
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
