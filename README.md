# CLICKBAIT-FILTER-SCRAPER

[![ClickBaitSite](https://click-bait-filtering-plugin.com/assets/images/icon-128-122x122.png)](https://click-bait-filtering-plugin.com/index.html)

## Description

This service is a part of a group of services who plot to rid the web of clickbait by relying on user input and machine learning. The completed application functions by storing its user clicked items and using them to disseminate what is clickbait and what is legitimate news, stories, etc. This is done in conjunction with a machine learning classificator. The full application functions on all sites and thus can allow you to be more productive while browsing the web. It functions by providing the user with a slider that givies the possibility to filter content deemed clickbait or highlight content that is deemed not. The application also shows its user a topology of the most clickbaity content of each domain.
</br>
</br>
This service is a rss feed subscriber sourcing news media links from multiple online news feeds. This service is used to generate data for updating the model throught [click_bait_filter_nlp], thus is essential for both maintenance as well as improving the quality of the service. I know that's not what a scraper does. But it will scrape soon. For more info visit the application [CLICKBAIT-PORTAL] and download the user application/plugin from the [CHROME-STORE].

## Technologies

CLICKBAIT-FILTER-SERVICE uses a number of open source projects:

  * [JAVA11] - JAVA 11 SDK
  * [GRADLE] - BUILD AUTOMATION TOOL
  * [SPRING] - JAVA SPRING FRAMEWORK
  * [SPRING-BOOT] - SPRING PROJ BOOTSTRAP
  * [SPRING-INTEGRATION] - OPEN SOURCE SQL DATABASE
  * [POSTGRESQL] - OPEN SOURCE SQL DATABASE

## Installation

Download Java dependancies for the project:
```sh
$ cd click_bait_filter_scraper
$ gradlew assembleDevelopmentDebug --refresh-dependencies
```

## Applications Scopes

This service is a part of a multi application project that features the following git repositories:

| Service Name                                  | Description                         | Maintainer              |
| ----------------------------------------      |:------------------------------------|:------------------------|
| [click_bait_filter_extension]                 | Chrome Extensions Plugin            | [LeadShuriken]          |
| [click_bait_filter_be]\(PROTO)                | Node Application Test Server        | [LeadShuriken]          |
| [click_bait_filter_j]                         | Spring Production Server            | [LeadShuriken]          |
| [click_bait_filter_tflow]                     | Java Tensor Flow Server             | [LeadShuriken]          |
| [click_bait_filter_nlp]                       | TensorFlow Model Generator/Updater  | [LeadShuriken]          |
| [click_bait_filter_portal]                    | Service and Information Portal      | [LeadShuriken]          |
| [click_bait_filter_scraper]                   | RSS Feed sourcer and link scraper   | [LeadShuriken]          |

For development the application should have the following structure:
```sh
 | .
 | +-- click_bait_filter_extension
 | +-- click_bait_filter_be
 | +-- click_bait_filter_j
 | +-- click_bait_filter_tflow
 | +-- click_bait_filter_ml
 | +-- click_bait_filter_portal
 | +-- click_bait_filter_scraper
```
This as 'click_bait_filter_nlp' uses the 'click_bait_filter_be' api's for filtering links. 'click_bait_filter_portal' is just static html which can preside anywhere. 

## Running and Building

This application is an **GRADLE APPLICATION USING THE GRADLE WRAPPER**;

* **WITH CLI COMMANDS**

  Open the terminal and navigate to the root project folder.

  ```sh
  $ gradle build
  ```
  or with no gradle
  ```sh
  $ ./gradlew build
  ```

### Todos

 - Tests and Docs
 
[JAVA11]: <https://www.oracle.com/java/technologies/javase-jdk11-downloads.html>
  [SPRING]: <https://spring.io>
  [SPRING-BOOT]: <https://spring.io/projects/spring-boot>
  [POSTGRESQL]: <https://www.postgresql.org>
  [GRADLE]: <https://gradle.org>
  [JWT]: <https://jwt.io>
  [SPRING-INTEGRATION]: <https://spring.io/projects/spring-integration>

  [webpack-chrome-extension-reloader]: <https://github.com/LeadShuriken/webpack-chrome-extension-reloader>
  [click_bait_filter_extension]: <https://github.com/LeadShuriken/click_bait_filter_extension>
  [click_bait_filter_be]: <https://github.com/LeadShuriken/click_bait_filter_be>
  [click_bait_filter_nlp]: <https://github.com/LeadShuriken/click_bait_filter_nlp>
  [click_bait_filter_portal]: <https://github.com/LeadShuriken/click_bait_filter_portal>
  [click_bait_filter_j]: <https://github.com/LeadShuriken/click_bait_filter_j>
  [click_bait_filter_tflow]: <https://github.com/LeadShuriken/click_bait_filter_tflow>
  [click_bait_filter_scraper]: <https://github.com/LeadShuriken/click_bait_filter_scraper>

  [LeadShuriken]: <https://github.com/LeadShuriken>
  [rubenspgcavalcante]: <https://github.com/rubenspgcavalcante>

  [CHROME-STORE]: <https://chrome.google.com/webstore/detail/clickbait-filtering-plugi/mgebfihfmenffogbbjlcljgaedfciogm>
  [CLICKBAIT-PORTAL]: <https://click-bait-filtering-plugin.com>
