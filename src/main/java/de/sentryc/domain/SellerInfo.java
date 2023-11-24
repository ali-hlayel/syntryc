package de.sentryc.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.util.UUID;

@Data
@Document(indexName = "seller_info")
public class SellerInfo {

    @Id
    private UUID id;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Marketplace marketplace;

    @Field(type = FieldType.Text, fielddata = true)
    private String name;

    @Field(type = FieldType.Text)
    private String url;

    @Field(type = FieldType.Text)
    private String country;

    @Field(type = FieldType.Keyword)
    private String externalId;
}
