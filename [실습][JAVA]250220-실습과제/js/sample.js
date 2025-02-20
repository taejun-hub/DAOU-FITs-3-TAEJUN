
// Selector
// 전체 선택자: `$('*')`  ⇒ 모든 element 싹 다
// 타입 선택자: `$('태그명1, 태그명2')` ⇒ 원하는 tag 명을 이용해서 선택
// 아이디 선택자: `$('#아이디')`⇒ 원하는 id를 가진 element 선택(unique 한 선택)
// 클래스 선택자:  `$('.클래스명')` ⇒ 원하는 class를 가진 elelment 를 선택
// 자식 선택자: $(태그 > 태그) => > 으로 선택
// 후손 선택자: $(태그  태그) => 공백으로 선택
// 동위 선택자: + => 바로 다음에 나오는 형제 1개
//            ~ => 바로 다음에 나오는 형제 포함 나머지 싹다
// 속성 선택자: []

// -------------------------------------
// Method
// each() : 선택된 각각의 element를 반복처리 하기 위한 메소드

// function myFunc() {
//     $('h1, div').css("color", "red");
//     $('h1, div').remove();
//     $('#seoul').css("color", "red");
//     $('.myClass').css("background-color", "blue");
//     $('ol > li').css("color", "green");
//     $('div li').remove();
//     alert($('#kang ~ li').text());
// }

function change(city) {
    let region = $('select > option:selected').text();
    // 사용자 입력 양식이기 때문에 text() 대신 val() 사용
    // val() => 현재 입력 상자 안의 값을 알아온다
    // val(param) => 인자 값으로 입력 상자를 채운다
    $('input').val(city);

}

function myFunc() {
    let newName = $('#name').val();
    let liHTML = `<li>${newName}</li>`
    // 원하는 위치에 넣을 때는 4가지 method
    // 1. append() : 마지막 자식으로 붙인다
    $('ul').append(liHTML);

    // 2. prepend(): 첫번째 자식으로 붙인다
    $('ul').prepend(liHTML);

    // 3. after() : 바로 다음 형제로 붙인다
    $('li').after(liHTML);

    // 4. before() : 바로 앞 형제로 붙인다
    $('ul > li:last').before(liHTML);
    // let myImg = $('<img />').setAttribute('srx '

    // jQuery Event 처리 context 에서는 this 는 이벤트 소스에 대한 문서 객체를 가리킨다
    $('h1').on('click',
        (e) => {
            console.log($(this));
           alert(`${$(this).text()} clicked!`);
        }
    )
}


