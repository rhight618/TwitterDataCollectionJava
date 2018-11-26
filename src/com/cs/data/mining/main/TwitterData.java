package com.cs.data.mining.main;

import java.util.ArrayList;
import java.util.List;

import com.cs.data.mining.model.Tweet;

import twitter4j.IDs;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;

public class TwitterData {

	public static ArrayList getTwitterData(Twitter authObject, String topic, boolean runOnce, int countLimit) {
		System.out.println("Inside tweet data method: "+authObject + " , "+topic);
		ArrayList<Tweet> tweetList = new ArrayList();
		try {
			Query query = new Query(topic);
			//query.setCount(countLimit);
			//query.setUntil("2018-11-14");
			QueryResult result = null;
			do {
				result = authObject.search(query);
				RateLimitStatus rateLimit = result.getRateLimitStatus();
				System.out.println("Limit: " + rateLimit.getLimit() +  " Remaining: " + rateLimit.getRemaining() +
						" ResetTime: " + rateLimit.getResetTimeInSeconds() + " Seconds to Reset: " + rateLimit.getSecondsUntilReset());
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					Tweet searchTweet = new Tweet();
					searchTweet.setUser(tweet.getUser().getScreenName());
					searchTweet.setTweet(tweet.getText());
					searchTweet.setId(tweet.getId());
					searchTweet.setDate(tweet.getCreatedAt());
					searchTweet.setRetweetCount(tweet.getRetweetCount());
					searchTweet.setFavoriteCount(tweet.getFavoriteCount());
					tweetList.add(searchTweet);
				}
				
				if (runOnce){
					break;
				}
			} while (result.hasNext() == true);
			
			System.out.println(result.getMaxId());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Failed to search tweets: " + e.getMessage());
		}
		System.out.println(tweetList);
		return tweetList;
	}
	
	public static ResponseList<User> getRetweeterData(Twitter authObject, long tweetId) {
		
		ResponseList<User> users = null;
		
		try {

				IDs ids = authObject.getRetweeterIds(tweetId,-1L);
				users = authObject.lookupUsers(ids.getIDs());
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Failed to return retweet user data: " + e.getMessage());
		}
		
		return users;
	}
}