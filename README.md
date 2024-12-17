# Cricinfo

A backend-only web application that scrapes cricket match data from Cricbuzz and provides it through RESTful APIs. Built using **Spring Boot**, this project allows users to access real-time cricket scores, match details, player statistics, and more.

## Features

- **Live Match Scores**: Provides real-time cricket scores for ongoing matches.
- **Match Details**: Fetches detailed information about each match, including teams, players, and match status.
- **Player Statistics**: Displays player performance stats for ongoing or past matches.
- **Match Schedule**: Provides the schedule for upcoming matches.
- **Web Scraping**: Scrapes live data from Cricbuzz using HTML scraping.
- **Persistent Storage**: Stores scraped data in a PostgreSQL database for efficient data management.

## Tech Stack

- **Backend**: Spring Boot
- **Web Scraping**: HTMLScraper
- **Database**: PostgreSQL
- **APIs**: RESTful services to provide cricket data
- **Build Tool**: Maven

## Requirements

- Java 11 or later
- Maven
- PostgreSQL
- Internet connection (for scraping Cricbuzz data)

## Setup and Installation

### 1. Clone the repository

```bash
git clone https://github.com/your-username/cricinfo-website.git
cd cricinfo-website
