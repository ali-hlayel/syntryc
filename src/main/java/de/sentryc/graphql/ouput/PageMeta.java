package de.sentryc.graphql.ouput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageMeta {
    private int totalPages;
    private long totalItems;
}
