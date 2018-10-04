(function () { 
  var register = document.getElementById('register');
  var file = document.getElementById('teamFile');
  
 
  register.addEventListener('click', function() {
	  
	  if(file.files.length == 0)
	  {
		  alert('파일을 등륵하세요.');	 
	  	  location.reload();
	  }
	  else{
		  alert('글이 등록되었습니다.');
		  location.reload();		  
	  }
  });
})();
