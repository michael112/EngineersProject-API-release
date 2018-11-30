package main.controllers;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import main.service.file.RemoteDownloadService;
import main.service.crud.course.file.FileCrudService;

import main.util.labels.LabelProvider;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpBadRequestException;
import main.error.exception.HttpInternalServerErrorException;

import main.model.course.File;

import main.constants.rolesallowedconstants.RolesAllowedConstants;
import main.constants.urlconstants.RemoteAttachementControllerUrlConstants;

@RequestMapping(value = RemoteAttachementControllerUrlConstants.CLASS_URL)
@RestController
public class RemoteAttachementController {

    @Autowired
    private RemoteDownloadService remoteDownloadService;

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private FileCrudService fileCrudService;

    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value = RemoteAttachementControllerUrlConstants.GET_ATTACHEMENT, method = RequestMethod.GET, produces = "multipart/form-data")
    public ResponseEntity<byte[]> getAttachement(@PathVariable("attachementID") String attachementID) {
        File attachement = this.fileCrudService.findFileByID(attachementID);
        if( attachement == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("file.not.found"));
        if( attachement.getRemoteID() == null ) throw new HttpBadRequestException("attachement.not.remote.hosted");
        try {
            return new ResponseEntity<>(this.remoteDownloadService.getFile(attachement.getRemoteID()), prepareHttpHeaders(attachement.getName()), HttpStatus.OK);
        }
        catch( Exception ex ) {
            throw new HttpInternalServerErrorException("attachement.download.failure");
        }
    }

    private HttpHeaders prepareHttpHeaders(String fileName) {
        HttpHeaders result = new HttpHeaders();
        result.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        result.set("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        return result;
    }

}
