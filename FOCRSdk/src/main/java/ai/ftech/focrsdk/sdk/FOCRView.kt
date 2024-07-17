package ai.ftech.focrsdk.sdk

import ai.ftech.focrsdk.R
import ai.ftech.focrsdk.domain.model.InfoData
import ai.ftech.focrsdk.domain.model.OCRData
import ai.ftech.focrsdk.extension.dpToPx
import ai.ftech.focrsdk.extension.getString
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding

class FOCRView @JvmOverloads constructor(ctx: Context, attrs: AttributeSet? = null) : LinearLayoutCompat(ctx, attrs) {
    private lateinit var llRoot: LinearLayoutCompat

    init {
        LayoutInflater.from(context).inflate(R.layout.focr_view_layout, this, true)
        initView()
    }

    private fun initView() {
        llRoot = findViewById(R.id.llRoot)
    }

    fun setData(ocrData: OCRData?) {
        ocrData?.transformData?.data?.let { listData ->
            listData.forEach { infoData ->
                when (infoData.type) {
                    OCRInfoDataType.TEXT.value -> {
                        addTextView(infoData)
                    }

                    OCRInfoDataType.ARRAY.value -> {
                        addTextViewArray(infoData)
                    }

                    OCRInfoDataType.TABLE.value -> {
                        addTable(infoData)
                    }
                }
            }
        }
    }

    private fun addTable(infoData: InfoData) {
        infoData.data?.let { listData ->
            val tableLayout = TableLayout(context)
            tableLayout.setPadding(16.dpToPx())
            tableLayout.setBackgroundResource(R.drawable.shape_rectangle_white_bg_gray_border)
            listData.forEachIndexed { index, tableData ->
                addTableRow(tableLayout, tableData, index)
            }
            val params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            params.topMargin = 16.dpToPx()
            llRoot.addView(tableLayout, params)
        }
    }

    private fun addTableRow(tableLayout: TableLayout, tableData: InfoData, index: Int) {
        val tableRow = TableRow(context)
        tableRow.setBackgroundColor(if (index % 2 == 0) ContextCompat.getColor(context, R.color.gray) else Color.WHITE)
        tableData.data?.forEach { infoData ->
            val textView = TextView(context)
            textView.text = infoData.title.getString(infoData.content.getString())
            textView.textSize = 16.0f
            if (index == 0) {
                textView.setPadding(16.dpToPx())
            } else {
                textView.setPadding(16.dpToPx(), 32.dpToPx(), 16.dpToPx(), 32.dpToPx())
            }
            textView.setTextColor(Color.BLACK)
            tableRow.addView(textView)
        }
        tableLayout.addView(tableRow)
    }

    private fun addTextViewArray(infoData: InfoData) {
        val title = TextView(context)
        title.text = infoData.title.getString()
        title.textSize = 16.0f
        title.setPadding(0, 16.dpToPx(), 0, 16.dpToPx())
        title.setTextColor(Color.BLACK)
        val content = TextView(context)
        content.textSize = 16.0f
        content.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
        content.setPadding(16.dpToPx(), 16.dpToPx(), 0, 16.dpToPx())
        content.setTextColor(ContextCompat.getColor(context, R.color.text_gray))
        val sbContent = StringBuilder()
        infoData.data?.forEachIndexed { index, arrayData ->
            if (index > 0) {
                sbContent.append(", ")
            }
            sbContent.append(arrayData.content.getString())
        }
        content.text = sbContent.toString()

        val params = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )

        llRoot.addView(title, params)
        llRoot.addView(content, params)
    }

    private fun addTextView(infoData: InfoData) {
        val title = TextView(context)
        title.text = infoData.title.getString()
        title.textSize = 16.0f
        title.setPadding(0, 16.dpToPx(), 0, 16.dpToPx())
        title.setTextColor(Color.BLACK)
        val content = TextView(context)
        content.text = infoData.content.getString()
        content.textSize = 16.0f
        content.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
        content.setPadding(16.dpToPx(), 16.dpToPx(), 0, 16.dpToPx())
        content.setTextColor(ContextCompat.getColor(context, R.color.text_gray))

        val params = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )

        llRoot.addView(title, params)
        llRoot.addView(content, params)
    }
}

enum class OCRInfoDataType(val value: String) {
    TEXT("text"),
    ARRAY("array"),
    TABLE("table")
}