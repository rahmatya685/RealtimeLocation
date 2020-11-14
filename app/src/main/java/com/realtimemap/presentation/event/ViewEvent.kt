package com.realtimemap.presentation.event

data class ViewEvent<T>(val value: T) : SingleEvent<T>(value)
