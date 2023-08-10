package com.example.presensibagas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presensibagas.Data.PresensiViewModel
import com.example.presensibagas.databinding.FragmentListPresensiBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ListPresensiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListPresensiFragment : Fragment() {

    private var _binding : FragmentListPresensiBinding? = null
    private val binding get() = _binding!!
    private lateinit var mPresensiViewModel: PresensiViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListPresensiBinding.inflate(inflater, container, false)

        //Menyiapkan Adapter
        val adapter = PresensiListAdapter()
        val rvListPresensi = binding.rvListPresensi

        //Menambahkan adapter ke recyclerView
        rvListPresensi.adapter = adapter
        rvListPresensi.layoutManager = LinearLayoutManager(requireContext())

        mPresensiViewModel = ViewModelProvider(this).get(PresensiViewModel::class.java)
        mPresensiViewModel.readAllData.observe(viewLifecycleOwner) { presensi ->
            adapter.setData(presensi)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAbsenMasuk.setOnClickListener {
            this.findNavController().navigate(R.id.action_listPresensiFragment_to_addPresensiManualFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}