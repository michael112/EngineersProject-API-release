package main.controllers;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import main.util.labels.LabelProvider;
import main.util.properties.PropertyProvider;

import main.constants.urlconstants.ConfigControllerUrlConstants;

import main.json.response.MaxFileSizeResponseJson;
import main.json.response.AbstractResponseJson;

@RequestMapping(value = ConfigControllerUrlConstants.CLASS_URL)
@RestController
public class ConfigController {

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private PropertyProvider propertyProvider;

    @PermitAll
    @RequestMapping(value = ConfigControllerUrlConstants.MAX_FILE_SIZE, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getMaxFileSize() {
        int maxFileSize = Integer.parseInt(this.propertyProvider.getProperty("file.max.upload.size"));
        String messageStr = this.labelProvider.getLabel("file.max.upload.success");
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<MaxFileSizeResponseJson>(new MaxFileSizeResponseJson(maxFileSize, messageStr, responseStatus), responseStatus);
    }

}
