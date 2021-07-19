package com.spring.ontologies.mapper;

import com.spring.ontologies.domain.Ontology;
import com.spring.ontologies.dto.OntologyDTO;
import org.mapstruct.Mapper;

/**
 * @author serjoshua
 */
@Mapper(componentModel = "spring")
public interface OntologyMapper {

    OntologyDTO toDto(Ontology ontology);

    Ontology toEntity(OntologyDTO dto);
}
