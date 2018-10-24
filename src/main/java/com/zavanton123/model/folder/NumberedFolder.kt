package com.zavanton123.model.folder

import java.io.File

data class NumberedFolder(val orderNumber: Int, val file: File)
    : Comparable<NumberedFolder> {

    override fun compareTo(other: NumberedFolder): Int {

        return this.orderNumber - other.orderNumber
    }


}