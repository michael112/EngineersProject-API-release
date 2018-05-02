package main.service.controller.admin.placementtest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.service.controller.AbstractService;

import main.util.locale.LocaleCodeProvider;

import main.service.crud.placementtest.PlacementTestCrudService;

import main.json.admin.placementtest.list.PlacementTestJson;

import main.model.language.Language;

@Service("adminPlacementTestService")
public class AdminPlacementTestServiceImpl extends AbstractService implements AdminPlacementTestService {

    private PlacementTestCrudService placementTestCrudService;

    public Set<PlacementTestJson> getPlacementTestList() {

    }

    public Set<PlacementTestJson> getPlacementTestListForLanguage(Language language) {

    }

    public void createPlacementTest() {

    }

    @Autowired
    public AdminPlacementTestServiceImpl(LocaleCodeProvider localeCodeProvider, PlacementTestCrudService placementTestCrudService) {
        super(localeCodeProvider);
        this.placementTestCrudService = placementTestCrudService;
    }

}
