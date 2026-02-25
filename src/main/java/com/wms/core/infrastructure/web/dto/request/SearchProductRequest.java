package com.wms.core.infrastructure.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class SearchProductRequest {

    @NotNull(message = "owner_id is required")
    private UUID ownerId;

    private String search;

    @Min(value = 0, message = "page must be zero or greater")
    private Integer page = 0;

    @Positive(message = "size must be greater than 0")
    private Integer size = 10;

    public UUID getOwnerId(){
        return ownerId;
    }

    public String getSearch(){
        return search;
    }

    public Integer getPage(){
        return page;
    }

    public Integer getSize(){
        return size;
    }

    public void setOwnerId(UUID ownerId){
        this.ownerId = ownerId;
    }

    public void setSearch(String search){
        this.search = search;
    }

    public void setPage(Integer page){
        this.page = page;
    }

    public void setSize(Integer size){
        this.size = size;
    }

}
