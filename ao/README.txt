AORetriever Release 1.0
=========================================

The AORetriever will retrieve Analyst Opinion data from https://ca.finance.yahoo.com/.

=========================================

Installation:

1) Unzip the zip file to any location on your computer.

2) Verify that Java 8 runtime is installed on your computer.  If not, install it here: http://java.com/en/download/

How to use:

1) An "input.csv" file which must be located in the same directory as the AORetriever.exe.
The input.csv must contain a list of stock codes in the first column.
There will be one stock code per line.

2) Run AORetriever.exe to retrieve the latest data for "Mean Recommendation (this week)" and "No. of Brokers" data from the yahoo finance website.

3) The output file will be located in the "output" directory with the appropriate date.

=========================================
Limitations:
-Because this is a webpage scrape, if the website decides to change their table layouts, column names or the UI in general we may have to look at remapping our application to the new layout.
