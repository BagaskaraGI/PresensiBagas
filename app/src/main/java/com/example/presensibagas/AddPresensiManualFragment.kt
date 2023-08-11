package com.example.presensibagas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.presensibagas.Data.Presensi
import com.example.presensibagas.Data.PresensiViewModel
import com.example.presensibagas.databinding.FragmentAddPresensiManualBinding
import java.text.SimpleDateFormat
import java.util.Calendar


/**
 * A simple [Fragment] subclass.
 * Use the [AddPresensiManualFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPresensiManualFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var _binding: FragmentAddPresensiManualBinding? = null
    private val binding get() = _binding!!
    private lateinit var mPresensiViewModel: PresensiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddPresensiManualBinding.inflate(layoutInflater,container,false)
        mPresensiViewModel = ViewModelProvider(this).get(PresensiViewModel::class.java)

        val time = Calendar.getInstance().time
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        val timeFormatter = SimpleDateFormat("HH:mm")
        val currentDate = dateFormatter.format(time)
        val currentTime = timeFormatter.format(time)

//        binding.inputTanggalMasuk.setText(currentDate)
//        binding.inputTanggalMasuk.isEnabled = false
//        binding.inputJamMasuk.setText(currentTime)
//        binding.inputJamMasuk.isEnabled = false

        binding.inputTanggalMasuk.apply {
            setText(currentDate)
            isEnabled = false
        }

        binding.inputJamMasuk.apply {
            setText(currentTime)
            isEnabled = false
        }

        binding.btnSimpanAbsenMasuk.setOnClickListener {
            insertDataToDatabase()
            this.findNavController().navigate(R.id.action_addPresensiManualFragment_to_listPresensiFragment)
        }
        return binding.root
    }

    private fun insertDataToDatabase(){
        val tanggalMasuk = binding.inputTanggalMasuk.text.toString()
        val jamMasuk = binding.inputJamMasuk.text.toString()
        val lokasiMasuk = binding.inputLokasiMasuk.text.toString()
        val tanggalKeluar = "-"
        val jamKeluar = "-"
        val lokasiKeluar = "-"
        val statusWfh = binding.inputStatusWfh.text.toString()

        val presensi = Presensi(0, tanggalMasuk, jamMasuk, lokasiMasuk, tanggalKeluar, jamKeluar, lokasiKeluar, statusWfh)

        mPresensiViewModel.insertPresensiMasuk(presensi)


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}