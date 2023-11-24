package de.sentryc.service.dto;

import de.sentryc.domain.Producer;
import de.sentryc.domain.Seller;
import de.sentryc.graphql.ouput.ProducerSellerStateDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SellerMapper {

    public List<SellerDto> mapToDTOs(List<Seller> sellers) {
        return sellers.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public SellerDto mapToDTO(Seller seller) {
        SellerDto sellerDTO = new SellerDto();
        sellerDTO.setSellerName(seller.getSellerInfo().getName());
        sellerDTO.setExternalId(seller.getSellerInfo().getExternalId());
        sellerDTO.setMarketplaceId(seller.getSellerInfo().getMarketplace().getId());

        List<ProducerSellerStateDto> producerSellerStates = seller.getProducers().stream()
                .map(producer -> mapToProducerSellerStateDTO(producer, seller))
                .collect(Collectors.toList());

        sellerDTO.setProducerSellerStates(producerSellerStates);

        return sellerDTO;
    }

    private ProducerSellerStateDto mapToProducerSellerStateDTO(Producer entity, Seller seller) {
        ProducerSellerStateDto dto = new ProducerSellerStateDto();
        dto.setProducerId(entity.getId());
        dto.setProducerName(entity.getName());
        dto.setSellerId(seller.getId());
        dto.setSellerState(seller.getSellerState());
        return dto;
    }
}
