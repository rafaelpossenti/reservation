# campsite reservations API

## About the project

### validations rules
* The campsite will be free for all.
* The campsite can be reserved for max 3 days.
* The campsite can be reserved minimum 1 day(s) ahead of arrival and up to 1 month in advance.
* Reservations can be cancelled anytime.
* For sake of simplicity assume the check-in & check-out time is 12:00 AM
### System Requirements
* The users will need to find out when the campsite is available. So the system should expose an API to provide information of the
  availability of the campsite for a given date range with the default being 1 month.
* Provide an end point for reserving the campsite. The user will provide his/her email & full name at the time of reserving the campsite
  along with intended arrival date and departure date. Return a unique booking identifier back to the caller if the reservation is successful.
* The unique booking identifier can be used to modify or cancel the reservation later on. Provide appropriate end point(s) to allow
  modification/cancellation of an existing reservation
* Due to the popularity of the island, there is a high likelihood of multiple users attempting to reserve the campsite for the same/overlapping
  date(s). Demonstrate with appropriate test cases that the system can gracefully handle concurrent requests to reserve the campsite.
* Provide appropriate error messages to the caller to indicate the error cases.
* In general, the system should be able to handle large volume of requests for getting the campsite availability.
* There are no restrictions on how reservations are stored as as long as system constraints are not violated.

## Getting the project

```bash
$ git clone https://github.com/rafaelpossenti/reservation.git
$ cd reservation
```

## Database

### Running the database
```bash
$ sudo docker-compose up
```

### Query collections in the database
```bash
$ sudo docker exec -it mongodb /bin/bash
$ root@e05364f68117:/ mongo
> use reservation
> db.reservation.find({}) #find all records
> db.reservation.deleteMany({}) #delete all records
```

## Running the application
```bash
$ gradle clean build -x test
$ gradle bootRun
```

## Swagger
The open API documentation is available in: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

The Json is also available in: [http://localhost:8080/v3/api-docs] (http://localhost:8080/v3/api-docs)

## Postman Collections


## Tests

### unit tests
```bash
$ gradle clean test
```

### concurrent creation test

You need run the application and the execute the following python script:
```bash
$ cd concurrent-test
$ python3 concurrent.py
```

The response will be:
```json
{"code":"619d6b295aca9f7a0e242624"}
{"status":"BAD_REQUEST","timestamp":"2021-11-23T19:28:57.809684561","message":"Validation error","errors":["There is any reservation dates available from 2021-11-24 to 2021-11-26"]}
{"status":"BAD_REQUEST","timestamp":"2021-11-23T19:28:57.796856304","message":"Validation error","errors":["There is any reservation dates available from 2021-11-24 to 2021-11-26"]}
```

Note: Mongo DB doesnt has concept of transactions:

    ** No A.C.I.D.
    ** No locking for transactional support => faster inserts
so I used the ReentrantReadWriteLock class to assure that the project will have a safety save.


### performance test for get available dates
The test was realized using ApacheBench executing the following command with the application already running:
```Bash
$  ab -n 5000 -c 70 -k http://localhost:8080/reservations/available-dates
```

Result:

```
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 500 requests
Completed 1000 requests
Completed 1500 requests
Completed 2000 requests
Completed 2500 requests
Completed 3000 requests
Completed 3500 requests
Completed 4000 requests
Completed 4500 requests
Completed 5000 requests
Finished 5000 requests


Server Software:        
Server Hostname:        localhost
Server Port:            8080

Document Path:          /reservations/available-dates
Document Length:        378 bytes

Concurrency Level:      70
Time taken for tests:   11.598 seconds
Complete requests:      5000
Failed requests:        0
Keep-Alive requests:    0
Total transferred:      2415000 bytes
HTML transferred:       1890000 bytes
Requests per second:    431.11 [#/sec] (mean)
Time per request:       162.373 [ms] (mean)
Time per request:       2.320 [ms] (mean, across all concurrent requests)
Transfer rate:          203.34 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   10  16.2      3     104
Processing:     8  151  81.0    133     663
Waiting:        5  143  81.0    123     662
Total:         15  161  78.8    143     663

Percentage of the requests served within a certain time (ms)
  50%    143
  66%    175
  75%    199
  80%    215
  90%    270
  95%    317
  98%    378
  99%    419
 100%    663 (longest request)
```