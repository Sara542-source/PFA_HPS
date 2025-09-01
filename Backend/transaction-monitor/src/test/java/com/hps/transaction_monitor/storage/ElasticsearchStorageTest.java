/*package com.hps.transaction_monitor.storage;
//test d integration
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ElasticsearchStorageTest {
    @Autowired
    private ElasticsearchStorage elstorage ;

    @Test
    public void testElasticsearchStorageBeanNotNull() {
        assertNotNull(elstorage, "ElasticsearchStorage bean should not be null");
    }
    @Test
    public void testGetLogs()throws IOException {
        List<ObjectNode> logs =  elstorage.getLogs("transactions-*", 0L );
        if (logs.isEmpty()){
            System.out.println("No logs found=============================================£££££µµµµµµµµµµµµ£££££££££££££££");
        }else{
            System.out.println("Found " + logs.size() + " logs: " + logs.toString());
            assertTrue(logs.size() > 0, "Expected at least one log entry");
        }

    }


} */
