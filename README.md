# _Respondet?_

_Respondet?_, latin for "_Does it respond?_" is a program that monitors the availability of many websites over the
network, produces metrics about them and stores these metrics.

## Objective

This is a toy project to play with new features from Java 21.

The idea is to implement a program that monitors the availability of many
websites over the network, produces metrics about these and store the metrics
into a database.

The website monitor should perform the checks periodically and collect the
request timestamp, the response time, the HTTP status code, as well as
optionally checking the returned page contents for a regexp pattern that is
expected to be found on the page. 

Each check should run periodically, with the
ability to configure the interval (between 5 and 300 seconds) and the regexp on a
per-check basis. 

The monitored URLs can be anything found online.

When writing to the database, the 
results should handle a reasonable amount
of checks performed over a longer period of time.
