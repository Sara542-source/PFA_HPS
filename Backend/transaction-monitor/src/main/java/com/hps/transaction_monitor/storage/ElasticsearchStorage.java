package com.hps.transaction_monitor.storage;
//1
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;



@Component
public class ElasticsearchStorage {
    private final ElasticsearchClient client;
    public ElasticsearchStorage(ElasticsearchClient client) {
        this.client = client;
    }

    public List<ObjectNode> getLogs(String index, Long lastTimestamp) throws IOException {
        SearchResponse<ObjectNode> response = client.search(s -> s
                        .index(index)
                        .size(100)
                          .sort(sort -> sort.field(f -> f.field("@timestamp").order(SortOrder.Asc)))
                        .query(q -> q
                                .bool(b -> b
                                        .must(m -> m  // Existing range filter
                                                .range(r -> r
                                                        .field("@timestamp")
                                                        .gt(JsonData.of(lastTimestamp))
                                                )
                                        )
                                        .must(m -> m.exists(e -> e.field("CREQ_RECEPTION_ms")))
                                        .must(m -> m.exists(e -> e.field("CHALLENGE_DISPLAY_ms")))
                                        .must(m -> m.exists(e -> e.field("RREQ_SENDING_ms")))
                                )
                        )
                        .source(src -> src
                                .filter(f -> f
                                        .includes("auth_step", "@timestamp", "transaction_id", "CREQ_RECEPTION_ms", "CHALLENGE_DISPLAY_ms", "RREQ_SENDING_ms")
                                )
                        ),
                ObjectNode.class
        );
        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}
