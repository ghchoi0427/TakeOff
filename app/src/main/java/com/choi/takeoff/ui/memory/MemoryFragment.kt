package com.choi.takeoff.ui.memory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.choi.takeoff.MainActivity
import com.choi.takeoff.databinding.FragmentMemoryBinding
import com.choi.takeoff.db.entity.Memo
import com.choi.takeoff.ui.memo.MemoViewModel
import com.choi.takeoff.util.Converters
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class MemoryFragment : Fragment() {

    private lateinit var newMemoViewModel: MemoViewModel
    private lateinit var memoryViewModel: MemoryViewModel
    private var _binding: FragmentMemoryBinding? = null

    private val binding get() = _binding!!
    lateinit var lineChart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        memoryViewModel =
            ViewModelProvider(this).get(MemoryViewModel::class.java)

        _binding = FragmentMemoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        newMemoViewModel = (activity as MainActivity).memoViewModel

        lineChart = binding.lineChart
        setLineChart(load(), "this indicates how much you're hyped up")

        return root
    }


    private fun setLineChart(chartItem: ArrayList<Memo>, displayName: String) {
        val entries = ArrayList<Entry>()

        chartItem.indices.mapTo(entries) {
            Entry(
                Converters.stringToTimestampFloat(chartItem[it].time!!),
                chartItem[it].mood!!.toFloat()
            )
        }

        val lineDataSet = LineDataSet(entries, displayName)
        lineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        lineDataSet.valueTextSize = 12f
        lineDataSet.setDrawFilled(false)

        val labels = ArrayList<String>()


        chartItem.forEach {
            it.time?.let { it1 ->
                labels.add("bitch")
            }
        }

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet as ILineDataSet)

        val data = LineData(dataSets)
        lineChart.data = data

        lineChart.xAxis.let {
            it.position = XAxis.XAxisPosition.BOTTOM
            it.setDrawGridLines(false)
            it.valueFormatter = Formatter()
        }

        lineChart.invalidate()
    }

    inner class Formatter : ValueFormatter() {
        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            //return labels[value.toInt()]
            /*return Instant.ofEpochSecond(value.toLong())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .toString()*/
            return "bitch"
        }
    }

    private fun load() = newMemoViewModel.allMemos.value as ArrayList<Memo>


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}