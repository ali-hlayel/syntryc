package de.sentryc.graphql.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerFilter {
    private String searchByName;
    private List<String> producerIds;
    private List<String> marketplaceIds;
}

