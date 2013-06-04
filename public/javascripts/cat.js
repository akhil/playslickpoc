angular.module('cat', ['mongolab']).
  config(function($routeProvider) {
    $routeProvider.
      when('/', {controller:ListCtrl, templateUrl:'list.html'}).
      when('/edit/:catname', {controller:EditCtrl, templateUrl:'detail.html'}).
      when('/new', {controller:CreateCtrl, templateUrl:'detail.html'}).
      otherwise({redirectTo:'/'});
  });
 
 
function ListCtrl($scope, Cat) {
  $scope.cats = Cat.query({verb:'list'});
}
 
 
function CreateCtrl($scope, $location, Cat) {
  $scope.save = function() {
    Cat.save($scope.cat, function(cat) {
      $location.path('/edit/' + cat.$name);
    });
  }
}
 
 
function EditCtrl($scope, $location, $routeParams, Cat) {
  var self = this;
 
  Cat.get({verb:"get", name: $routeParams.catname}, function(cat) {
    self.original = cat;
    $scope.cat = new Cat(self.original);
  });
 
  $scope.isClean = function() {
    return angular.equals(self.original, $scope.cat);
  }
 
  $scope.destroy = function() {
    self.original.destroy(function() {
      $location.path('/list');
    });
  };
 
  $scope.save = function() {
    $scope.cat.update(function() {
      $location.path('/');
    });
  };
}