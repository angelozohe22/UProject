package com.example.uproject.ui.modules.home.products

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uproject.ui.modules.home.products.fragments.accesorios.AccesoriosFragment
import com.example.uproject.ui.modules.home.products.fragments.confiteria.ConfiteriaFragment
import com.example.uproject.ui.modules.home.products.fragments.decorativos.DecorativosFragment
import com.example.uproject.ui.modules.home.products.fragments.insumos.InsumosFragment

class ProductsViewPagerAdapter(fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity){

    override fun getItemCount(): Int = 4

    //inicializarlos antes
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> InsumosFragment()
            1 -> AccesoriosFragment()
            2 -> ConfiteriaFragment()
            3 -> DecorativosFragment()
            else -> InsumosFragment()
        }
    }


}