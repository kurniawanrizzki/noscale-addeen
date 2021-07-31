package com.advotics.addeen.utils.pdfcommon

import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfWriter
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class PageNumeration: PdfPageEventHelper() {
    private val FONT_FOOTER = Font(Font.FontFamily.TIMES_ROMAN, 8F, Font.NORMAL, BaseColor.DARK_GRAY)

    override fun onEndPage(writer: PdfWriter?, document: Document?) {
        try {
            var cell: PdfPCell? = null
            var table: PdfPTable = PdfPTable(2)
            table.setWidthPercentage(100f)
            table.setWidths(floatArrayOf(3f,1f))

            val date = SimpleDateFormat("YYYY-MMM-dd HH:mm:ss").format(Date())
            val anchor = Anchor(Phrase("This document is created at $date", FONT_FOOTER))
            anchor.reference = "http://34.101.167.168"

            cell = PdfPCell(anchor)
            cell.horizontalAlignment = Element.ALIGN_LEFT
            cell.border = 0
            cell.setPadding(2f)
            table.addCell(cell)

            table.setTotalWidth(document?.pageSize?.width?.minus(document?.leftMargin())?.minus(document?.rightMargin())!!)
            table.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin() - 5, writer?.directContent)

            cell = PdfPCell(Phrase("Page - ${writer?.pageNumber}", FONT_FOOTER))
            cell.horizontalAlignment = Element.ALIGN_RIGHT
            cell.border = 0
            cell.setPadding(2f)
            table.addCell(cell)
            table.setTotalWidth(document.pageSize.width.minus(document.leftMargin()).minus(document.rightMargin()))
            table.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin()-5, writer?.directContent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}