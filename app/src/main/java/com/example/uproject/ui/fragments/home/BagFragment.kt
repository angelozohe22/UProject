package com.example.uproject.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uproject.R
import com.example.uproject.core.Resource
import com.example.uproject.core.aplication.ctx
import com.example.uproject.data.firebase.home.FirebaseFirestoreDataSourceImpl
import com.example.uproject.data.local.db.DulcekatDataBase
import com.example.uproject.data.local.db.entity.BagProductEntity
import com.example.uproject.data.local.source.LocalDataSourceImpl
import com.example.uproject.databinding.FragmentBagBinding
import com.example.uproject.domain.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.activities.home.pay.PayActivity
import com.example.uproject.ui.fragments.home.adapter.BagAdapter
import com.example.uproject.ui.viewmodels.home.BagViewModel
import com.example.uproject.ui.viewmodels.home.factoryhome.HomeViewModelFactory
import kotlin.math.roundToInt

class BagFragment : Fragment(), BagAdapter.OnProductBagClickListener {

    private var _binding: FragmentBagBinding? = null
    private val binding get() = _binding!!

    private var currentBagList = mutableListOf<BagProductEntity>()

    var totalPrice = 0.0

    private val viewModel by activityViewModels<BagViewModel> {
        HomeViewModelFactory(
            DulcekatRepositoryImpl(
                LocalDataSourceImpl(DulcekatDataBase.getInstance(requireContext())),
                FirebaseFirestoreDataSourceImpl()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBagBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupListener()
    }

    private fun setupRecycler() {
        binding.rvListBag.apply {
            layoutManager = LinearLayoutManager( ctx,
                LinearLayoutManager.VERTICAL,false)
        }

        viewModel.fetchBagProductList().observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        currentBagList = result.data.toMutableList()
                        val bagList = result.data
                        if (bagList.isNotEmpty()) {
                            for (product in bagList) {
                                totalPrice += product.price_by_one*product.quantity
                            }
                            totalPrice = (totalPrice * 100.0).roundToInt() / 100.0
                            binding.rvListBag.adapter = BagAdapter(bagList, this)
                            binding.tvTotalPrice.text = "S/ ".plus(totalPrice.toString())
                            binding.btnPay.apply {
                                isEnabled = true
                                setBackgroundResource(R.drawable.btn_corner)
                            }
                            val text = (if(bagList.size<10) "0" else "")
                                .plus(bagList.size)
                                .plus(if (bagList.size == 1) " producto" else " productos")
                            binding.tvTotalQuantityBag.text = text
                        } else {
                            binding.rvListBag.visibility = View.GONE
                            binding.imgDefaultEmptyBag.visibility = View.VISIBLE
                            binding.titleDefaultEmptyBag.visibility = View.VISIBLE
                            binding.rvListBag.adapter = BagAdapter(emptyList(), this)
                            binding.btnPay.apply {
                                isEnabled = false
                                setBackgroundResource(R.drawable.btn_corner_dissable)
                            }
                        }
                    }
                    is Resource.Failure -> {
                    }
                }
            }
        })

    }

    override fun onProductBagClicked(idproduct: Int) {
        viewModel.deleteProductFromBag(idproduct)

        val newList = mutableListOf<BagProductEntity>()
        for (bagProduct in currentBagList){
            if(bagProduct.idproduct != idproduct){
                newList.add(bagProduct)
            }
        }
        currentBagList = newList
        newList.toList()
        val text = (if(currentBagList.size<10 && currentBagList.size != 0 ) "0" else "")
            .plus(currentBagList.size)
            .plus(if (currentBagList.size == 1) " producto" else " productos")
        binding.tvTotalQuantityBag.text = text

        totalPrice = 0.0

        if(newList.isNotEmpty()){
            for (product in currentBagList) {
                totalPrice += product.price_by_one*product.quantity
            }
            totalPrice = (totalPrice * 100.0).roundToInt() / 100.0
            binding.rvListBag.adapter = BagAdapter(newList, this)
            binding.tvTotalPrice.text = "S/ ".plus(totalPrice.toString())
            binding.btnPay.apply {
                isEnabled = true
                setBackgroundResource(R.drawable.btn_corner)
            }
        }else{
            binding.tvTotalPrice.text = "S/ ".plus("0")
            binding.rvListBag.visibility = View.GONE
            binding.imgDefaultEmptyBag.visibility = View.VISIBLE
            binding.titleDefaultEmptyBag.visibility = View.VISIBLE
            binding.rvListBag.adapter = BagAdapter(emptyList(), this)
            binding.btnPay.apply {
                isEnabled = false
                setBackgroundResource(R.drawable.btn_corner_dissable)
            }
        }



        Toast.makeText(requireContext(), "Producto eliminado de la bolsa", Toast.LENGTH_SHORT).show()
    }

    private fun setupListener(){
        binding.btnPay.setOnClickListener {
            val intent = Intent(requireContext(), PayActivity::class.java)
            intent.putExtra("totalprice", totalPrice)
            requireActivity().startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}