package de.sentryc.graphql.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageInput {
    private int page;
    private int pageSize;

    public static PageInput of(int page, int pageSize) {
        return PageInput.builder().page(page).pageSize(pageSize).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageInput)) return false;
        PageInput pageInput = (PageInput) o;
        return page == pageInput.page && pageSize == pageInput.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, pageSize);
    }
}

