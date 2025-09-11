package com.example.timers.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * BatchIdsRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class BatchIdsRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @Valid
  private List<String> ids = new ArrayList<>();

  public BatchIdsRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public BatchIdsRequest(List<String> ids) {
    this.ids = ids;
  }

  public BatchIdsRequest ids(List<String> ids) {
    this.ids = ids;
    return this;
  }

  public BatchIdsRequest addIdsItem(String idsItem) {
    if (this.ids == null) {
      this.ids = new ArrayList<>();
    }
    this.ids.add(idsItem);
    return this;
  }

  /**
   * Get ids
   * @return ids
   */
  @NotNull @Size(min = 1) 
  @Schema(name = "ids", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("ids")
  public List<String> getIds() {
    return ids;
  }

  public void setIds(List<String> ids) {
    this.ids = ids;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchIdsRequest batchIdsRequest = (BatchIdsRequest) o;
    return Objects.equals(this.ids, batchIdsRequest.ids);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ids);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchIdsRequest {\n");
    sb.append("    ids: ").append(toIndentedString(ids)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

