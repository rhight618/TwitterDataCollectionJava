package com.cs.data.mining.main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.cs.data.mining.model.Tweet;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterMain {

	public static void main(String[] args) {

		String CONSUMER_KEY = "d1eQEh5Qk76cfWxy96L8Vjn9S";
		String CONSUMER_KEY_SECRET = "xoQJwU2JlMSTW5IcXJHip3p0oUMVE19LAJb1rxod10ySYW5VYs";

		Twitter twitter = new TwitterFactory().getInstance();

		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

		String accessToken = "150117554-KSoYvUzb0F1lpurg4RF31nIO2vV6fvBCxoQ6Gt27";
		String accessTokenSecret = "g09NoI7FkaRJJOhMlbKW7WtJ1SUVLFdRbp8MJ185GJhlQ";
		twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

		try {
			Query query = new Query("#UFC"); // kalau tweet nya kososng,
														// maka outputnya
														// terminated

			// query.set

			// GeoLocation location = new GeoLocation(-6.2115, 106.8452);
			// //latitude, longitude
			// Unit unit = Query.MILES; // or Query.MILES; Query.KILOMETERS;
			// query.setGeoCode(location, 1, unit); //location, radius, unit

			QueryResult result;
			List<Tweet> tweetList = new ArrayList<Tweet>();

			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				System.out.println(tweets.size());

				for (Status tweet : tweets) {
					// System.out.println("@" + tweet.getUser().getScreenName()
					// + " - " + tweet.getText()+"\n");
					Tweet resultTweet = new Tweet();
					resultTweet.setUser(tweet.getUser().getScreenName());
					resultTweet.setTweet(tweet.getText());
					tweetList.add(resultTweet);
				}

			} while ((query = result.nextQuery()) != null);
			
			writeCSV(tweetList);

		} catch (TwitterException te) {
			System.out.println("Failed to search tweets: " + te.getMessage());
		}

		System.out.println("Done");
	}

	private static void writeCSV(List<Tweet> tweetList) {

		String SAMPLE_CSV_FILE = "./sample.csv";

		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));
			CSVPrinter csvPrinter = new CSVPrinter(writer,CSVFormat.DEFAULT.withHeader("User", "Tweet"));
			
			for (Tweet tweet : tweetList) {
				
				csvPrinter.printRecord(tweet.getUser(), tweet.getTweet());
				
			}
			
			csvPrinter.flush();
			csvPrinter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
