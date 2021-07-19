package com.spring.ontologies.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StreamUtils;

/**
 * @author serjoshua
 */
@JsonTest
@ExtendWith(SpringExtension.class)
public class OntologyTest {

    @MockBean
    private ObjectMapper mapper;

    private Ontology ontology;
    private String ontologyJson;

    private static final String ONTOLOGY_ID = "ot1";

    @Before
    public void setUp() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/json/ontologyTest.json");
        ontologyJson = StreamUtils.copyToString(inputStream, Charset.defaultCharset());

        List<String> definitionProperties = new ArrayList<>();
        definitionProperties.add("http://www.ebi.ac.uk/efo/definition");
        definitionProperties.add("http://purl.obolibrary.org/obo/IAO_0000115");

        List<String> synonymProperties = new ArrayList<>();
        synonymProperties.add("http://www.ebi.ac.uk/efo/alternative_term");
        synonymProperties.add("http://www.geneontology.org/formats/oboInOwl#hasExactSynonym");

        ontology.setOntologyId(ONTOLOGY_ID);
        ontology.setTitle("Ontology Sample 1");
        ontology.setDescription("This is a sample ontology");
        ontology.setDefinitionProperties(definitionProperties);
        ontology.setSynonymProperties(synonymProperties);
    }

    @Test
    public void serialize() throws JsonProcessingException, JSONException {
        String serializedData = mapper.writeValueAsString(ontology);

        JSONAssert.assertEquals(ontologyJson, serializedData, true);
    }

    @Test
    public void deserialize() throws JsonProcessingException {
        Ontology ontology = mapper.readValue(ontologyJson, Ontology.class);

        Assertions.assertNull(ontology); // TODO
    }
}
