/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.league.LeagueEntry;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import org.springframework.ui.Model;

@Controller
@SpringBootApplication
public class Main {

    Summoner summoner; //Object from Orianna
    Summoners summonerData;//Object to display Data 
    
    // summoner ID's
    final String coregamer90 = "EA-P_z7ydJRcd2gckHhiwwviock3Q_GrySADMBn0QxskmA";
    final String fiffo = "3R0bQzyrkYmwOHYx97AVSn73l2Mi8ydLxdyfpDEP3zM";
    final String braindead28 = "XkyQ7mxWpWiwJDtdlAt7V1JYgdzScqvonIP2gBY9Jw5vBIs";
    final String besoffenekugel = "bFgfX0nSyvI2x-Hx0k4CXMWxD1eF2t5x7x1Tq9yyFpuTSUY";
    final String seppl = "-unE9mwKRNylTpCO2tQahtU71ALUBZgA8rEbTyqQAkEakRI";
    final String killhim = "3Wb9g5oYsFdu_bfRWMel-jbVHMHOcLTe10b7R8Udv10";
    final String ulzman = "hUKb1lWcR7J8zdV2bDc07RqIDXjwN1Dl5P51SrwMlrE";
    final String lordkiller = "xPVkkcfHpSoBHdnZ3lePCxejPb7SJd0eMvRSYoQZFpxnkA";

    final String RIOT_KEY = System.getenv("RIOT_KEY");
        
    // Array of summoners
    private String[] summonerAccountIDs = {coregamer90, fiffo, braindead28, besoffenekugel, seppl, killhim, ulzman, lordkiller};
    private ArrayList<Summoners> summoners = new ArrayList<Summoners>();

//    @Value("${spring.datasource.url}")
//    private String dbUrl;
//    @Autowired
//    private DataSource dataSource;
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

    @RequestMapping(value = "/")
    String index() {
        return "index";
    }

    @RequestMapping("/aboutUs.html")
    public String getSummoners(Model model) {
        try {
            // try to get data from Riot servers
            for (String summoner1 : summonerAccountIDs) {

                summonerData = new Summoners();
                summonerData = summon(Queue.RANKED_SOLO, summoner1);
                summoners.add(summonerData);
                model.addAttribute("summoner", summoners);
            }
            
            // if error occurs, pass blank argument to thymeleaf
        } catch (Exception e) {
            for (String summoner1 : summonerAccountIDs) {

                summonerData = new Summoners();
                summonerData.setSumDivision("(not connected to LoL Servers)");
                summonerData.setSumTier("");
                summoners.add(summonerData);
                model.addAttribute("summoner", summoners);
            }
        }
        return "aboutUs.html";
    }
    
    public Summoners summon(Queue queue, String summonerAccountID) {
        Orianna.setRiotAPIKey(RIOT_KEY);
        Orianna.setDefaultRegion(Region.EUROPE_WEST);

        summoner = Orianna.summonerWithAccountId(summonerAccountID).get();
        summonerData.setSumName(summoner.getName());
        summonerData.setSumLevel(summoner.getLevel());

        LeagueEntry rankedLeague = summoner.getLeaguePosition(queue);
        if (rankedLeague == null) {
            summonerData.setSumDivision("Unranked");
            summonerData.setSumTier("");
        } else {
            summonerData.setSumDivision(rankedLeague.getDivision().toString());
            summonerData.setSumTier(rankedLeague.getTier().toString());
        }

        return summonerData;

    }

    @RequestMapping("/gallery.html")
    String gallery() {
        return "gallery.html";
    }

    @RequestMapping("/indexOriginal.html")
    String indexOriginal() {
        return "indexOriginal.html";
    }

    @RequestMapping("/news.html")
    String news() {
        return "news.html";
    }

//    @RequestMapping("/db")
//    String db(Map<String, Object> model) {
//        try (Connection connection = dataSource.getConnection()) {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
//            stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
//            ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
//
//            ArrayList<String> output = new ArrayList<String>();
//            while (rs.next()) {
//                output.add("Read from DB: " + rs.getTimestamp("tick"));
//            }
//
//            model.put("records", output);
//            return "db";
//        } catch (Exception e) {
//            model.put("message", e.getMessage());
//            return "error";
//        }
//    }
//    @Bean
//    public DataSource dataSource() throws SQLException {
//        if (dbUrl == null || dbUrl.isEmpty()) {
//            return new HikariDataSource();
//        } else {
//            HikariConfig config = new HikariConfig();
//            config.setJdbcUrl(dbUrl);
//            return new HikariDataSource(config);
//        }
//    }
}
