package com.example;

import java.util.Set;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

/**
 * This example demonstrates using the RiotApi to request summoner information
 * for a given summoner name
 */
public class SummonerExample {

    private String sumName;
    private String sumAccID;
    private String sumID;
    private String sumLevel;

    public void summon(String sumString) throws RiotApiException {
        ApiConfig config = new ApiConfig().setKey("RGAPI-8d0a0a07-6708-4a04-a6a8-80f92acc716e");
        RiotApi api = new RiotApi(config);

        Summoner summoner = api.getSummonerByName(Platform.EUW, sumString);
        sumName = "Name: " + summoner.getName();
        sumID = "Summoner ID: " + summoner.getId();
        sumAccID = "Summoner Account ID: " + summoner.getAccountId();
        sumLevel = "Summoner Level:" + summoner.getSummonerLevel();
    }

    public String getSumName() {
        return sumName;
    }

    public void setSumName(String sumName) {
        this.sumName = sumName;
    }

    public String getSumAccID() {
        return sumAccID;
    }

    public void setSumAccID(String sumAccID) {
        this.sumAccID = sumAccID;
    }

    public String getSumID() {
        return sumID;
    }

    public void setSumID(String sumID) {
        this.sumID = sumID;
    }

    public String getSumLevel() {
        return sumLevel;
    }

    public void setSumLevel(String sumLevel) {
        this.sumLevel = sumLevel;
    }

}
