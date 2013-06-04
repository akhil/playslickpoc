// This is a module for cloud persistance in mongolab - https://mongolab.com
angular.module('mongolab', ['ngResource']).
    factory('Cat', function($resource) {
      var Cat = $resource('/:verb')
 
      Cat.prototype.update = function(cb) {
        return Cat.update({name: this.name, verb:'update', method:"POST"}, cb);
      };
	  
	  Cat.prototype.get = function(cb) {
		  return Cat.query({name: this.name, verb:'get', method:"GET"}, cb)
	  };
 
      Cat.prototype.destroy = function(cb) {
        return Cat.remove({name: this.name, verb:'delete', method:'DELETE'}, cb);
      };
	  
	  Cat.prototype.list = function(cb) {
	    return Cat.query({verb:'list'}, cb)
	  }
      return Cat;
    });