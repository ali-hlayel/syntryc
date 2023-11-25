package de.sentryc.graphql.resolver;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import de.sentryc.domain.SellerState;
import de.sentryc.graphql.input.PageInput;
import de.sentryc.graphql.input.SellerFilter;
import de.sentryc.graphql.input.SellerSortBy;
import de.sentryc.graphql.ouput.PageMeta;
import de.sentryc.graphql.ouput.SellerPageableResponse;
import de.sentryc.service.SellerService;
import de.sentryc.service.dto.ProducerSellerStateDto;
import de.sentryc.service.dto.SellerDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@GraphQLTest
@ComponentScan({"de.sentryc.graphql.resolver"})
@Slf4j
class SellerResolverTest {

    @Autowired
    protected GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    protected SellerService sellerService;
    @Test
    void getSellers() throws IOException {

        ProducerSellerStateDto producerSellerStateDto = ProducerSellerStateDto.builder()
                .producerId(UUID.randomUUID())
                .sellerState(SellerState.WHITELISTED)
                .producerId(UUID.randomUUID())
                .sellerId(UUID.randomUUID())
                .build();
        SellerDto dto = SellerDto.builder()
                .sellerName("Name")
                .externalId("Id")
                .producerSellerStates(List.of(producerSellerStateDto))
                .marketplaceId("marketPlace")
                .build();
        SellerPageableResponse result = SellerPageableResponse.builder().meta(new PageMeta(1, 1))
                .data(List.of(dto))
                .build();
        when(sellerService.getSellers(any(SellerFilter.class), any(PageInput.class), any(SellerSortBy.class)))
                .thenReturn(result);

        GraphQLResponse response = graphQLTestTemplate
                .postForResource("graphql/seller.graphql");
        log.debug("response: {}", response.getRawResponse());
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.sellers.meta.totalPages")).isEqualTo("1");
        assertThat(response.get("$.data.sellers.meta.totalItems")).isEqualTo("1");

        assertThat(response.get("$.data.sellers.data[0].sellerName")).isEqualTo("Name");
        assertThat(response.get("$.data.sellers.data[0].externalId")).isEqualTo("Id");
        assertThat(response.get("$.data.sellers.data[0].marketplaceId")).isEqualTo("marketPlace");

        verify(sellerService, times(1))
                .getSellers(any(SellerFilter.class), any(PageInput.class), any(SellerSortBy.class));
    }
}