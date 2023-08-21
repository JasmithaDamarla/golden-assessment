package com.epam.integrationtest;

import com.epam.dto.AssociatesDTO;
import com.epam.dto.BatchesDTO;
import com.epam.entity.Associates;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssociateIntegrationTest {

    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost";
    private static RestTemplate restTemplate;
    @Autowired
    private AssocaiatesTestRepository associatesTestRepository;
    @Autowired
    private BatchesTestRepository batchesTestRepository;
    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/rd/associates");
    }

    @Test
    void testAddBatches() {
        BatchesDTO batchesDTO=new BatchesDTO(1,"name","practice",new Date(),new Date());
        AssociatesDTO associatesDTO = new AssociatesDTO(1,"Name","name@gmail.com","F","clg","ACTIVE",batchesDTO);
        AssociatesDTO response = restTemplate.postForObject(baseUrl, associatesDTO,AssociatesDTO.class);
        assertEquals("Name", response.getName());
        assertEquals(1, associatesTestRepository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO batches (id, endDate, name, practice, startDate) VALUES (3, CURRENT_TIMESTAMP, 'name1', 'practice', CURRENT_TIMESTAMP)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO associates (id, college, email, gender, name, status, batch_id) VALUES (5, 'VFSTR', 'name3@gmail.com', 'F', 'name3', 'ACTIVE', 3)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM associates WHERE id = 5", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM batches WHERE id = 3", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAssociatesByGender() {
        List<AssociatesDTO> response = restTemplate.getForObject(baseUrl + "/{gender}", List.class, "F");
        assertEquals(response.size(),associatesTestRepository.findAll().size());
    }



    @Test
//    @Sql(statements = "insert into associates values (14,'VFSTR','name1@gmail.com','M','name1','INACTIVE',1);", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "update associates set email='name2@gmail.com' where id =14", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateAssociatesByGender() {
        BatchesDTO batchesDTO=new BatchesDTO(4,"name1","practice",new Date(),new Date());
        AssociatesDTO associatesDTO = new AssociatesDTO(14,"name1","name2@gmail.com","F","clg","ACTIVE",batchesDTO);
        restTemplate.put(baseUrl, associatesDTO,AssociatesDTO.class);
        Optional<Associates> associates = associatesTestRepository.findById(14);
        assertEquals("name2@gmail.com", associates.get().getEmail());
    }

    @Test
    @Sql(statements = "INSERT INTO associates (id, college, email, gender, name, status) VALUES (5, 'VFSTR', 'name3@gmail.com', 'F', 'name3', 'ACTIVE')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM associates WHERE id = 5", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteAssociate() throws Exception {
//        batchesTestRepository.deleteById(3);
        restTemplate.delete(baseUrl+"/{id}", 6);
        assertEquals(Optional.empty(),associatesTestRepository.findById(6));


    }
}
