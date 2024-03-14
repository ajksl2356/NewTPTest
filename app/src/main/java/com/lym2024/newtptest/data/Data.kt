package com.lym2024.newtptest.data

class Data ( var num : start, var finsh : List<detail>)

data class start (var total_count : Int, var pageable_count : Int, var is_end : Boolean)

data class detail(
    var title : String, //제목
    var type : String, // 기간
    var period : String, // 기간
    var eventPeriod : String, // 시간
    var eventSite : String,  // 장소
    var charge : String,        // 금액
    var contactPoint : String, // 문의안내
    var url:String,             // URL

)
