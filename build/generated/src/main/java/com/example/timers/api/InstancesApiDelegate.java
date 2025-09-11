package com.example.timers.api;

import com.example.timers.model.BatchIdsRequest;
import com.example.timers.model.TimerInstance;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link InstancesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public interface InstancesApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /api/instances : List instances
     *
     * @param templateId  (optional)
     * @param country  (optional)
     * @param region  (optional)
     * @param subregion  (optional)
     * @param flowType  (optional)
     * @param clientId  (optional)
     * @param productType  (optional)
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 20)
     * @return Instances (status code 200)
     * @see InstancesApi#listInstances
     */
    default ResponseEntity<List<TimerInstance>> listInstances(String templateId,
        String country,
        String region,
        String subregion,
        String flowType,
        String clientId,
        String productType,
        Integer page,
        Integer size) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"country\" : \"country\", \"clientId\" : \"clientId\", \"subregion\" : \"subregion\", \"lastAttemptAt\" : \"2000-01-23T04:56:07.000+00:00\", \"templateId\" : \"templateId\", \"effectiveStartAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lastSuccessAt\" : \"2000-01-23T04:56:07.000+00:00\", \"effectiveEndAt\" : \"2000-01-23T04:56:07.000+00:00\", \"attemptCountToday\" : 0, \"zoneId\" : \"zoneId\", \"id\" : \"id\", \"region\" : \"region\", \"productType\" : \"CASH\", \"flowType\" : \"flowType\", \"status\" : \"ACTIVE\" }, { \"country\" : \"country\", \"clientId\" : \"clientId\", \"subregion\" : \"subregion\", \"lastAttemptAt\" : \"2000-01-23T04:56:07.000+00:00\", \"templateId\" : \"templateId\", \"effectiveStartAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lastSuccessAt\" : \"2000-01-23T04:56:07.000+00:00\", \"effectiveEndAt\" : \"2000-01-23T04:56:07.000+00:00\", \"attemptCountToday\" : 0, \"zoneId\" : \"zoneId\", \"id\" : \"id\", \"region\" : \"region\", \"productType\" : \"CASH\", \"flowType\" : \"flowType\", \"status\" : \"ACTIVE\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /api/instances/_reset : Reset instances by ids (set completed/failed back to active)
     *
     * @param batchIdsRequest  (required)
     * @return Updated (status code 200)
     * @see InstancesApi#resetInstances
     */
    default ResponseEntity<List<TimerInstance>> resetInstances(BatchIdsRequest batchIdsRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"country\" : \"country\", \"clientId\" : \"clientId\", \"subregion\" : \"subregion\", \"lastAttemptAt\" : \"2000-01-23T04:56:07.000+00:00\", \"templateId\" : \"templateId\", \"effectiveStartAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lastSuccessAt\" : \"2000-01-23T04:56:07.000+00:00\", \"effectiveEndAt\" : \"2000-01-23T04:56:07.000+00:00\", \"attemptCountToday\" : 0, \"zoneId\" : \"zoneId\", \"id\" : \"id\", \"region\" : \"region\", \"productType\" : \"CASH\", \"flowType\" : \"flowType\", \"status\" : \"ACTIVE\" }, { \"country\" : \"country\", \"clientId\" : \"clientId\", \"subregion\" : \"subregion\", \"lastAttemptAt\" : \"2000-01-23T04:56:07.000+00:00\", \"templateId\" : \"templateId\", \"effectiveStartAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lastSuccessAt\" : \"2000-01-23T04:56:07.000+00:00\", \"effectiveEndAt\" : \"2000-01-23T04:56:07.000+00:00\", \"attemptCountToday\" : 0, \"zoneId\" : \"zoneId\", \"id\" : \"id\", \"region\" : \"region\", \"productType\" : \"CASH\", \"flowType\" : \"flowType\", \"status\" : \"ACTIVE\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /api/instances/_resume : Resume instances by ids
     *
     * @param batchIdsRequest  (required)
     * @return Updated (status code 200)
     * @see InstancesApi#resumeInstances
     */
    default ResponseEntity<List<TimerInstance>> resumeInstances(BatchIdsRequest batchIdsRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"country\" : \"country\", \"clientId\" : \"clientId\", \"subregion\" : \"subregion\", \"lastAttemptAt\" : \"2000-01-23T04:56:07.000+00:00\", \"templateId\" : \"templateId\", \"effectiveStartAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lastSuccessAt\" : \"2000-01-23T04:56:07.000+00:00\", \"effectiveEndAt\" : \"2000-01-23T04:56:07.000+00:00\", \"attemptCountToday\" : 0, \"zoneId\" : \"zoneId\", \"id\" : \"id\", \"region\" : \"region\", \"productType\" : \"CASH\", \"flowType\" : \"flowType\", \"status\" : \"ACTIVE\" }, { \"country\" : \"country\", \"clientId\" : \"clientId\", \"subregion\" : \"subregion\", \"lastAttemptAt\" : \"2000-01-23T04:56:07.000+00:00\", \"templateId\" : \"templateId\", \"effectiveStartAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lastSuccessAt\" : \"2000-01-23T04:56:07.000+00:00\", \"effectiveEndAt\" : \"2000-01-23T04:56:07.000+00:00\", \"attemptCountToday\" : 0, \"zoneId\" : \"zoneId\", \"id\" : \"id\", \"region\" : \"region\", \"productType\" : \"CASH\", \"flowType\" : \"flowType\", \"status\" : \"ACTIVE\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /api/instances/_suspend : Suspend instances by ids
     *
     * @param batchIdsRequest  (required)
     * @return Updated (status code 200)
     * @see InstancesApi#suspendInstances
     */
    default ResponseEntity<List<TimerInstance>> suspendInstances(BatchIdsRequest batchIdsRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"country\" : \"country\", \"clientId\" : \"clientId\", \"subregion\" : \"subregion\", \"lastAttemptAt\" : \"2000-01-23T04:56:07.000+00:00\", \"templateId\" : \"templateId\", \"effectiveStartAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lastSuccessAt\" : \"2000-01-23T04:56:07.000+00:00\", \"effectiveEndAt\" : \"2000-01-23T04:56:07.000+00:00\", \"attemptCountToday\" : 0, \"zoneId\" : \"zoneId\", \"id\" : \"id\", \"region\" : \"region\", \"productType\" : \"CASH\", \"flowType\" : \"flowType\", \"status\" : \"ACTIVE\" }, { \"country\" : \"country\", \"clientId\" : \"clientId\", \"subregion\" : \"subregion\", \"lastAttemptAt\" : \"2000-01-23T04:56:07.000+00:00\", \"templateId\" : \"templateId\", \"effectiveStartAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lastSuccessAt\" : \"2000-01-23T04:56:07.000+00:00\", \"effectiveEndAt\" : \"2000-01-23T04:56:07.000+00:00\", \"attemptCountToday\" : 0, \"zoneId\" : \"zoneId\", \"id\" : \"id\", \"region\" : \"region\", \"productType\" : \"CASH\", \"flowType\" : \"flowType\", \"status\" : \"ACTIVE\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /api/instances/_trigger : Trigger instances by ids
     *
     * @param batchIdsRequest  (required)
     * @return Updated (status code 200)
     * @see InstancesApi#triggerInstances
     */
    default ResponseEntity<List<TimerInstance>> triggerInstances(BatchIdsRequest batchIdsRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"country\" : \"country\", \"clientId\" : \"clientId\", \"subregion\" : \"subregion\", \"lastAttemptAt\" : \"2000-01-23T04:56:07.000+00:00\", \"templateId\" : \"templateId\", \"effectiveStartAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lastSuccessAt\" : \"2000-01-23T04:56:07.000+00:00\", \"effectiveEndAt\" : \"2000-01-23T04:56:07.000+00:00\", \"attemptCountToday\" : 0, \"zoneId\" : \"zoneId\", \"id\" : \"id\", \"region\" : \"region\", \"productType\" : \"CASH\", \"flowType\" : \"flowType\", \"status\" : \"ACTIVE\" }, { \"country\" : \"country\", \"clientId\" : \"clientId\", \"subregion\" : \"subregion\", \"lastAttemptAt\" : \"2000-01-23T04:56:07.000+00:00\", \"templateId\" : \"templateId\", \"effectiveStartAt\" : \"2000-01-23T04:56:07.000+00:00\", \"lastSuccessAt\" : \"2000-01-23T04:56:07.000+00:00\", \"effectiveEndAt\" : \"2000-01-23T04:56:07.000+00:00\", \"attemptCountToday\" : 0, \"zoneId\" : \"zoneId\", \"id\" : \"id\", \"region\" : \"region\", \"productType\" : \"CASH\", \"flowType\" : \"flowType\", \"status\" : \"ACTIVE\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
