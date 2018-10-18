
(function () {
  var write_btn = document.getElementById('write');
  var write_board = document.getElementById('write_board');
  var cancel_btn = document.getElementById('cancel');
  var flag = false;
  cancel_btn.addEventListener('click', function(){
    write_board.style.display = 'none';
    flag = !flag;
  });
  write_btn.addEventListener('click', function(){
    flag === false ? write_board.style.display = 'block' : write_board.style.display = 'none';
    flag = !flag;
  });
})();