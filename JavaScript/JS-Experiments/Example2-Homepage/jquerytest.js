alert("Hello")

/*function onLoadInfo(){
alert("site is loaded")
}

$("document").ready(function(){
	alert("jquery is working")
});

window.onload=onLoadInfo;*/

/*

var elementHTML = document.getElementById("stopka");

if(null != elementHTML)
	alert(elementHTML.nodeName);

var listItems = document.getElementsByTagName("*");

if(null != listItems)
	alert(listItems[listItems.length-1].className);

*/

//write function to find in the document all (HTML)elements with a particular class
//BIBLITEKA CORE.JS --> tam jest mo¿e RegExp zdefiniowany
/*var searchClass = function(theClass){
	var elementsArray = [];
	var matchedArray = [];

	elementsArray=document.getElementsByTagName("*");
	
	var pattern = RegExp("(^| )"+theClass+("( |$)"); //"regular expression" - helps us search strings of a particular pattern 
	
	for(var i=0; i<elementsArray.length; i++){
		if(pattern.test(elementsArray[i].className)){
			matchedArray[matchedArray.length]=elementsArray[i];
		}
	}
	
	return matchedArray;
};

alert("UWAGA!");
var cos = searchClass("jscode");*/


	
	
	
	
	
	