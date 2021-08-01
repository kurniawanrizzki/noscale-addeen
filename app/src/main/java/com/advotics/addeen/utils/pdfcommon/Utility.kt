package com.advotics.addeen.utils.pdfcommon

import android.content.Context
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.lang.NullPointerException

class Utility {
    interface OnDocumentClose {
        fun onPdfDocumentClose(file: File)
    }

    companion object {
        val FONT_TITLE = Font(Font.FontFamily.TIMES_ROMAN, 16F, Font.BOLD)
        val FONT_SUBTITLE = Font(Font.FontFamily.TIMES_ROMAN, 10F, Font.NORMAL)
        val FONT_CELL = Font(Font.FontFamily.TIMES_ROMAN, 12F, Font.NORMAL)
        val FONT_COLUMN = Font(Font.FontFamily.TIMES_ROMAN, 14F, Font.NORMAL)

        fun createPdf (context: Context, callback: OnDocumentClose, items: List<Array<String?>>, file: File, isPortrait: Boolean) {
            if (file.exists()) {
                file.delete()
                Thread.sleep(50)
            }

            val document = Document()
            document.setMargins(24f, 24f, 32f, 32f)
            document.pageSize = if (isPortrait) PageSize.A4 else PageSize.A4.rotate()

            val writer = PdfWriter.getInstance(document, FileOutputStream(file.absoluteFile))
            writer.setFullCompression()
            writer.pageEvent = PageNumeration()

            document.open()
            addHeader(document)
            addEmptyLine(document, 3)
            document.add(createDataTable(items))
            document.close()

            try {
                writer.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (null != callback) callback.onPdfDocumentClose(file)
        }

        private fun addEmptyLine (document: Document, number: Int) {
            for (i in 0..number) document.add(Paragraph(" "))
        }

        private fun addHeader (document: Document) {
            val table = PdfPTable(3)
            table.widthPercentage = 100f
            table.setWidths(floatArrayOf(2f, 7f, 2f))
            table.defaultCell.border = PdfPCell.NO_BORDER
            table.defaultCell.verticalAlignment = Element.ALIGN_CENTER
            table.defaultCell.horizontalAlignment = Element.ALIGN_CENTER

            var cell = PdfPCell()

            cell.horizontalAlignment = Element.ALIGN_CENTER
            cell.border = PdfPCell.NO_BORDER
            cell.setPadding(8f)
            cell.isUseAscender = true

            var temp = Paragraph("Recipient Data", FONT_TITLE)
            temp.alignment = Element.ALIGN_CENTER
            cell.addElement(temp)

            temp = Paragraph("", FONT_SUBTITLE)
            temp.alignment = Element.ALIGN_CENTER
            cell.addElement(temp)

            table.addCell(cell)
            document.add(table)
        }

        @Throws(DocumentException::class)
        private fun createDataTable(dataTable: List<Array<String?>>): PdfPTable {
            val table1 = PdfPTable(5)
            table1.widthPercentage = 100f
            table1.setWidths(floatArrayOf(2f,2f,2f,1f,1f))
            table1.headerRows = 1

            table1.defaultCell.verticalAlignment = Element.ALIGN_CENTER
            table1.defaultCell.horizontalAlignment = Element.ALIGN_CENTER

            var cell = PdfPCell(Phrase("Name",  FONT_COLUMN))
            cell.horizontalAlignment = Element.ALIGN_CENTER
            cell.verticalAlignment = Element.ALIGN_MIDDLE
            cell.setPadding(4f)
            table1.addCell(cell)

            cell = PdfPCell(Phrase("Email", FONT_COLUMN))
            cell.horizontalAlignment = Element.ALIGN_CENTER
            cell.verticalAlignment = Element.ALIGN_MIDDLE
            cell.setPadding(4f)
            table1.addCell(cell)

            cell = PdfPCell(Phrase("Phone", FONT_COLUMN))
            cell.horizontalAlignment = Element.ALIGN_CENTER
            cell.verticalAlignment = Element.ALIGN_MIDDLE
            cell.setPadding(4f)
            table1.addCell(cell)

            cell = PdfPCell(Phrase("Year", FONT_COLUMN))
            cell.horizontalAlignment = Element.ALIGN_CENTER
            cell.verticalAlignment = Element.ALIGN_MIDDLE
            cell.setPadding(4f)
            table1.addCell(cell)

            cell = PdfPCell(Phrase("Status", FONT_COLUMN))
            cell.horizontalAlignment = Element.ALIGN_CENTER
            cell.verticalAlignment = Element.ALIGN_MIDDLE
            cell.setPadding(4f)
            table1.addCell(cell)

            val topBottomPadding = 8f
            val leftRightPadding = 4f
            var alternate = false

            val ltGray = BaseColor(221,221,221)
            var cellColor: BaseColor

            for (temp in dataTable) {
                cellColor = if (alternate) ltGray else BaseColor.WHITE

                cell = PdfPCell(Phrase(temp[0], FONT_CELL))
                cell.horizontalAlignment = Element.ALIGN_LEFT
                cell.verticalAlignment = Element.ALIGN_MIDDLE
                cell.paddingLeft = leftRightPadding
                cell.paddingRight = leftRightPadding
                cell.paddingTop = topBottomPadding
                cell.paddingBottom = topBottomPadding
                cell.backgroundColor = cellColor
                table1.addCell(cell)

                cell = PdfPCell(Phrase(temp[1], FONT_CELL))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                cell.verticalAlignment = Element.ALIGN_MIDDLE
                cell.paddingLeft = leftRightPadding
                cell.paddingRight = leftRightPadding
                cell.paddingTop = topBottomPadding
                cell.paddingBottom = topBottomPadding
                cell.backgroundColor = cellColor
                table1.addCell(cell)

                cell = PdfPCell(Phrase(temp[3], FONT_CELL))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                cell.verticalAlignment = Element.ALIGN_MIDDLE
                cell.paddingLeft = leftRightPadding
                cell.paddingRight = leftRightPadding
                cell.paddingTop = topBottomPadding
                cell.paddingBottom = topBottomPadding
                cell.backgroundColor = cellColor
                table1.addCell(cell)

                cell = PdfPCell(Phrase(temp[4], FONT_CELL))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                cell.verticalAlignment = Element.ALIGN_MIDDLE
                cell.paddingLeft = leftRightPadding
                cell.paddingRight = leftRightPadding
                cell.paddingTop = topBottomPadding
                cell.paddingBottom = topBottomPadding
                cell.backgroundColor = cellColor
                table1.addCell(cell)

                cell = PdfPCell(Phrase(temp[5], FONT_CELL))
                cell.horizontalAlignment = Element.ALIGN_CENTER
                cell.verticalAlignment = Element.ALIGN_MIDDLE
                cell.paddingLeft = leftRightPadding
                cell.paddingRight = leftRightPadding
                cell.paddingTop = topBottomPadding
                cell.paddingBottom = topBottomPadding
                cell.backgroundColor = cellColor
                table1.addCell(cell)

                alternate = !alternate
            }

            return table1
        }
    }
}