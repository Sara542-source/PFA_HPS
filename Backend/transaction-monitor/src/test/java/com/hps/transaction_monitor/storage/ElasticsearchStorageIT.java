/*package com.hps.transaction_monitor.storage;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


import java.io.IOException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // activer mockito
public class ElasticsearchStorageIT {
    @Mock
    private ElasticsearchClient client;
    //instanicie storage et inject elcclient dedans
    @InjectMocks
    private ElasticsearchStorage elasticsearchStorage;

    private final ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    @Test
    void getLogs_shouldRtrnFiltredData() throws Exception {
        //Arrange
        String index = "logs";
        Long lastTimeStamp = 1735689600000L ; //Définit un timestamp (23 juillet 2024, 00:00:00 UTC) pour filtrer les logs après cette date.
        ObjectNode log1 = mapper.createObjectNode()
                .put("transaction_id","TR12")
                .put("@timestamp","2024-07-23T10:00:00.000Z")
                .put("auth_step", "CREQ")
                .put("CREQ_RECEPTION_ms", 50)
                .put("CHALLENGE_DISPLAY_ms", 60)
                .put("RREQ_SENDING_ms", 70);
        ObjectNode log2 = mapper.createObjectNode()
                .put("transaction_id", "TX124")
                .put("@timestamp", "2023-11-01T12:00:00.000Z")
                .put("auth_step", "RREQ")
                .put("CREQ_RECEPTION_ms", 80)
                .put("CHALLENGE_DISPLAY_ms", 90)
                .put("RREQ_SENDING_ms", 100);
        SearchResponse<ObjectNode> mockResponse = mock(SearchResponse.class) ;
        HitsMetadata mockHits = mock(HitsMetadata.class) ;
        when(mockResponse.hits()).thenReturn(mockHits) ;
        when(mockHits.hits()).thenReturn(List.of(
                new Hit.Builder<ObjectNode>().source(log1).build(),
                new Hit.Builder<ObjectNode>().source(log2).build()

        ));
        //Act
        List<ObjectNode> result = elasticsearchStorage.getLogs(index, lastTimeStamp);
        //Assert<
        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals("TR12",result.get(0).get("transaction_id"));
        assertEquals("TX124",result.get(1).get("transaction_id"));
        verify(client).search(any(SearchRequest.class),eq(ObjectNode.class));

    }
    @Test
    void getLogs_shouldHandlIOExeption() throws IOException {
        //Arrange
        String index = "logs";
        Long lastTimeStamp = 1735689600000L ;
        when(client.search(any(SearchRequest.class),eq(ObjectNode.class))).thenThrow(new IOException("Connection error"));
        //Act&Assert
        IOException exception = assertThrows(IOException.class, () -> {
            elasticsearchStorage.getLogs(index, lastTimeStamp) ;
        });
        assertEquals("Connection error", exception.getMessage());
        verify(client).search(any(SearchRequest.class),eq(ObjectNode.class));
    }

    @Test
    void getLogs_shouldRetournEmptyList() throws IOException {
        String index = "logs";
        Long lastTimeStamp = 1735689600000L ;
        SearchResponse<ObjectNode> mockResponse = mock(SearchResponse.class) ;
        HitsMetadata mockHits = mock(HitsMetadata.class) ;
        when(mockResponse.hits()).thenReturn(mockHits) ;
        when(mockHits.hits()).thenReturn(List.of());
        when(client.search(any(SearchRequest.class),eq(ObjectNode.class))).thenReturn(mockResponse) ;
    }

}
*/