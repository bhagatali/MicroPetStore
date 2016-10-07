'use strict';

angular.module('myApp.contact', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/contact', {
    templateUrl: 'contact/contact.html',
    controller: 'ContactController'
  });
}])

.controller('ContactController', ['$scope','$http',function($scope,$http) {
	$http.get('json/contact.json').then(function(response){
		$scope.locations = response.data;
	})
}]);