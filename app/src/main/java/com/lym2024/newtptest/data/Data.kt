package com.lym2024.newtptest.data

data class AA(var response: QSD)
data class QSD(var header:ResultCode,val body:Items)
data class ResultCode(var resultCode:String,var resultMsg:String)
data class Items(var items:Item,val numOfRows:String,var pageNo:String,var totalCount:String )
data class Item(var item:List<Title>)
data class Title(
    var title : String,           // 제목
    var type : String,            // 기간
    var period : String,          // 기간
    var eventPeriod : String,     // 시간
    var eventSite : String,       // 장소
    var charge : String,          // 금액
    var contactPoint : String,    // 문의 안내
    var url : String,             // URL
    var imageObject : String,     // 이미지 썸네일
    var description : String,     // 설명
    var viewCount : String        // 조회수
)
