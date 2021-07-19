package com.spring.ontologies.repository;

import com.spring.ontologies.domain.Ontology;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author serjoshua
 */
public interface OntologyRepository extends MongoRepository<Ontology, String> {

    Ontology findByOntologyId(String ontologyId);
}
