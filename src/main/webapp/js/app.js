var Status = Backbone.Model.extend({
	urlRoot : "/api/v1/statuses"
});
var Statuses = Backbone.Collection.extend({
	url : "/api/v1/statuses"
});



var PostView = Backbone.View.extend({
	el : ".page",
	
	events : {
		"submit #postForm" : "postStatus"
	},
	
	postStatus : function(event){
		event.preventDefault();
		console.log("In postStatus()... ");
		$("#postForm").mask("Posting status ...");
		var status = $("textarea#status").val();
		var postedBy = $("#postedBy").val();
		var useCurrentLocation = $('#useCurrentLocation').is(":checked") ? true : false;
		
		if(useCurrentLocation){
			var options = {
					status : status,
					postedBy : postedBy,
					formName : "#postForm"
			};
			getCurrentPosition(postViewCallback ,options);
		}else{
			var obj = {
				status : status,
				postedBy : postedBy,
			};
			var model = new Status();
			model.save(obj , {
				success : function(model, response, options){
					console.log("Post successfully saved without location.."+model);
					$("#postForm").unmask();
					app.navigate("#",{trigger:true});
				},error : function(model, xhr, options){
					console.log("Save Error");
					$("#postForm").unmask();
				}
			});
		}
	},
	
	render : function(){
		var template = _.template($("#status-post-template").html() , {});
		this.$el.html(template);
	}
});

function getCurrentPosition(callback , options){

	navigator.geolocation.getCurrentPosition(function(position){

					var longitude = position.coords.longitude;
			    	var latitude = position.coords.latitude;
			    	callback(latitude , longitude , options);
				}, function(e){
					$(options.formName).unmask();
					switch (e.code) {
						case e.PERMISSION_DENIED:
							alert('You have denied access to your position. You will ' +
									'not get the most out of the application now.'); 
							break;
						case e.POSITION_UNAVAILABLE:
							alert('There was a problem getting your position.'); 
							break;
						case e.TIMEOUT:
									alert('The application has timed out attempting to get ' +
											'your location.'); 
							break;
						default:
							alert('There was a horrible Geolocation error that has ' +
									'not been defined.');
					}
				},
					{ timeout: 45000 }

				);
}

function postViewCallback(latitude , longitude , options){
	var obj = {
			status : options.status,
			postedBy : options.postedBy,
			location : {
				type : "POINT",
				coordinates : [longitude , latitude]
			}
		};
	var model = new Status();
	console.log("before save");
	model.save(obj , {
		success : function(model, response, options){
			console.log("Post successfully saved with location.."+model);
			$("#postForm").unmask();
			app.navigate("#",{trigger:true});
		},error : function(model, xhr, options){
			console.log("Save Error");
			$("#postForm").unmask();
		}
	});
}

var HomeView = Backbone.View.extend({
	el : ".page",
	render : function() {
		var statuses = new Statuses();
		var that = this;
		statuses.fetch({
			success : function(statuses) {
				var template = _.template($("#status-list-template").html(), {
					statuses : statuses.models
				});
				that.$el.html(template);
			}
		});

	}
});

var Router = Backbone.Router.extend({
	routes : {
		"" : "home",
		"post" : "post"
	},

});


var homeView = new HomeView();
var postView = new PostView();

var app = new Router();

app.on("route:home", function(){
	console.log("Show home page");
	homeView.render();
});

app.on("route:post", function(){
	console.log("Show post page");
	postView.render();
});

Backbone.history.start();