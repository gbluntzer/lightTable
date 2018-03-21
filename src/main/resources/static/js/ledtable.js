function sendColor() {
  var form1 = document.getElementById("form1")

  /**
          String payload = "{\"var1\":\"test\",\"tablePixelList\":[ {\"red\":255,\"green\":0,\"blue\":0 }]}";
  //        String requestUrl = "http://localhost:9090/json";
          String requestUrl = "http://192.168.0.25:8080/json";
          */
         // requestUrl = "/json";
//          requestUrl = "http://localhost:9090/json";
 var        requestUrl = "http://192.168.0.25:8080/json";
  var payload = {};

  var tablePixelList = [];
  for(var x = 0; x < form1.elements.length ; x++){
    var tablePixels = form1.elements[x].value;
    tablePixelList[x] = hexToRgb(tablePixels);
  }
  payload["tablePixelList"] = tablePixelList;
  payload["var1"] = "test";

  //var jsonData = JSON.parse( JSONObject ); //if we want to convert string
 // var jsonData = payload;
  var jsonData = {"var1":"test","tablePixelList":[ {"red":255,"green":0,"blue":0 }]};

var request = $.ajax({
  url: requestUrl,
  type: "POST",
  data: jsonData,
  contentType: "application/json; charset=utf-8",
  dataType: "json"
});
}
//https://stackoverflow.com/questions/5623838/rgb-to-hex-and-hex-to-rgb
function hexToRgb(hex) {
    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? {
        "red": parseInt(result[1], 16),
        "green": parseInt(result[2], 16),
        "blue": parseInt(result[3], 16)
    } : null;
};

//function hexToRgbMask(hex) {
//    var bigint = parseInt(hex, 16);
//     return bigint ? {
//            red:(bigint >> 16) & 255,
//            green: (bigint >> 8) & 255,
//            blue: bigint & 255,
//        } : null;
//
//};