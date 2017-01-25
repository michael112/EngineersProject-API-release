package main.json.placementtests;

import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PlacementTestListJson {

    @Getter
    private String language;
    @Getter
    private Set<PlacementTestResultJson> test;

    public PlacementTestListJson(String language, Set<PlacementTestResultJson> tests) {
        this.language = language;
        this.test = tests;
    }

}