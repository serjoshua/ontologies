package com.spring.ontologies.controller;

import com.spring.ontologies.dto.OntologyDTO;
import com.spring.ontologies.service.OntologyService;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author serjoshua
 */
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/v1/ontology")
public class OntologyController {

    private final OntologyService ontologyService;

    public OntologyController(OntologyService ontologyService) {
        this.ontologyService = ontologyService;
    }

    @PostMapping("/create")
    public ResponseEntity<OntologyDTO> createOntology(@RequestBody OntologyDTO ontologyDTO)
        throws URISyntaxException {
        log.debug("Request body - ontologyId: " + ontologyDTO.getOntologyId());
        log.debug("Request body - title: " + ontologyDTO.getTitle());
        OntologyDTO savedOntologyDTO;
        try {
            savedOntologyDTO = ontologyService.saveOntology(ontologyDTO);
        } catch (IllegalArgumentException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.created(new URI("/v1/ontology/create"))
            .body(savedOntologyDTO);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<OntologyDTO> getOntologyById(@PathVariable("id") String id) {
        log.debug("Path variable - id: " + id);
        OntologyDTO ontologyDTO = ontologyService.findOntology(id);
        if (ontologyDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(ontologyDTO);
    }
}
