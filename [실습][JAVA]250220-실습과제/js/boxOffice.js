function myFunc() {
    const kobisUrl = 'http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json';
    const key = '545bac822b06f81c4d4b542aa93b3c2d';
    let date = '20250218';

    $.ajax({
        async : true,                                   // 비동기화 여부 (default : true)
        url : kobisUrl,                                 // 요청할 서버 URL
        type : 'get',                                   // 타입 (get, post, put 등등)
        data : {
            key : key,
            targetDt : date,
        },
        dataType: 'json',                               // 서버가 보내주는 결과 데이터가 JSON(default: json)
        success : function(result) {              // 결과 성공 콜백함수
            console.log(result);
            let movieName = result.boxOfficeResult.dailyBoxOfficeList[0].movieNm;
            alert(`호출 성공`);
            $('h1').text(movieName);
        },
        error : function(request, status, error) { // 결과 에러 콜백함수
            console.log(error);
            alert("호출 실패");
        }
    })

}