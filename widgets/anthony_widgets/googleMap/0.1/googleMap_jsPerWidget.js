
var GoogleMap_[[widgetId]] = function() {
	return {
		myVariable: null,

		init: function() {
			alert("GoogleMap_[[widgetId]].init");
			
//			// attach an event to an HTML element
//			var self = this;
//			jQuery(".GoogleMap .myElementClass").click(function() {
//				self.myMethod();
//				// do something
//				...
//			});
		},
		
		myMethod: function() {
			alert("GoogleMap.myMethod()");
		}
		// no comma after last method
	};
}();

//jQuery(GoogleMap_[[widgetId]].init()); // Run after page loads