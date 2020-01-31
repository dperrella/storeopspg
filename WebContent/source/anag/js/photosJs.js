function createElement(id,url,title){
	 var currentDiv = document.getElementById("myBody"); 
	 var localId = id;
	 var newDiv = document.createElement("div"); 
	 newDiv.setAttribute("class","input-group");
	 newDiv.setAttribute("id","image"+id);
	 var newA = document.createElement("a");
	 newA.setAttribute("class","btn btn-default form-control");
	 newA.setAttribute("href","#");
	 newA.textContent = title;
	 newA.readOnly= true;
	 var newSpan = document.createElement("span"); 
	 newSpan.setAttribute("class","input-group-addon btn-default myMinus");
	 newSpan.setAttribute("id",id);
	 var newI = document.createElement("i"); 
	 newI.setAttribute("class","fa fa-minus");
	 newI.setAttribute("aria-hidden","true");
	 newI.setAttribute("id",id);
	 newSpan.appendChild(newI);
	 newDiv.appendChild(newA);
	 newDiv.appendChild(newSpan);
	 var newDashed = document.createElement("div");
	 newDashed.setAttribute("class","line line-dashed b-b line-lg pull-in");
	 currentDiv.appendChild(newDiv);
	 currentDiv.appendChild(newDashed);
	 $(newA).magnificPopup({
		  type:'image',
		  midClick: true,
		  mainClass: 'custom-popup-class',
		  items: {
		      src: url,
		      type: 'image',
		      title: title
		  },
		  // other options
		});
}
function appendButton(label){
	 var currentDiv = document.getElementById("myBody"); 
	 var addButton = document.createElement("button");
	 addButton.setAttribute("type","button");
	 addButton.setAttribute("class","btn btn-primary");
	 addButton.setAttribute("id","addBarc");
	 addButton.textContent= label;
	 currentDiv.appendChild(addButton);
}