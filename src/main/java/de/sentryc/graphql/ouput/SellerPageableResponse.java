package de.sentryc.graphql.ouput;

import de.sentryc.service.dto.SellerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerPageableResponse {
    private PageMeta meta;
    private List<SellerDto> data;
}
