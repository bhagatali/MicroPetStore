'use strict';

angular.module('myApp.pet', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  
  $routeProvider
  		.when('/pet',{
    		templateUrl: 'pet/pet.html',
  			controller: 'PetController'	
  		})
  		.when('/pet/:petId',{
    		templateUrl: 'pet/petDetails.html',
  			controller: 'PetDetailsController'	  			
  		});
}])

.controller('PetController', ['$scope','$http','$location', 
	                 function( $scope , $http , $location ) {

    //Get Pet Information
    $http.get('http://localhost:8080/pet/')
         .success(function(data){
            $scope.petStoreData = data;           
         });


    //Delete a Pet
    $scope.deletePet = function(petId){
    	var URL = "http://localhost:9080/pet/" + petId;

        var response =  $http.delete(URL);
    	
        response.success(function(data){
            console.log(data);
    		$location.path('/pet/');
    	});
    }; 

    //Show Add Pet Form
    $scope.showAddPetForm = function(){
    	clearFields();
    	$scope.addFormShowFlag = true;
    };

    //Cancel Add Pet Form
    $scope.cancelAdd = function(){
    	$scope.addFormShowFlag = false;
    };

    //Submit Add Pet Form
    $scope.submitAddPetForm =  function(){
    	var tags = $scope.tags.split(',');

    	//Create an array of objects for tags
    	var tagArrayJSON = [];
    	for (var i=0;i<tags.length;i++){
    		var tagJson = {};
    		tagJson.tagName = tags[i];
    		tagArrayJSON.push(tagJson);
    	}

    	//Create an array of objects for categories
    	var categoryArrayJSON = [];
    	for (var j=0;j<$scope.categories.length;j++){
    		var categoryJson = {};
    		categoryJson.categoryName = $scope.categories[j]; 
    		categoryArrayJSON.push(categoryJson);
    	}

    	//Create Pet Object
    	var petObject={
    			petName:$scope.name || null,
    			status:$scope.status || null,
    			photoUrl:$scope.image || null,
    			price:$scope.price || null,
    			tags:tagArrayJSON || null,
    			categories:categoryArrayJSON || null
    	};

    	console.log(petObject);

    	//Post Pet Object
    	var response = $http.post('http://localhost:9080/pet/', petObject);

    	response.success(function(data, status, headers, config){
    		$scope.message =  data;
    		$location.path('/pet/');
    	}).error(function(data, status, headers, config){
			alert( "failure message: " + JSON.stringify({data: data}));
    	});

    	clearFields();
    	$scope.addFormShowFlag = false;
    };

    var clearFields = function(){
        $scope.name = "";
        $scope.status = "";
        $scope.image = "";
        $scope.price = "";
        $scope.categories = "";
        $scope.tags = "";
    };    
}])
.controller('PetDetailsController',['$scope','$http','$routeParams','$filter',
                           function( $scope , $http , $routeParams , $filter){

        var petId = $routeParams.petId;
        var URL = "http://localhost:8080/pet/" + petId;

        //Get Specific Pet - By ID
        $http.get(URL).success(function(data){
        	$scope.pet = data;
        	$scope.mainImage = $scope.pet.photoUrl[0];
        	$scope.setImage = function(image){
        		$scope.mainImage = image;
        	};
        });
}]);