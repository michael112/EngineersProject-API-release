package main.service.controller.admin.placementtest;

import java.util.Set;

import main.json.admin.placementtest.list.PlacementTestJson;

import main.model.language.Language;

public interface AdminPlacementTestService {

    Set<PlacementTestJson> getPlacementTestList();
    Set<PlacementTestJson> getPlacementTestListForLanguage(Language language);
    void createPlacementTest();

}
