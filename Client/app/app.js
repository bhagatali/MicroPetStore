'use strict';

angular.module('myApp', [
  'ngRoute',
  'myApp.about',
  'myApp.contact',
  'myApp.pet'
]).
config(['$routeProvider', function($routeProvider) {
 // $locationProvider.hashPrefix('!');

  $routeProvider
  		.when('/',{
  			templateUrl:'pet/pet.html',
  			controller:'PetController'
  		})
  		.otherwise({
  			redirectTo:'/pet'});
}]);
