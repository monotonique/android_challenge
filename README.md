This is a sample News Reader app that is supposed to display news list and the details.
The first page displays news list, when one of the items is clicked, it is supposed to show the detail of the selected news.
Unfortunately, the app is full of bugs and it crashes as soon as it is launched.
Also, the code is not properly written and there are no unit tests.
Can you help to fix all the problems?

## Before you start
This project requires the following
1. Android Studio.
2. Android SDK 22 or above.
3. Android SDK build tools 22.0.1 or above.

## Screenshots
The screenshot below shows how the app looks like when it is done.

![](http://i.imgur.com/GgEP7FM.jpg)
![](http://i.imgur.com/yAtzntJ.jpg)

## About the project
All the data is from a local JSON file stored in "raw/news_list.json".
The file contains a list of news items as well as URLs to the pictures associated with each story.

## Fix crashes in News List page
Can you help to fix all bugs so that it can display news list properly?

## Fix crashes in News Detail page
Can you help to fix all bugs so that the app can show news detail properly? Also, clicking on "Full Story" button, it should open a browser and display full story in the browser.

## Basic unit test
Can you help to write unit tests for MediaEntity and NewsEntity?

## Improvements
1. The main logic is written in MainActivity, which is not a very clean way to construct an app. Can you help to improve it?

2. The way of parsing JSON data is not very nice. For example, if one of the name/value is missing, it can cause the app to crash.
Can you help to make it better?

## Notes
1. It is possible that some of the stories do not have images.
2. It is possible that the link to the full story might not work as it is controlled by New York Times.