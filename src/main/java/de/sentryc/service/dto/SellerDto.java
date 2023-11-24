package de.sentryc.service.dto;

import de.sentryc.graphql.ouput.ProducerSellerStateDto;
import lombok.Data;

import java.util.List;

@Data
public class SellerDto {
    private String sellerName;
    private String externalId;
    private List<ProducerSellerStateDto> producerSellerStates;
    private String marketplaceId;
}
