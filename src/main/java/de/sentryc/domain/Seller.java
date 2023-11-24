package de.sentryc.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Document(indexName = "seller")
public class Seller {

    @Id
    private UUID id;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Producer> producers = new ArrayList<>();

    @Field(type = FieldType.Nested, includeInParent = true)
    private SellerInfo sellerInfo;

    @Enumerated(EnumType.STRING)
    private SellerState sellerState;
}
