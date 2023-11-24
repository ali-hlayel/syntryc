package de.sentryc.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Data
@Document(indexName = "producer")
public class Producer {

    @Id
    @Field(type = FieldType.Keyword)
    private UUID id;

    private String name;

}
