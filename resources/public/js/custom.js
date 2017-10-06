var currenttab = 1;

var optionr = 0;
var optioni = 0;
var optiona = 0;
var options = 0;
var optione = 0;
var optionc = 0;

$('#modal-part1').modal('show');
$('#modal-part2').modal('hide');
$('#modal-part3').modal('hide');

function checkinfon(){
	if(currenttab >= 1 && currenttab <= 6){
		$("#part1panel").removeClass("hidden");
		$("#part2panel").addClass("hidden");
		$("#part3panel").addClass("hidden");
		$("#part4panel").addClass("hidden");

	} else if (currenttab >= 7 && currenttab <= 12) {
		$("#part1panel").addClass("hidden");
		$("#part2panel").removeClass("hidden");
		$("#part3panel").addClass("hidden");
		$("#part4panel").addClass("hidden");

	} else if (currenttab >= 13 && currenttab <= 18) {
		$("#part1panel").addClass("hidden");
		$("#part2panel").addClass("hidden");
		$("#part3panel").removeClass("hidden");
		$("#part4panel").addClass("hidden");

	} else if (currenttab > 18) {
		$("#part1panel").addClass("hidden");
		$("#part2panel").addClass("hidden");
		$("#part3panel").addClass("hidden");
		$("#part4panel").removeClass("hidden");
		$('#modal-part1').modal('hide')
		$('#modal-part2').modal('hide')
		$('#modal-part3').modal('hide')
	};
}

function checkmodal() {
	if(currenttab == 1) {
		$('#modal-part1').modal('show');
		$('#modal-part2').modal('hide');
		$('#modal-part3').modal('hide');
	} else if (currenttab == 7){
		$('#modal-part1').modal('hide');
		$('#modal-part2').modal('show');
		$('#modal-part3').modal('hide');
	} else if (currenttab == 13){
		$('#modal-part1').modal('hide');
		$('#modal-part2').modal('hide');
		$('#modal-part3').modal('show');
	}
}

function checkctn() {
	if (currenttab === 18){

		$("#tab" + currenttab).addClass("hidden");
		currenttab += 1;
		$("#bnext").addClass("hidden");
		$("#bsubmit").removeClass("hidden");
		$("#bsubmit").attr("href", "/res" + "/" + optionr + "/" + optioni + "/" + optiona + "/" + options + "/" + optione + "/" + optionc);
		
		} else {

		$("#tab" + currenttab).addClass("hidden");
  		$("#tab" + (currenttab + 1)).removeClass("hidden");
			currenttab += 1;
		}
	}

function checkctp() {
	if (currenttab === 1){
  		currenttab = 1;
  		} else if (currenttab === 19) {

  		$("#bnext").removeClass("hidden");
		$("#bsubmit").addClass("hidden");
		currenttab -= 1;
		$("#tab" + currenttab).removeClass("hidden");

		} else {

  		$("#tab" + currenttab).addClass("hidden");
  		$("#tab" + (currenttab - 1)).removeClass("hidden");
  		currenttab -= 1;
  	}
}

$( "#bnext" ).click(function() {
  	checkctn();
  	checkinfon();
  	checkmodal();
});

$( "#bprev" ).click(function() {
	checkctp();
	checkinfon();
});

$(".op-r > *").click(function(){
	if($(this).hasClass('bactive')){
		optionr -= 1;
	} else {
		optionr += 1;
	}
});	 

$(".op-i > *").click(function(){
	if($(this).hasClass('bactive')){
		optioni -= 1;
	} else {
		optioni += 1;
	}
});	

$(".op-a > *").click(function(){
	if($(this).hasClass('bactive')){
		optiona -= 1;
	} else {
		optiona += 1;
	}
});	 

$(".op-s > *").click(function(){
	if($(this).hasClass('bactive')){
		options -= 1;
	} else {
		options += 1;
	}
});	 

$(".op-e > *").click(function(){
	if($(this).hasClass('bactive')){
		optione -= 1;
	} else {
		optione += 1;
	}
});	 

$(".op-c > *").click(function(){
	if($(this).hasClass('bactive')){
		optionc -= 1;
	} else {
		optionc += 1;
	}
});	  

$(".optiontab > .btn").click(function() {
	$(this).toggleClass('bactive');
});
