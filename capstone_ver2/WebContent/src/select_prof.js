
(function () {
  var select_prof_section = document.getElementsByClassName('selectprof');
 
  
  for( var i = 0; i< modify_section.length;i++){
     var modify_board = document.getElementById('modify_board');
     var cancel_btn = document.getElementById('modify_cancel');
     var modify_btn = modify_section.item(i);
     var register = document.getElementById('mod_register');
     var file = file_section.item(i);
     
     to_file_section.item(i).value = file.value;
     
     cancel_btn.addEventListener('click', function(){
        modify_board.style.display = 'none';
       flag = !flag;
     });
     modify_btn.addEventListener('click', function(){
        
        flag === false ? modify_board.style.display = 'block' : modify_board.style.display = 'none';
        flag = !flag;
     }); 
     register.addEventListener('click', function() {
       alert('파일이 수정 되었습니다');
       location.reload();
     });
  }
})();
