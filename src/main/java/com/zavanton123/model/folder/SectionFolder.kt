package com.zavanton123.model.folder

import java.io.File

data class SectionFolder(val orderNumber: Int, val folder: File)
    : Comparable<SectionFolder> {

    override fun compareTo(other: SectionFolder): Int {

        return this.orderNumber - other.orderNumber
    }


}