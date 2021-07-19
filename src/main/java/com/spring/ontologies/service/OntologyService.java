package com.spring.ontologies.service;

import com.spring.ontologies.dto.OntologyDTO;

/**
 * @author serjoshua
 */
public interface OntologyService {

    OntologyDTO findOntology(String ontologyId);

    OntologyDTO saveOntology(OntologyDTO ontologyDTO);
}
