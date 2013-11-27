<html>
<head>
<title></title>
</head>
<body>
<!--START-->
<style>
      #map_canvas {
        width: %%widthProperty%%px;
        height: %%heightProperty%%px;
      }
      
      

    </style>
    <script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
    <script>
    var coor =  new google.maps.LatLng(%%latitudeProperty%%,%%longitudeProperty%%);
    var zoomSize = %%zoomProperty%%;
     
   
    
      function initialize() {
        var map_canvas = document.getElementById('map_canvas');
        var map_options = {
          center: coor, //15.190606
          zoom: zoomSize,
          mapTypeId: google.maps.MapTypeId.%%typeProperty%%
        }
        
        var map = new google.maps.Map(map_canvas, map_options)
        
        var marker = new google.maps.Marker({
            position: coor,
            map: map,
            title:"Hello World!"
        });
        
      }
      google.maps.event.addDomListener(window, 'load', initialize);
    </script>
 	
 	<div id="map_canvas"></div>

<!--END-->
</body>
</html>
