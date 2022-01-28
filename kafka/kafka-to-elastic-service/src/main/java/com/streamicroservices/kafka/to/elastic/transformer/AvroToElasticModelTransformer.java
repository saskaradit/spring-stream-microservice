package com.streamicroservices.kafka.to.elastic.transformer;

import com.streamicroservices.elastic.model.index.impl.TwitterIndexModel;
import com.streamicroservices.kafka.avro.model.TwitterAvroModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AvroToElasticModelTransformer {
    public List<TwitterIndexModel> getElasticModels(List<TwitterAvroModel> avroModels){
        return avroModels.stream().map(avroModel -> TwitterIndexModel
                .builder()
                .userId(avroModel.getUserId())
                .id(String.valueOf(avroModel.getUserId()))
                .text(avroModel.getText())
                .createdAt(ZonedDateTime.ofInstant(Instant.ofEpochMilli(avroModel.getCreatedAt()),
                        ZoneId.systemDefault())).build()).collect(Collectors.toList());
    }
}
