package de.sentryc.graphql.ouput;

import de.sentryc.domain.SellerState;
import lombok.Data;

import java.util.UUID;

@Data
public class ProducerSellerStateDto {
private UUID producerId;
private String producerName;
private SellerState sellerState;
private UUID sellerId;
}
