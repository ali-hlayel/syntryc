package de.sentryc.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerDto {
    private String sellerName;
    private String externalId;
    private List<ProducerSellerStateDto> producerSellerStates;
    private String marketplaceId;
}
