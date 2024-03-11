package com.lym2024.newtptest.data

data class datedata(var meta : locationBasedList, var documents : List<Place> )

data class locationBasedList(var total_count : Int, var pageable_count : Int, var is_end : Boolean)

data class Place(
    var AND  : String,  // 폰정보
    var contentTypeId : String, // 관광타입
    var mapX : String, // 경도좌표
    var mapY : String, //위도좌표
    var _type : String, // 응답메세지
    var radius :String,  // 거리
    var stdt : String     // 약도,대표이미지,분류정보, 지역정보,주소정보 , 좌표정보, 개요정보, 길안내 정보, , 이미지정보,연게관광정보목록,을 조회하는 기능
)

