package com.realtimemap.ext

import com.google.gson.Gson
import com.realtimemap.repo.model.UserLocationModel


fun UserLocationModel.toJson(): String? =Gson().toJson(this)

