package com.example.timers.api;

import com.example.timers.model.TimerExecutionDto;
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
 * A delegate to be called by the {@link ExecutionsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public interface ExecutionsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /api/executions : List executions
     *
     * @param instanceId  (optional)
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 20)
     * @return Executions (status code 200)
     * @see ExecutionsApi#listExecutions
     */
    default ResponseEntity<List<TimerExecutionDto>> listExecutions(String instanceId,
        Integer page,
        Integer size) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"instanceId\" : \"instanceId\", \"scheduledFor\" : \"2000-01-23T04:56:07.000+00:00\", \"errorMessage\" : \"errorMessage\", \"timerName\" : \"timerName\", \"startedAt\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"id\", \"triggerType\" : \"SCHEDULED\", \"outcome\" : \"SUCCESS\", \"timerId\" : \"timerId\", \"finishedAt\" : \"2000-01-23T04:56:07.000+00:00\" }, { \"instanceId\" : \"instanceId\", \"scheduledFor\" : \"2000-01-23T04:56:07.000+00:00\", \"errorMessage\" : \"errorMessage\", \"timerName\" : \"timerName\", \"startedAt\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"id\", \"triggerType\" : \"SCHEDULED\", \"outcome\" : \"SUCCESS\", \"timerId\" : \"timerId\", \"finishedAt\" : \"2000-01-23T04:56:07.000+00:00\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
