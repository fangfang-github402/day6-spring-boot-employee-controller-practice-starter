**GET #obtain company list with response of id, name**  
HTTP Method: GET  
URL: /companies  

**GET #obtain a certain specific company with response of id, name**  
HTTP Method: GET  
URL: /companies/{companyId}  

**GET #obtain list of all employee under a certain specific company**  
HTTP Method: GET  
URL: /companies/{companyId}/employees  

**GET #Page query, page equals 1, size equals 5, it will return the data in company list from index 0 to index 4.**  
HTTP Method: GET  
URL: /companies?page=1&size=5

**PUT # update an employee with company**  
HTTP Method: PUT  
URL: /companies/{companyId}/employee/{employeeId}   
Request Body:  
{  
  "name": "Tom5",  
  "age": 23,  
  "gender": "FEMALE",  
  "salary": 8000.0  
}  

**POST #add a company**  
HTTP Method: POST  
URL: /companies  
Request Body:  
{  
  "name": "Cargo Smart"  
}  

**DELETE # delete a company**
HTTP Method: DELETE  
URL: /companies/{companyId}