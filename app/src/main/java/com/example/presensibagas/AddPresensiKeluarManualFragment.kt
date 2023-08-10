package com.example.presensibagas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.presensibagas.Data.Presensi
import com.example.presensibagas.Data.PresensiViewModel
import com.example.presensibagas.databinding.FragmentAddPresensiKeluarManualBinding



/**
 * A simple [Fragment] subclass.
 * Use the [AddPresensiKeluarManualFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPresensiKeluarManualFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private val args by navArgs<AddPresensiKeluarManualFragmentArgs>()
    private var _binding: FragmentAddPresensiKeluarManualBinding? = null
    private val binding get() = _binding!!
    private lateinit var mPresensiViewModel: PresensiViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddPresensiKeluarManualBinding.inflate(layoutInflater,container,false)
        mPresensiViewModel = ViewModelProvider(this).get(PresensiViewModel::class.java)

        binding.btnSimpanAbsenKeluar.setOnClickListener {
            insertDataToDatabase()
            this.findNavController().navigate(R.id.action_addPresensiKeluarManualFragment_to_listPresensiFragment)
        }

        return binding.root
    }

    private fun insertDataToDatabase(){
        val id = args.currentPresensi.id
        val tanggalMasuk = args.currentPresensi.tanggalMasuk
        val jamMasuk = args.currentPresensi.jamMasuk
        val lokasiMasuk = args.currentPresensi.lokasiMasuk
        val tanggalKeluar = binding.inputTanggalKeluar.text.toString()
        val jamKeluar = binding.inputJamKeluar.text.toString()
        val lokasiKeluar = binding.inputLokasiKeluar.text.toString()
        val statusWfh = binding.inputStatusWfh.text.toString()
        val presensi = Presensi(0, tanggalMasuk, jamMasuk, lokasiMasuk, tanggalKeluar, jamKeluar, lokasiKeluar, statusWfh)


    }


}