'use strict';

describe('ContactController', function () {

  var controller = null;
  var $scope = null;
  var $http = null;

  beforeEach(function () {
    module('myApp.contact');
  });

  beforeEach(inject(function ($controller, $rootScope, $http) {
    $scope = $rootScope.$new();
    $http = $http;
    controller = $controller('ContactController', {
      $scope: $scope
    });
  }));

  it('should be defined....', function () {
    expect(controller).toBeDefined();
  });
});