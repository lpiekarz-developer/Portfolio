var StripyTables = {

	init: function(){
		//alert("in function init");
		var tables = Core.getElementsByClass("dataTable");

		for (var i = 0; i < tables.length; i++) {
			var tbodies = tables[i].getElementsByTagName("tbody");

			for (var j = 0; j < tbodies.length; j++) {
				var rows = tbodies[j].getElementsByTagName("tr");
				//alert("j");
				//alert(rows.length);

				for (var k = 0; k < rows.length; k++) {
					//alert("dodawanie klasy");
					//alert(rows[k]);
					Core.addClass(rows[k], "alt");
				}	
			}	
		}
	}
};


Core.start(StripyTables);