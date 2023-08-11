package com.example.presensibagas

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.presensibagas.Data.Presensi

class PresensiListAdapter : RecyclerView.Adapter<PresensiListAdapter.MyViewHolder>() {

    private var presensiList = emptyList<Presensi>()

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PresensiListAdapter.MyViewHolder {
       return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: PresensiListAdapter.MyViewHolder, position: Int) {
        val currentItem = presensiList[position]
        holder.itemView.findViewById<TextView>(R.id.tanggal_masuk_tv).text = currentItem.tanggalMasuk
        holder.itemView.findViewById<TextView>(R.id.lokasi_masuk_tv).text = currentItem.lokasiMasuk
        holder.itemView.findViewById<TextView>(R.id.jam_masuk_tv).text = currentItem.jamMasuk
        holder.itemView.findViewById<TextView>(R.id.tanggal_keluar_tv).text = currentItem.tanggalKeluar
        holder.itemView.findViewById<TextView>(R.id.lokasi_keluar_tv).text = currentItem.lokasiKeluar
        holder.itemView.findViewById<TextView>(R.id.jam_keluar_tv).text = currentItem.jamKeluar
        holder.itemView.findViewById<TextView>(R.id.status_wfh_tv).text = currentItem.statusWFH

        val buttonAbsenKeluar = holder.itemView.findViewById<Button>(R.id.btn_absen_keluar)

        if (currentItem.tanggalKeluar != "-" || currentItem.lokasiKeluar != "-" || currentItem.jamKeluar != "-"){
            buttonAbsenKeluar.isEnabled = false
        }

        holder.itemView.findViewById<Button>(R.id.btn_absen_keluar).setOnClickListener {
            val action = ListPresensiFragmentDirections.actionListPresensiFragmentToAddPresensiKeluarManualFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return presensiList.size
    }

    fun setData(daftarPresensi:List<Presensi>){
        this.presensiList = daftarPresensi
        Log.d("PresensiList", daftarPresensi.toString())
        notifyDataSetChanged()
    }

}