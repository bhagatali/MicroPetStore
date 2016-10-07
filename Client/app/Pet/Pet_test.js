'use strict';

describe('Pet Store Tests...', function () {

  var petController = null;
  var petDetailController = null;  
  var $scope = null;
  var $rootScope = null;
  var $httpBackend = null;
  var mockedDashboardJSON = null;
  var authRequestHandler = null;


  beforeEach(function () {
    module('myApp.pet', 'mockedDashboardJSON');
  });


   beforeEach(inject(function($injector) {
     // Set up the mock http service responses
     $httpBackend = $injector.get('$httpBackend');

     // backend definition common for all tests
     authRequestHandler = $httpBackend.when('GET', 'http://localhost:8080/pet/')
                            .respond({id: '1'}, {'pet': 'Buster'});

     // Get hold of a scope (i.e. the root scope)
     $rootScope = $injector.get('$rootScope');

     // The $controller service is used to create instances of controllers
     var $controller = $injector.get('$controller');

     petController = function() {
       return $controller('PetController', {'$scope' : $rootScope });
     };

     petDetailController = function() {
       return $controller('PetDetailController', {'$scope' : $rootScope });
     };     
   }));

    afterEach(function () {
      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
    });

	it('petController should be defined....', function () {
	expect(petController).toBeDefined();
	});

	it('petDetailController should be defined....', function () {
	expect(petDetailController).toBeDefined();
	});

	it('should fetch pet data', function() {
	 $httpBackend.expectGET('http://localhost:8080/pet/');
	 var controller = petController();
	 $httpBackend.flush();
	}); 
 });