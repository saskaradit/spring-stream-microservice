package com.streamicroservices.elastic.index.client.service;

import com.streamicroservices.elastic.model.index.IndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
    List<String> save(List<T> documents);
}
