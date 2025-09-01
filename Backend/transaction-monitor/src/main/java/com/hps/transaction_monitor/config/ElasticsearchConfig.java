package com.hps.transaction_monitor.config;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUrl ;

    @Value("${spring.elasticsearch.username}")
    private String elasticsearchusername ;

    @Value("${spring.elasticsearch.password}")
    private String elasticsearchpassword ;

//Cette méthode crée un client HTTP Elasticsearch prêt à :Se connecter à ton serveur Elasticsearch (via elasticsearchUrl).S’authentifier automatiquement avec un login/mot de passe (username, password).Être réutilisé ailleurs dans ton application Spring grâce à @Bean.
    @Bean
    //Crée client HTTP bas-niveau avec login/mot de passe
    public RestClient restClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(elasticsearchusername,elasticsearchpassword)) ;
        return RestClient.builder(HttpHost.create(elasticsearchUrl.trim())).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)).build();
    }

    @Bean
    //Convertit le client HTTP en transport JSON
    public ElasticsearchTransport elasticsearchTransport(RestClient restClient) {
        return new RestClientTransport(restClient, new JacksonJsonpMapper());
    }

    @Bean
    //Crée le client Java haut-niveau pour interagir avec Elasticsearch
    public ElasticsearchClient elasticsearchClient(ElasticsearchTransport elasticsearchtransport) {
        return new ElasticsearchClient(elasticsearchtransport);
    }

}
