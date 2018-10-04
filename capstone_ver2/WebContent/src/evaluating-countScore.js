(function () {
  var score = document.getElementById('result');
  var sumList = [];
  sumList.length = 7;
  
  // 처음 값을 불러오기위한 호출
  init();
  
  var radio = [];
  radio.length = 7;
  for(var i = 0; i < radio.length; i++) {
    radio[i] = document.querySelectorAll('input[name="val_' + (i + 1) + '"]');
  }
  
  for(var i = 0; i < radio.length; i++) {
    for(var j = 0; j < radio[0].length; j++) {
      (function(i, j) {
        radio[i][j].addEventListener('click', function() {
          sumList[i] = radio[i][j].value;
          sum();
        });
      })(i, j);
    }
  }
  function init() {
    var sum = 0;
    var init = [];
    for(var i = 0; i < sumList.length; i++) {
      if(document.querySelector('input[name="val_' + (i + 1) + '"]:checked')) {
        init[i] = parseInt(document.querySelector('input[name="val_' + (i + 1) + '"]:checked').value);
        sum += init[i];
      }
    }
    score.innerHTML = parseInt(sum);
  }
  
  function sum() {
    var count = 0;
    for(var i = 0; i < sumList.length; i++) {
      if(document.querySelector('input[name="val_' + (i + 1) + '"]:checked')) {
        count += parseInt(document.querySelector('input[name="val_' + (i + 1) + '"]:checked').value);
      }
    }
    score.innerHTML = count;
  }
})();