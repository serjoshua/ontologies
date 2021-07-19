package com.spring.ontologies.service;

import com.spring.ontologies.domain.Ontology;
import com.spring.ontologies.dto.EbiOntologyDTO;
import com.spring.ontologies.dto.OntologyDTO;
import com.spring.ontologies.mapper.OntologyMapper;
import com.spring.ontologies.repository.OntologyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author serjoshua
 */
@Slf4j
@Service
public class OntologyServiceImpl implements OntologyService {

    private final OntologyRepository ontologyRepository;
    private final OntologyMapper ontologyMapper;

    private RestTemplate restTemplate;

    public OntologyServiceImpl(OntologyRepository ontologyRepository,
        OntologyMapper ontologyMapper) {
        this.ontologyRepository = ontologyRepository;
        this.ontologyMapper = ontologyMapper;
    }

    @Override
    public OntologyDTO findOntology(String ontologyId) {
        Ontology ontology = ontologyRepository.findByOntologyId(ontologyId);
        log.debug("Returned ontology: " + ontology);
        OntologyDTO ontologyDTO = ontologyMapper.toDto(ontology);
        if (ontologyDTO == null) {
            ontologyDTO = findOntologyInEbi(ontologyId);
            log.debug("Returned ontology from EBI API: " + ontologyDTO);
        }

        return ontologyDTO;
    }

    private OntologyDTO findOntologyInEbi(String ontologyId) {
        String uri = "https://www.ebi.ac.uk/ols/api/ontologies/" + ontologyId;
        restTemplate = new RestTemplate();
        EbiOntologyDTO ontologyDtoFromTemp;
        try {
            ontologyDtoFromTemp = restTemplate.getForObject(uri, EbiOntologyDTO.class);
            log.debug("Returned ontology from rest template: " + ontologyDtoFromTemp);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return null;
        }

        if (ontologyDtoFromTemp == null || ontologyDtoFromTemp.getOntologyId() == null
            || ontologyDtoFromTemp.getOntologyId().isEmpty()) {
            return null;
        } else {
            OntologyDTO ontologyDTO = new OntologyDTO();
            ontologyDTO.setOntologyId(ontologyDtoFromTemp.getOntologyId());
            ontologyDTO.setTitle(ontologyDtoFromTemp.getConfig().getTitle());
            ontologyDTO.setDescription(ontologyDtoFromTemp.getConfig().getDescription());
            ontologyDTO.setDefinitionProperties(
                ontologyDtoFromTemp.getConfig().getDefinitionProperties());
            ontologyDTO
                .setSynonymProperties(ontologyDtoFromTemp.getConfig().getSynonymProperties());

            return ontologyDTO;
        }
    }

    @Override
    public OntologyDTO saveOntology(OntologyDTO ontologyDTO) {
        if (ontologyDTO.getOntologyId() == null || ontologyDTO.getOntologyId().isEmpty()) {
            throw new IllegalArgumentException("Ontology ID is required");
        }
        if (ontologyDTO.getTitle() == null || ontologyDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        Ontology ontology = ontologyMapper.toEntity(ontologyDTO);
        Ontology savedOntology = ontologyRepository.save(ontology);
        log.debug("Saved ontology: " + savedOntology);
        OntologyDTO savedOntologyDTO = ontologyMapper.toDto(savedOntology);

        return savedOntologyDTO;
    }
}
