package com.example.carepetprac.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carepetprac.R
import com.example.carepetprac.adapter.RecyclerAdapter
import com.example.carepetprac.item.RecyclerItemFrag2
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Fragment2 : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_2, container, false)
        val icon1 =view.findViewById<ImageView>(R.id.icon1)
        val icon2 =view.findViewById<ImageView>(R.id.icon2)
        recyclerView = view.findViewById(R.id.recyclerView_frag2)

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)

        icon1.setOnClickListener {
            Toast.makeText(requireContext(), "icon 1", Toast.LENGTH_SHORT).show()
        }

        icon2.setOnClickListener {
            Toast.makeText(requireContext(), "icon 2", Toast.LENGTH_SHORT).show()
        }

        val list:ArrayList<RecyclerItemFrag2> = arrayListOf(
            RecyclerItemFrag2("1",R.drawable.image1,"1"),
            RecyclerItemFrag2("2",R.drawable.image2,"2"),
            RecyclerItemFrag2("3",R.drawable.image3,"3"),
            RecyclerItemFrag2("4",R.drawable.image1,"4"),
            RecyclerItemFrag2("5",R.drawable.image2,"5")
        )

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        adapter= RecyclerAdapter(list)
        recyclerView.adapter=adapter

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_frag2, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item1 -> {
                Toast.makeText(requireContext(), "menu_item1", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_item2 -> {
                Toast.makeText(requireContext(), "menu_item2", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}