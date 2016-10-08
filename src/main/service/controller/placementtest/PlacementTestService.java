package main.service.controller.placementtest;

import java.util.Set;

import main.model.placementtest.PlacementTest;
import main.model.user.User;

import main.json.placementtests.PlacementTestJson;
import main.json.placementtests.PlacementTestListJson;
import main.json.placementtests.SolvedPlacementTestJson;

public interface PlacementTestService {

    Set<PlacementTestListJson> getPlacementTestList(User currentUser);

    PlacementTestJson getPlacementTestContent(PlacementTest test);

    double setSolvedPlacementTest(User currentUser, PlacementTest placementTest, SolvedPlacementTestJson solvedPlacementTestJson);

}
