//<script src="http://code.jquery.com/jquery-latest.min.js"></script>
//<script>window.jQuery && document.write('<script src="C:\Users\Lukasz\Desktop\jquery-1.10.2.js"></script>')</script>

/*
alert(surr("Q","³ka"));

var x=2;
var y= cos + "outo\"ft\"heblue";
alert("Zmienna cos" + cos);
cos = "zmienna bez var";
alert("Zmienna cos raz jeszcze" + cos);
alert(x+y);

Book = new Object();
Book.iloscStron = 100;
Book.ileMaszStron = function(){
return Book.iloscStron;
}

alert(Book.ileMaszStron());

function surr(a,b){
cos="cos innego";
return a+b+a;
}*/

/*var a = "a''";
var s = "abba";
alert(s+a);*/


//---------------------------------PODSTAWY SK£ADNI JAVASCRIPT---------------------------------------//



//DZIA£ANIA NA TABLICACH
/*
var rack = [];
rack[0]="a"
alert("Value under index 0: "+rack[0]+". Tab length: "+rack.length);
rack[32]="z";
alert("Tab length: "+rack.length);
alert("Undefined value under rack[15]: "+rack[15]);
*/

//AUTOMATYCZNA TABLICA ARGUMENTÓW
/*
function argShow(){
	alert(arguments[0]);
}

argShow("Pierwszy");
*/

//IT MAKES CHROME BROWSER UNRESPONSIVE
/*while(true);*/


// STOS I DEKLARACJA VAR
/*
function onScope(){
	stock=5;		//deklaracja bez var, wiêc pobiera zmienn¹ globaln¹
}

var stock = 44;
onScope();
alert(stock); //wyœwietli 5

function onScope1(){
	var stock1=5;	//tworzy zmienn¹ na lokalnym stosie funkcji
}

var stock1=44;
onScope1();
alert(stock1); //wyœwietli 44

function onScope2(){
	stock3=6;
}

onScope2();
alert(stock3); //wyœwietli 6, funkcja stworzy³a zmienn¹ globaln¹ !
*/

//DWA SPOSOBY DEKLARACJI FUNKCJI I RÓ¯NICE
/*
beefOre();		//zadziala niezale¿nie czy wywolanie jest przed czy po deklaracji funkcji
beefOre1();		//to nie zadziala, wolanie musi byæ po deklaracji funkcji

function beefOre(){
	alert("BEEF");
}

var beefOre1 = function(){
	alert("BEEF1");
};

beefOre1();		//OK o ile powy¿sze, wczeœniejsze wo³anie funkcji beefOre1() zostanie zakomentowane
*/

//OBIEKTY - OPIS I KONSTRUOWANIE

/*
var Robot = new Object(); //obiket pusty
Robot.iloscNog = 2; 	  //nie trzeba var, zmienne poprzez NAZWÊ OBIEKTU s¹ automatycznie deklarowane w lokalnym stosie obiektu
Robot.podskocz = function(){
	alert("JUMP");
};
Robot.podskocz();
*/

//SPOSÓB PRZYPOMINAJ¥CY DEFINICJÊ KLASY z JAVA lub C++

/*
var Rower = {
	iloscKol : 4,
	jedz : function(){
		alert("RIDING");
	}
};

Rower.jedz();
*/


//-----------------------------------DOCUMENT ACCESS---------------------------------------

//TYPY DANYCH W JAVASCRIPT: NUMBER, STRING, OBJECT ---> typeof

/*
	var x=5;
	alert(typeof x); //number
	
	x="mama";
	alert(typeof x); //string
	
	var Rower = {	};	
	alert(typeof Rower); //object
	
	var dodaj = function(){
		alert("dodawanie");
	}
	
	alert(typeof dodaj()); //undefined

*/

//

var body=document.getElementsByTagName("body")[0];
alert(body);
body.style.backgroundColor="#000066";

/* PONI¯SZE ROZWI¥ZANIE RÓWNOWA¯NE POWY¯SZEMU

var body=document.getElementsByTagName("body");
alert(body[0]);
body[0].style.backgroundColor="#000066";*/






















