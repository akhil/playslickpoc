// This is a module for cloud persistance in mongolab - https://mongolab.com
angular.module('mongolab', ['ngResource']).
    factory('Cat', function($resource) {
      var Cat = $resource('/:verb', {}, 
	  			{
					update: { method: 'PUT' ,params: {verb:'update'} },
					save: {method:'POST', params:{verb:'create'}}
				})
 
      Cat.prototype.update = function(cb) {
        return Cat.update({name: this.name, color: this.color}, cb);
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