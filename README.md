# salesTaxCalculator
Spring Boot RESTful Sales Tax Calculator

## Description

Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical products that are exempt. Import duty is an additional sales tax applicable on all imported goods at a rate of 5%, with no exemptions.

When I purchase items I receive a receipt which lists the name of all the items and their price (including tax), finishing with the total cost of the items, and the total amounts of sales taxes paid.The rounding rules for sales tax are as follows. Given a tax rate of n% and price p the tax is n*p/100 rounded up to the nearest 0.05 amount.

Code a simple RESTful API that computes the sales tax according to the description above for a list of products. The API receives the list of products in a HTTP POST request in json format, comput es the sales tax and returns the response in json format.


For example, assuming the RESTful service runs on http://localhost/taxcalculator and that in the current directory there is a file “input1.json” that contains the following data:
```json
[
    {
     "description": "Book", 
     "count": 1, 
     "unitPrice": 12.49,
     "imported": false,
     "category": "BOOKS"
    }, 
    {
     "description": "Chocolate Bar", 
     "count": 1,
     "unitPrice": 0.85,
     "imported": false,
     "category": "FOOD"
    }, 
    {
     "description": "Music CD", 
     "count": 1,
     "unitPrice": 14.99,
     "imported": false,
     "category": "OTHER_PRODUCTS"
    } 
]
```

When running the following command:

###### curl -H "Accept: application/json" -H "Content-type: application/json" -X POST –d @input1.json http://localhost/taxcalculator

I should get the following response:
```json
{“salesTax”: 1.50}
```

Feel free to modify/extend the example data models above if needed.

## Implementation

1. Sales Tax Calculator is developed using Spring Boot Framework, which includes spring-boot-starter-web and spring-boot-starter-test libraries to work with RESTful service and perform unit and integration tests.
2. All changing properties like: basic.sales.tax, import.duty.tax, round.scale - are put in application.properties file. 
3. To start the application you need: salestaxcalculator-0.0.1.jar file, which you can build via Maven.
4. In /salesTaxCalculator/src/main/resources you can find examples of json files for testing: input1.json, input2.json, input3.json.
5. Using command curl -H "Accept: application/json" -H "Content-type: application/json" -X POST –d @input1.json http://localhost/taxcalculator (or using Postman application) - you can start using this application. 

!!! Note that @input1.json - not enough - you should specify full path to file.
