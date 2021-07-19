package com.spring.ontologies.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.spring.ontologies.domain.Ontology;
import com.spring.ontologies.dto.OntologyDTO;
import com.spring.ontologies.mapper.OntologyMapper;
import com.spring.ontologies.repository.OntologyRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author serjoshua
 */
@ExtendWith(SpringExtension.class)
public class OntologyServiceTest {

    @InjectMocks
    private OntologyServiceImpl ontologyService;

    @Mock
    private OntologyRepository ontologyRepository;

    @Mock
    private OntologyMapper ontologyMapper;

    @Test
    public void saveOntology_thenReturnEntity() {
        List<String> definitionProperties = new ArrayList<>();
        definitionProperties.add("http://www.ebi.ac.uk/efo/definition");
        definitionProperties.add("http://purl.obolibrary.org/obo/IAO_0000115");
        List<String> synonymProperties = new ArrayList<>();
        synonymProperties.add("http://www.ebi.ac.uk/efo/alternative_term");
        synonymProperties.add("http://www.geneontology.org/formats/oboInOwl#hasExactSynonym");
        String ontologyId = "ot1";
        String title = "Ontology Sample 1";
        String description = "This is a sample ontology";

        final OntologyDTO ontologyDTO = new OntologyDTO();
        ontologyDTO.setOntologyId(ontologyId);
        ontologyDTO.setTitle(title);
        ontologyDTO.setDescription(description);
        ontologyDTO.setDefinitionProperties(definitionProperties);
        ontologyDTO.setSynonymProperties(synonymProperties);

        final OntologyDTO ontologyDTOResponse = new OntologyDTO();
        ontologyDTOResponse.setOntologyId(ontologyId);
        ontologyDTOResponse.setTitle(title);
        ontologyDTOResponse.setDescription(description);
        ontologyDTOResponse.setDefinitionProperties(definitionProperties);
        ontologyDTOResponse.setSynonymProperties(synonymProperties);

        final Ontology ontology = new Ontology();
        ontology.setOntologyId(ontologyId);
        ontology.setTitle(title);
        ontology.setDescription(description);
        ontology.setDefinitionProperties(definitionProperties);
        ontology.setSynonymProperties(synonymProperties);

        final Ontology ontologyResponse = new Ontology();
        ontologyResponse.setOntologyId(ontologyId);
        ontologyResponse.setTitle(title);
        ontologyResponse.setDescription(description);
        ontologyResponse.setDefinitionProperties(definitionProperties);
        ontologyResponse.setSynonymProperties(synonymProperties);

        when(this.ontologyMapper.toEntity(ontologyDTO)).thenReturn(ontology);
        when(this.ontologyRepository.save(ontology)).thenReturn(ontologyResponse);
        when(this.ontologyMapper.toDto(ontologyResponse)).thenReturn(ontologyDTOResponse);

        assertNotNull(ontologyService.saveOntology(ontologyDTO));
        assertEquals(ontologyDTO.getOntologyId(), ontologyDTOResponse.getOntologyId());
        assertEquals(ontologyDTO.getTitle(), ontologyDTOResponse.getTitle());
    }

    @Test
    public void saveOntology_withoutId_thenThrowException() {
        String title = "Ontology Sample 1";
        String description = "This is a sample ontology";

        final OntologyDTO ontologyDTO = new OntologyDTO();
        ontologyDTO.setOntologyId(null);
        ontologyDTO.setTitle(title);
        ontologyDTO.setDescription(description);
        ontologyDTO.setDefinitionProperties(null);
        ontologyDTO.setSynonymProperties(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ontologyService.saveOntology(ontologyDTO);
        });

        String expectedMessage = "Ontology ID is required";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void saveOntology_withoutTile_thenThrowException() {
        String ontologyId = "ot1";
        String description = "This is a sample ontology";

        final OntologyDTO ontologyDTO = new OntologyDTO();
        ontologyDTO.setOntologyId(ontologyId);
        ontologyDTO.setTitle(null);
        ontologyDTO.setDescription(description);
        ontologyDTO.setDefinitionProperties(null);
        ontologyDTO.setSynonymProperties(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ontologyService.saveOntology(ontologyDTO);
        });

        String expectedMessage = "Title is required";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void findOntologyById_thenSuccess() throws Exception {
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

        final Ontology ontologyResponse = new Ontology();
        ontologyResponse.setOntologyId(ontologyId);
        ontologyResponse.setTitle(title);
        ontologyResponse.setDescription(description);
        ontologyResponse.setDefinitionProperties(definitionProperties);
        ontologyResponse.setSynonymProperties(synonymProperties);

        when(this.ontologyRepository.findByOntologyId(ontologyId)).thenReturn(ontologyResponse);
        when(this.ontologyMapper.toDto(ontologyResponse)).thenReturn(ontologyDTOResponse);

        assertNotNull(ontologyService.findOntology(ontologyId));
        assertEquals(ontologyId, ontologyDTOResponse.getOntologyId());
    }

    @Test
    public void findOntologyById_thenFail() throws Exception {
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

        when(this.ontologyRepository.findByOntologyId("ot2")).thenReturn(null);
        when(this.ontologyMapper.toDto(null)).thenReturn(null);

        assertNull(this.ontologyService.findOntology("ot2"));
        assertNotEquals(ontologyDTOResponse.getOntologyId(), "ot2");
    }
}
