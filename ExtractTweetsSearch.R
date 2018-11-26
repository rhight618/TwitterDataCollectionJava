# minimum example for R

install.packages("httr")
install.packages("base64enc")
library("httr")
library("base64enc")

appname <- "DataMining_Project_2018_TTU_CS"
key <- "d1eQEh5Qk76cfWxy96L8Vjn9S"
secret <- "xoQJwU2JlMSTW5IcXJHip3p0oUMVE19LAJb1rxod10ySYW5VYs"

# base64 encoding
kands <- paste(key, secret, sep=":")
base64kands <- base64encode(charToRaw(kands))
base64kandsb <- paste("Basic", base64kands, sep=" ")

# request bearer token
resToken <- POST(url = "https://api.twitter.com/oauth2/token",
                 add_headers("Authorization" = base64kandsb, "Content-Type" = "application/x-www-form-urlencoded;charset=UTF-8"),
                 body = "grant_type=client_credentials")

# get bearer token
bearer <- content(resToken)
bearerToken <- bearer[["access_token"]]
bearerTokenb <- paste("Bearer", bearerToken, sep=" ")

# get example from full archive
#identify seed tweet via full archive api
resTweets <- POST(url = "https://api.twitter.com/1.1/tweets/search/fullarchive/DEV.json",
                  add_headers("authorization" = bearerTokenb, "content-Type" = "application/json"),
                  body = "{\"query\": \"@rory_macdonald @Benaskren champ vs champ\" ,\"maxResults\": 100,\"fromDate\": \"201804010000\", \"toDate\": \"201804200000\" }")

tweets <- content(resTweets)
#eye ball search for the first instance of the tweet that has not been retweeted

