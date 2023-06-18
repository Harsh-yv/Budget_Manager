package com.example.budgetmanager

data class wow (
    val title: String,
    val amount: String,
    val id:String,
    val date:String
){
    override fun toString(): String {
        return "{$title,$amount,$id,$date}"
    }}