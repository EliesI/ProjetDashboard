package com.doubledash.controller;


import com.doubledash.dto.TweetDTO;
import com.doubledash.dto.Weather;
import com.doubledash.model.Widget;
import com.github.redouane59.twitter.TwitterClient;
import com.github.redouane59.twitter.dto.tweet.Tweet;
import com.github.redouane59.twitter.dto.user.UserV2;
import com.github.redouane59.twitter.signature.TwitterCredentials;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@RequestMapping("/api/widgets")
@RestController
public class WidgetController {

    @GetMapping(path = "/weather/{parVille}")
    public Weather Weather(@PathVariable(value = "parVille") String parVille) throws IOException, ParseException {
        JSONObject W =  new JSONObject();
        JSONObject current = new JSONObject();
        URL url = new URL("http://api.weatherapi.com/v1/current.json?key=a0af91a7af0543d68f6111921220711&q="+parVille+"&aqi=no");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode  = conn.getResponseCode();

        if(responseCode != 200){
            throw new RuntimeException("HttpResponseCode: "+ responseCode + " oui");
        }else{
            StringBuilder informations = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while(scanner.hasNext()){
                informations.append(scanner.nextLine());
            }

            scanner.close();
            System.out.println(informations);

            JSONParser parse = new JSONParser();
            W = (JSONObject) parse.parse(String.valueOf(informations));
            current = (JSONObject) parse.parse(W.getAsString("current"));
        }

        Weather weather = new Weather();
        weather.setCity(parVille);
        weather.setTemperature(current.getAsString("temp_c"));
        weather.setWidget_id("1");

        return weather;
    }

    @GetMapping(path = "/twitter/{username}")
    public JSONObject TwitterProfileByUsername(@PathVariable(value = "username") String parUsername) throws IOException, ParseException {
        TwitterClient twi = new TwitterClient(TwitterCredentials.builder().apiKey("HxfTjFEza4cVA5YJglfglssx6").apiSecretKey("fZgc9LQagxJ6bucMgLzyJdGZ4tnBegKJ4j0KB96DS7chduyWJN").build());
        UserV2 user = twi.getUserFromUserName(parUsername);
        StringBuilder strB = new StringBuilder();
        strB.append(user.getDescription());
        strB.append(user.getDisplayedName());
        strB.append(user.getId());

        JSONObject res = new JSONObject();
        JSONObject public_metrics = new JSONObject();
        public_metrics.put("followers_count", user.getFollowersCount());
        public_metrics.put("following_count", user.getFollowingCount());
        public_metrics.put("tweet_count", user.getTweetCount());
        res.put("description", user.getDescription());
        res.put("username", user.getName());
        res.put("name", user.getDisplayedName());
        res.put("id",user.getId());
        res.put("public_metrics", public_metrics);


        return res;
    }


    @GetMapping(path = "/twitter/gettweets/{username}")
    public List<TweetDTO> GetTweets(@PathVariable(value = "username")String parUser) throws IOException, ParseException {
        TwitterClient twi = new TwitterClient(TwitterCredentials.builder().apiKey("HxfTjFEza4cVA5YJglfglssx6").apiSecretKey("fZgc9LQagxJ6bucMgLzyJdGZ4tnBegKJ4j0KB96DS7chduyWJN").build());
        JSONObject json = TwitterProfileByUsername(parUser);
        String id = json.getAsString("id");
        List<Tweet> tweets = twi.getUserTimeline(id,5);
        JSONObject res = new JSONObject();
        List<TweetDTO> list= new ArrayList<TweetDTO>();
        for (com.github.redouane59.twitter.dto.tweet.Tweet t: tweets) {
            TweetDTO tweetDTO = new TweetDTO();
            tweetDTO.setContenu(t.getText());
            tweetDTO.setRt(t.getRetweetCount());
            tweetDTO.setReplies(t.getReplyCount());
            JSONObject resSous = new JSONObject();
            resSous.put("contenu", t.getText());
            resSous.put("rt", t.getRetweetCount());
            resSous.put("replies", t.getReplyCount());
            list.add(tweetDTO);
        }
        return list;
    }

    @GetMapping(path = "/infos/{parCont}/{parVille}")
    public Object Infos(@PathVariable(value = "parVille") String parVille, @PathVariable(value = "parCont") String parCont) throws IOException, ParseException {
        JSONObject W = new JSONObject();
        JSONArray res = new JSONArray();
        JSONObject res2 = new JSONObject();

        URL url = new URL("https://api.zippopotam.us/" + parCont + "/" + parVille + "");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode + " oui");
        } else {
            StringBuilder informations = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                informations.append(scanner.nextLine());
            }

            scanner.close();
            System.out.println(informations);

            JSONParser parse = new JSONParser();
            W = (JSONObject) parse.parse(String.valueOf(informations));
            String post_code = String.valueOf(W.get("post code"));
            W.remove("post code");
            W.put("post_code", post_code);

            String country_abbrevation = String.valueOf(W.get("country abbreviation"));
            W.remove("country abbreviation");
            W.put("country_abbreviation", country_abbrevation);

            res = (JSONArray) parse.parse(String.valueOf(W.get("places")));
            res2 = (JSONObject) parse.parse(String.valueOf(res.get(0)));

            String place_name = String.valueOf(res2.get("place name"));
            res2.remove("place name");
            res2.put("place_name", place_name);

            W.merge(res2);
            W.remove("places");
        }
        return W;
    }

    @GetMapping(path = "/starwars/planet/{parNum}")
    public JSONObject PlaneteStarWars(@PathVariable(value = "parNum") String parNum) throws IOException, ParseException {
        JSONObject W = new JSONObject();
        JSONObject current = new JSONObject();
        URL url = new URL("https://swapi.dev/api/planets/" + parNum + "/?format=json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode + " oui");
        } else {
            StringBuilder informations = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                informations.append(scanner.nextLine());
            }

            scanner.close();
            System.out.println(informations);

            JSONParser parse = new JSONParser();
            W = (JSONObject) parse.parse(String.valueOf(informations));
        }
        return W;
    }
    @GetMapping(path = "/starwars/film/{parNum}")
    public JSONObject FilmStarWars(@PathVariable(value = "parNum") String parNum) throws IOException, ParseException {
        JSONObject W = new JSONObject();
        JSONObject current = new JSONObject();
        URL url = new URL("https://swapi.dev/api/films/" + parNum + "/?format=json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode + " oui");
        } else {
            StringBuilder informations = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                informations.append(scanner.nextLine());
            }

            scanner.close();
            System.out.println(informations);

            JSONParser parse = new JSONParser();
            W = (JSONObject) parse.parse(String.valueOf(informations));
        }
        return W;
    }

    @GetMapping(path = "/starwars/ship/{parNum}")
    public JSONObject ShipStarWars(@PathVariable(value = "parNum") String parNum) throws IOException, ParseException {
        JSONObject W = new JSONObject();
        JSONObject current = new JSONObject();
        URL url = new URL("https://swapi.dev/api/starships/" + parNum + "/?format=json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode + " oui");
        } else {
            StringBuilder informations = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                informations.append(scanner.nextLine());
            }

            scanner.close();
            System.out.println(informations);

            JSONParser parse = new JSONParser();
            W = (JSONObject) parse.parse(String.valueOf(informations));
        }
        return W;
    }

    @GetMapping(path = "/rickandmorty/{parNum}")
    public JSONObject RickAndMortyCharacter(@PathVariable(value = "parNum") String parNum) throws IOException, ParseException {
        JSONObject W =  new JSONObject();
        JSONObject current = new JSONObject();
        URL url = new URL("https://rickandmortyapi.com/api/character/"+parNum);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode  = conn.getResponseCode();

        if(responseCode != 200){
            throw new RuntimeException("HttpResponseCode: "+ responseCode + " oui");
        }else{
            StringBuilder informations = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while(scanner.hasNext()){
                informations.append(scanner.nextLine());
            }

            scanner.close();
            System.out.println(informations);

            JSONParser parse = new JSONParser();
            W = (JSONObject) parse.parse(String.valueOf(informations));
        }
        return W;
    }

    @GetMapping(path = "/agify/{parName}")
    public JSONObject Agify(@PathVariable(value = "parName") String parName) throws IOException, ParseException {
        JSONObject W =  new JSONObject();
        JSONObject current = new JSONObject();
        URL url = new URL("https://api.agify.io?name="+parName);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode  = conn.getResponseCode();

        if(responseCode != 200){
            throw new RuntimeException("HttpResponseCode: "+ responseCode + " oui");
        }else{
            StringBuilder informations = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while(scanner.hasNext()){
                informations.append(scanner.nextLine());
            }

            scanner.close();
            System.out.println(informations);

            JSONParser parse = new JSONParser();
            W = (JSONObject) parse.parse(String.valueOf(informations));
        }
        return W;
    }
}