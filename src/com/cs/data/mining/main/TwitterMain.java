package com.cs.data.mining.main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.cs.data.mining.model.Tweet;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

public class TwitterMain {
	
	private static String CONSUMER_KEY = "d1eQEh5Qk76cfWxy96L8Vjn9S";
	private static String CONSUMER_KEY_SECRET = "xoQJwU2JlMSTW5IcXJHip3p0oUMVE19LAJb1rxod10ySYW5VYs";
	private static String accessToken = "150117554-KSoYvUzb0F1lpurg4RF31nIO2vV6fvBCxoQ6Gt27";
	private static String accessTokenSecret = "g09NoI7FkaRJJOhMlbKW7WtJ1SUVLFdRbp8MJ185GJhlQ";
	
	private static HashMap authTokenMap = new HashMap();

	public static void main(String[] args) {


		authTokenMap.put("AccessToken", accessToken);
		authTokenMap.put("AccessSecret", accessTokenSecret);
		authTokenMap.put("ConsumerKey", CONSUMER_KEY);
		authTokenMap.put("ConsumerSecret", CONSUMER_KEY_SECRET);
		
		TwitterAuth twitterAuth = new TwitterAuth();
		TwitterData twitterData = new TwitterData();
		Twitter authObject = null;
		
		//String topic = "UFC231";
		ResponseList<User> users = null;
		
		try {
			authObject = TwitterAuth.authenticate(authTokenMap);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		try {
			
			users = TwitterData.getRetweeterData(authObject, 986716201144410112L);
			writeCSV(users);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		

		System.out.println("Done");
	}

	private static void writeCSV(ResponseList<User> users) {

		String SAMPLE_CSV_FILE = "./UserData.csv";

		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));
			CSVPrinter csvPrinter = new CSVPrinter(writer,CSVFormat.DEFAULT.withHeader("ID", "Screen_Nmae", "Favorites_Count","Freinds_Count", "Followers_Count", "Location"));
			
			for (User user : users) {
				
				csvPrinter.printRecord(user.getId(), user.getScreenName(), user.getFavouritesCount(), user.getFriendsCount(), user.getFollowersCount(), user.getLocation());
			
			}
			
			csvPrinter.flush();
			csvPrinter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
