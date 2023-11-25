package de.sentryc.service.dto;

import de.sentryc.domain.SellerState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProducerSellerStateDto {
private UUID producerId;
private String producerName;
private SellerState sellerState;
private UUID sellerId;
}
