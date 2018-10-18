
(function () {
  var delete_section = document.getElementsByClassName('delete_section');
  
  for( var i = 0; i< modify_section.length;i++){
	  var delete_btn = delete_section.item(i);
	  delete_btn.addEventListener('click', function() {
	    alert('파일이 삭제 되었습니다');
	    location.reload();
	  });
  }
})();