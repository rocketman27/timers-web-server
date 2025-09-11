package com.example.timers.api;

import com.example.timers.model.CreateTemplateRequest;
import com.example.timers.model.TimerTemplate;
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
 * A delegate to be called by the {@link TemplatesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public interface TemplatesApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/templates : Create template and materialize instances
     *
     * @param createTemplateRequest  (required)
     * @return Created (status code 201)
     * @see TemplatesApi#createTemplate
     */
    default ResponseEntity<TimerTemplate> createTemplate(CreateTemplateRequest createTemplateRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"regions\" : [ \"regions\", \"regions\" ], \"description\" : \"description\", \"productTypes\" : [ \"CASH\", \"CASH\" ], \"countries\" : [ \"countries\", \"countries\" ], \"subregions\" : [ \"subregions\", \"subregions\" ], \"triggerTime\" : \"615\", \"clientIds\" : [ \"clientIds\", \"clientIds\" ], \"suspended\" : false, \"cronExpression\" : \"cronExpression\", \"flowTypes\" : [ \"flowTypes\", \"flowTypes\" ], \"name\" : \"name\", \"zoneId\" : \"Europe/London\", \"id\" : \"id\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/templates/{id} : Get template by id
     *
     * @param id  (required)
     * @return Template (status code 200)
     *         or Not Found (status code 404)
     * @see TemplatesApi#getTemplateById
     */
    default ResponseEntity<TimerTemplate> getTemplateById(String id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"regions\" : [ \"regions\", \"regions\" ], \"description\" : \"description\", \"productTypes\" : [ \"CASH\", \"CASH\" ], \"countries\" : [ \"countries\", \"countries\" ], \"subregions\" : [ \"subregions\", \"subregions\" ], \"triggerTime\" : \"615\", \"clientIds\" : [ \"clientIds\", \"clientIds\" ], \"suspended\" : false, \"cronExpression\" : \"cronExpression\", \"flowTypes\" : [ \"flowTypes\", \"flowTypes\" ], \"name\" : \"name\", \"zoneId\" : \"Europe/London\", \"id\" : \"id\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/templates : List templates
     *
     * @return Templates (status code 200)
     * @see TemplatesApi#listTemplates
     */
    default ResponseEntity<List<TimerTemplate>> listTemplates() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"regions\" : [ \"regions\", \"regions\" ], \"description\" : \"description\", \"productTypes\" : [ \"CASH\", \"CASH\" ], \"countries\" : [ \"countries\", \"countries\" ], \"subregions\" : [ \"subregions\", \"subregions\" ], \"triggerTime\" : \"615\", \"clientIds\" : [ \"clientIds\", \"clientIds\" ], \"suspended\" : false, \"cronExpression\" : \"cronExpression\", \"flowTypes\" : [ \"flowTypes\", \"flowTypes\" ], \"name\" : \"name\", \"zoneId\" : \"Europe/London\", \"id\" : \"id\" }, { \"regions\" : [ \"regions\", \"regions\" ], \"description\" : \"description\", \"productTypes\" : [ \"CASH\", \"CASH\" ], \"countries\" : [ \"countries\", \"countries\" ], \"subregions\" : [ \"subregions\", \"subregions\" ], \"triggerTime\" : \"615\", \"clientIds\" : [ \"clientIds\", \"clientIds\" ], \"suspended\" : false, \"cronExpression\" : \"cronExpression\", \"flowTypes\" : [ \"flowTypes\", \"flowTypes\" ], \"name\" : \"name\", \"zoneId\" : \"Europe/London\", \"id\" : \"id\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /api/templates/{id} : Update template and re-materialize instances
     *
     * @param id  (required)
     * @param createTemplateRequest  (required)
     * @return Updated (status code 200)
     * @see TemplatesApi#updateTemplate
     */
    default ResponseEntity<TimerTemplate> updateTemplate(String id,
        CreateTemplateRequest createTemplateRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"regions\" : [ \"regions\", \"regions\" ], \"description\" : \"description\", \"productTypes\" : [ \"CASH\", \"CASH\" ], \"countries\" : [ \"countries\", \"countries\" ], \"subregions\" : [ \"subregions\", \"subregions\" ], \"triggerTime\" : \"615\", \"clientIds\" : [ \"clientIds\", \"clientIds\" ], \"suspended\" : false, \"cronExpression\" : \"cronExpression\", \"flowTypes\" : [ \"flowTypes\", \"flowTypes\" ], \"name\" : \"name\", \"zoneId\" : \"Europe/London\", \"id\" : \"id\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
