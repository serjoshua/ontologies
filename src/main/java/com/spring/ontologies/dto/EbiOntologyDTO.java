package com.spring.ontologies.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author serjoshua
 */
@ToString
@EqualsAndHashCode
public class EbiOntologyDTO {

    private String ontologyId;
    private EbiConfigDTO config;

    public EbiOntologyDTO() {
    }

    public EbiOntologyDTO(String ontologyId, EbiConfigDTO config) {
        this.ontologyId = ontologyId;
        this.config = config;
    }

    public String getOntologyId() {
        return ontologyId;
    }

    public void setOntologyId(String ontologyId) {
        this.ontologyId = ontologyId;
    }

    public EbiConfigDTO getConfig() {
        return config;
    }

    public void setConfig(EbiConfigDTO config) {
        this.config = config;
    }
}