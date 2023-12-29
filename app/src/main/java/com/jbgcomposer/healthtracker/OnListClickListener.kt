package com.jbgcomposer.healthtracker

import com.jbgcomposer.healthtracker.model.Calc

interface OnListClickListener {
    fun onClick(id: Int)
    fun onLongClick(position: Int, calc: Calc)
}