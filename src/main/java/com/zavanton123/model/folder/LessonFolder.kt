package com.zavanton123.model.folder

import java.io.File

data class LessonFolder(val parentOrderNumber: Int,
                        val orderNumber: Int,
                        val folder: File) : Comparable<LessonFolder> {

    override fun compareTo(other: LessonFolder): Int {

        return if (parentOrderNumber != other.parentOrderNumber) {
            parentOrderNumber - other.parentOrderNumber
        } else {
            orderNumber - other.orderNumber
        }
    }
}