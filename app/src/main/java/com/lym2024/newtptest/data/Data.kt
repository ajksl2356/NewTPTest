package com.lym2024.newtptest.data

data class AA(var response: QSD)
data class QSD(var header:ResultCode,val body:Items)
data class ResultCode(var resultCode:String,var resultMsg:String)
data class Items(var items:Item,val numOfRows:String,var pageNo:String,var totalCount:String )
data class Item(var item:List<Title>)
data class Title(
    var title:String,           //
    var type:String,            //
    var period:String,
    var eventPeriod:String,
    var charge:String,
    var contactPoint:String,    //
    var url:String,             //
    var imageObject:String,
    var description:String,
    var viewCount:String
)
