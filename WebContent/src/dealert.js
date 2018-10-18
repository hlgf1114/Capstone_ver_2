(function () { 
  var bWriter = document.getElementById('d_w_Team').value;
  var cUser = document.getElementById('d_User').value;
  var reg = document.getElementById('teamWriteDelete');
  
  reg.addEventListener('click', function() {
	  
	  if(bWriter != cUser)
	  {
		  alert('작성자만 삭제할 수 있습니다.');	 
	  	  location.reload();
	  }
	  else{
		  alert('글이 삭제되었습니다.');
		  location.reload();		  
	  }
  });
})();
