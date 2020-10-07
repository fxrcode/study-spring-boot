# Getting Started

## BAT interview: Java Concurrency of AQS  
* video: https://www.bilibili.com/video/BV1TK411N7jS
* This is port of the code from video, using Spring boot, and springfox3.
* For mappings, can check Endpoints tab in Intellij.
    * H2 UI: http://localhost:8080/h2-console
    * Swagger UI: http://localhost:8080/swagger-ui/index.html
    
## Steps
* single node Spring boot service for miaosha demo
* no lock on stock
* after it's up, goto H2 UI, then query with: `insert into shop_order values (1,5)`
* Use JMeter for load testing: 30 concurrent requests.
 
