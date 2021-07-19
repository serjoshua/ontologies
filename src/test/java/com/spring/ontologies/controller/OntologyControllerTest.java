package com.spring.ontologies.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ontologies.dto.OntologyDTO;
import com.spring.ontologies.mapper.OntologyMapper;
import com.spring.ontologies.service.OntologyService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author serjoshua
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(OntologyController.class)
public class OntologyControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OntologyService ontologyService;

    @MockBean
    private OntologyMapper ontologyMapper;

    @Test
    public void createOntology_thenSuccess() throws Exception {
        List<String> definitionProperties = new ArrayList<>();
        definitionProperties.add("http://www.ebi.ac.uk/efo/definition");
        definitionProperties.add("http://purl.obolibrary.org/obo/IAO_0000115");
        List<String> synonymProperties = new ArrayList<>();
        synonymProperties.add("http://www.ebi.ac.uk/efo/alternative_term");
        synonymProperties.add("http://www.geneontology.org/formats/oboInOwl#hasExactSynonym");
        String ontologyId = "ot1";
        String title = "Ontology Sample 1";
        String description = "This is a sample ontology";

        final OntologyDTO ontologyDTORequest = new OntologyDTO();
        ontologyDTORequest.setOntologyId(ontologyId);
        ontologyDTORequest.setTitle(title);
        ontologyDTORequest.setDescription(description);
        ontologyDTORequest.setDefinitionProperties(definitionProperties);
        ontologyDTORequest.setSynonymProperties(synonymProperties);

        final OntologyDTO ontologyDTOResponse = new OntologyDTO();
        ontologyDTOResponse.setOntologyId(ontologyId);
        ontologyDTOResponse.setTitle(title);
        ontologyDTOResponse.setDescription(description);
        ontologyDTOResponse.setDefinitionProperties(definitionProperties);
        ontologyDTOResponse.setSynonymProperties(synonymProperties);

        when(this.ontologyService.saveOntology(ontologyDTORequest))
            .thenReturn(ontologyDTOResponse);

        this.mockMvc
            .perform(post("/v1/ontology/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ontologyDTORequest)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("ontologyId").value(ontologyDTORequest.getOntologyId()))
            .andExpect(jsonPath("title").value(ontologyDTORequest.getTitle()))
            .andExpect(jsonPath("description").value(ontologyDTORequest.getDescription()))
            .andExpect(
                jsonPath("definitionProperties[0]")
                    .value(ontologyDTORequest.getDefinitionProperties().get(0)))
            .andExpect(jsonPath("definitionProperties[1]")
                .value(ontologyDTORequest.getDefinitionProperties().get(1)))
            .andExpect(
                jsonPath("synonymProperties[0]")
                    .value(ontologyDTORequest.getSynonymProperties().get(0)))
            .andExpect(jsonPath("synonymProperties[1]")
                .value(ontologyDTORequest.getSynonymProperties().get(1)));
    }

    @Test
    public void createOntology_withoutId_thenFail() throws Exception {
        String title = "Ontology Sample 1";
        String description = "This is a sample ontology";

        final OntologyDTO ontologyDTORequest = new OntologyDTO();
        ontologyDTORequest.setOntologyId(null);
        ontologyDTORequest.setTitle(title);
        ontologyDTORequest.setDescription(description);
        ontologyDTORequest.setDefinitionProperties(null);
        ontologyDTORequest.setSynonymProperties(null);

        when(this.ontologyService.saveOntology(ontologyDTORequest))
            .thenThrow(new IllegalArgumentException("Ontology ID is required"));

        this.mockMvc
            .perform(post("/v1/ontology/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ontologyDTORequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("ontologyId").doesNotExist())
            .andExpect(jsonPath("title").doesNotExist());
    }

    @Test
    public void createOntology_withoutTitle_thenFail() throws Exception {
        String ontologyId = "ot1";
        String description = "This is a sample ontology";

        final OntologyDTO ontologyDTORequest = new OntologyDTO();
        ontologyDTORequest.setOntologyId(ontologyId);
        ontologyDTORequest.setTitle(null);
        ontologyDTORequest.setDescription(description);
        ontologyDTORequest.setDefinitionProperties(null);
        ontologyDTORequest.setSynonymProperties(null);

        when(this.ontologyService.saveOntology(ontologyDTORequest))
            .thenThrow(new IllegalArgumentException("Title is required"));

        this.mockMvc
            .perform(post("/v1/ontology/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ontologyDTORequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("ontologyId").doesNotExist())
            .andExpect(jsonPath("title").doesNotExist());
    }

    @Test
    public void getOntologyById_thenSuccess() throws Exception {
        List<String> definitionProperties = new ArrayList<>();
        definitionProperties.add("http://www.ebi.ac.uk/efo/definition");
        definitionProperties.add("http://purl.obolibrary.org/obo/IAO_0000115");
        List<String> synonymProperties = new ArrayList<>();
        synonymProperties.add("http://www.ebi.ac.uk/efo/alternative_term");
        synonymProperties.add("http://www.geneontology.org/formats/oboInOwl#hasExactSynonym");
        String ontologyId = "ot1";
        String title = "Ontology Sample 1";
        String description = "This is a sample ontology";

        final OntologyDTO ontologyDTOResponse = new OntologyDTO();
        ontologyDTOResponse.setOntologyId(ontologyId);
        ontologyDTOResponse.setTitle(title);
        ontologyDTOResponse.setDescription(description);
        ontologyDTOResponse.setDefinitionProperties(definitionProperties);
        ontologyDTOResponse.setSynonymProperties(synonymProperties);

        when(this.ontologyService.findOntology(ontologyId))
            .thenReturn(ontologyDTOResponse);

        this.mockMvc
            .perform(get("/v1/ontology/search/{id}", ontologyId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("ontologyId").value(ontologyDTOResponse.getOntologyId()))
            .andExpect(jsonPath("title").value(ontologyDTOResponse.getTitle()))
            .andExpect(jsonPath("description").value(ontologyDTOResponse.getDescription()))
            .andExpect(
                jsonPath("definitionProperties[0]")
                    .value(ontologyDTOResponse.getDefinitionProperties().get(0)))
            .andExpect(jsonPath("definitionProperties[1]")
                .value(ontologyDTOResponse.getDefinitionProperties().get(1)))
            .andExpect(
                jsonPath("synonymProperties[0]")
                    .value(ontologyDTOResponse.getSynonymProperties().get(0)))
            .andExpect(jsonPath("synonymProperties[1]")
                .value(ontologyDTOResponse.getSynonymProperties().get(1)));
    }

    @Test
    public void getOntologyById_thenFail() throws Exception {
        List<String> definitionProperties = new ArrayList<>();
        definitionProperties.add("http://www.ebi.ac.uk/efo/definition");
        definitionProperties.add("http://purl.obolibrary.org/obo/IAO_0000115");
        List<String> synonymProperties = new ArrayList<>();
        synonymProperties.add("http://www.ebi.ac.uk/efo/alternative_term");
        synonymProperties.add("http://www.geneontology.org/formats/oboInOwl#hasExactSynonym");
        String ontologyId = "ot1";
        String title = "Ontology Sample 1";
        String description = "This is a sample ontology";

        final OntologyDTO ontologyDTOResponse = new OntologyDTO();
        ontologyDTOResponse.setOntologyId(ontologyId);
        ontologyDTOResponse.setTitle(title);
        ontologyDTOResponse.setDescription(description);
        ontologyDTOResponse.setDefinitionProperties(definitionProperties);
        ontologyDTOResponse.setSynonymProperties(synonymProperties);

        when(this.ontologyService.findOntology(ontologyId))
            .thenReturn(ontologyDTOResponse);

        this.mockMvc
            .perform(get("/v1/ontology/search/{id}", "sad"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("ontologyId").doesNotExist())
            .andExpect(jsonPath("title").doesNotExist());
    }
}
