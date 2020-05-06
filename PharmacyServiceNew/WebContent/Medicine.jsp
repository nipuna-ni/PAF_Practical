<%@page import="model.*"%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
 
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="ISO-8859-1"> 
<title>Medicine Management</title> 
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.2.1.min.js"></script> 
<script src="Components/Medicine.js"></script> 
</head> 
<body> 
<div class="container">
 <div class="row"> 
 <div class="col-6">  
 <h1>Medicine Management</h1> 
 
<form id="formmedi" name="formmedi">  
 
 Medicine code:   
 <input id="mediCode" name="mediCode" type="text"     class="form-control form-control-sm"> 
 
  <br> Medicine name:   
  <input id="mediName" name="mediName" type="text"        class="form-control form-control-sm"> 
 
  <br> Medicine price:   
  <input id="mediPrice" name="mediPrice" type="text"        class="form-control form-control-sm"> 
 
  <br> Medicine description:   
  <input id="mediDesc" name="mediDesc" type="text"        class="form-control form-control-sm"> 
 
  <br>   
  <input id="btnSave" name="btnSave" type="button" value="Save"     class="btn btn-primary">   
  <input type="hidden" id="hidmediIDSave"   name="hidmediIDSave" value="">  </form> 
 
 <div id="alertSuccess" class="alert alert-success"></div>  
 <div id="alertError" class="alert alert-danger"></div> 
 
 <br>  <div id="divItemsGrid">   
 <%    
 Medicine medicineObj = new Medicine();    
 	out.print(medicineObj.readMedicines());   
 %>
   </div>
 </div>
 </div>
 </div>
 </body>
 </html>
 