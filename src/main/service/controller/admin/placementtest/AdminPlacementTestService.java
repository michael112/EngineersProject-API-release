package main.service.controller.admin.placementtest;

import java.util.Set;

import main.json.admin.placementtest.list.PlacementTestJson;

import main.model.language.Language;
import main.model.placementtest.PlacementTest;

public interface AdminPlacementTestService {

    Set<PlacementTestJson> getPlacementTestList();
    PlacementTestJson getPlacementTestInfo(PlacementTest placementTest);
    Set<PlacementTestJson> getPlacementTestListForLanguage(Language language);
    void createPlacementTest();

}
