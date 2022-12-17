package com.example.forage.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State

fun <T> MutableState<T>.asState() = this as State<T>