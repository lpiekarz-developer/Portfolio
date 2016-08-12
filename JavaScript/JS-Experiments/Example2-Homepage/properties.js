//JAVASCRIPT RELATION TO DOCUMENT OBJECT MODEL
//WALKING DOM TREE


var olElement = document.getElementsByTagName("li"); //olElement=object NodeList; olElement[1] = object HTMLLIElement
var htmlElement = document.getElementById("list"); //htmlElement= object HTMLOlistElement
var allElements = document.getElementsByTagName("*"); //return all elements in HTML document
var internetExplorer = document.all;	//ODPOWIEDNIK powy¿szej komendy. Dzia³a zarówno na IE jak i Chrome.

//once you have a reference to the element you can use a lots of NATIVE METHODS to gain information about the element or change its contents.

//NATIVE PROPERTIES OF DOMElements, odnosimy siê po KROPCE
/*
	nodeName
	className
	parentNode
	childNodes
	
	a tak¿e...
	wszelkie atrybuty: id, href, rel, class... np: allElements[x].id
*/


/*for(var i=0;i<childNodes.length;i++){
	alert(ulElement.childNodes[i].nodeName);
}*/

//WYSWIETLANIE Z U¯YCIEM NATYWNYCH PÓL PRZEGL¥DARKI

/*
	alert(olElement);	//object NodeList
	alert(htmlElement);	//object HTMLDivElement
	alert(allElements.length);	//16
	alert(internetExplorer[3].nodeName); //czwarty znacznik od góry --> tu: META
	alert(typeof document.all);	//UNDEFINED in Chrome, object in IE
	
	for(var i=0; i<allElements.length; i++){
		if("DIV"==allElements[i].nodeName){
			alert("Nazwa klasy: " + allElements[i].className); //show two classes names which are attribute of DIV
		}
	}
	
*/

//PARENTNODE AND CHILDNODE

/*	
	alert(allElements[6].parentNode.nodeName); //allElements.parentNode won't work cause allElements IS A COLLECTION
	
	alert(allElements[1].nodeName);
	for(var i=0; i<allElements[1].childNodes.length; i++){
		alert(allElements[1].childNodes[i].nodeName);	//bia³e znaki s¹ traktowane jako childNode
	}
*/

//INTERACTING WITH ATTRIBUTES getAttribute(), np: setAttribute("href","www.holandia.ne");

//CHANGING THE STYLES:

/*
	var header=document.getElementsByTagName("h1")[0];
	header.style.fontFamily="verdana";
*/
	








