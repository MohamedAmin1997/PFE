package com.example.monpfe.Profile

 data class utilisateur_data (
      val email:String?,
      val id:String?,
      val name:String?,
      val phone:String?,
      val password:String?,
      val confpass:String?
 )
 {
     constructor():this(null,null,null,null,null,null)
 }