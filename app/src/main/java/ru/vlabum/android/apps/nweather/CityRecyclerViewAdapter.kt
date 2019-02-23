package ru.vlabum.android.apps.nweather


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_city.view.*
import ru.vlabum.android.apps.nweather.CityListFragment.OnListFragmentInteractionListener
import ru.vlabum.android.apps.nweather.data.CityItem

/**
 * [RecyclerView.Adapter] that can display a [CityItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class CityRecyclerViewAdapter(
    private val mValues: List<CityItem>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<CityRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as CityItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_city, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.cityName.text = item.name
        holder.weatherDescr.text = item.descr
        holder.weatherTemp.text = item.temerature.toString()

        with(holder.itemView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.fragment_city_name
        val weatherTemp: TextView = itemView.fragment_city_temp
        val weatherDescr: TextView = itemView.fragment_city_descr

        override fun toString(): String {
            return super.toString() + " '" + cityName.text + "' " + weatherDescr.text
        }
    }

}
