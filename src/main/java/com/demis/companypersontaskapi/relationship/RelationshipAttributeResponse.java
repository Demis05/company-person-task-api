package com.demis.companypersontaskapi.relationship;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Relationship operation result")
public record RelationshipAttributeResponse(
        @Schema(description = "Source node identifier", example = "company-1")
        String sourceId,
        @Schema(description = "Target node identifier", example = "person-1")
        String targetId,
        @Schema(description = "Relationship attribute name", example = "personPosition")
        String attributeName,
        @Schema(description = "Relationship attribute value", example = "Manager")
        String attributeValue
) {
}
