'use strict';

/* Controllers */

angular.module('myApp.controllers', [])
	.controller('MyCtrl1', function($scope, Restangular) {
        Restangular.oneUrl('borked','http://localhost:8088/builds')
            .get().then(
                function(data){
                	var brokeStuff = new Array();
                    for (var i = data.pipelines.length - 1; i >= 0; i--) {
                    	var pipeline = data.pipelines[i];
                    	for (var j = pipeline.environments.length - 1; j >= 0; j--) {
                    		var environment = pipeline.environments[j];
                    		if(environment.broken){
                    			for (var k = environment.brokenBuilds.length - 1; k >= 0; k--) {
                    				var brokeBuild = environment.brokenBuilds[k];
                    				var brokenThing = {
                    					pipeline: pipeline.name,
                    					environment: environment.name,
                    					name: brokeBuild.name,
                                        team: brokeBuild.team
                    				}
                    				brokeStuff.push(brokenThing);
                    			};
                    		}
                    	};
                    };
                    $scope.brokePipes = brokeStuff;
                }
            );		
	});