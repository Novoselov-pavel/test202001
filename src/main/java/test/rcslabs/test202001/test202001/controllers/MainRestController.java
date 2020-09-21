package test.rcslabs.test202001.test202001.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test.rcslabs.test202001.test202001.model.services.DbItemServiceInterface;

/**
 * Provides the controller of home page "/"
 */
@RestController
public class MainRestController {
    final Logger logger = LoggerFactory.getLogger(MainRestController.class);

    private DbItemServiceInterface dbItemService;


    /**
     * Return the Json string from the "source_data" table has grouped by parameters "row" and "col"
     *
     * @param row url parameter "row"
     * @param column url parameter "col"
     * @return Json array string
     */
    @GetMapping( value= "/", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String getPivotTableJson(@RequestParam(name = "row",required = false) String row,
                                                  @RequestParam(name = "col", required = false) String column){
        try {
            return dbItemService.getPivotTableJsonString(row, column);
        } catch (IllegalArgumentException exception) {
            logger.warn("getPivotTableJson warn: " + exception.getMessage(),exception);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(),exception);
        } catch (Exception exception) {
            logger.error("getPivotTableJson error: " + exception.getMessage(),exception);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(),exception);
        }
    }

    @Autowired
    public void setDbItemService(DbItemServiceInterface dbItemService) {
        this.dbItemService = dbItemService;
    }
}
