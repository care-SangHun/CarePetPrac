package com.example.carepetprac.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.carepetprac.R
import com.example.carepetprac.item.RecyclerItemFrag2
import java.util.ArrayList

class RecyclerAdapter (val itemList:ArrayList<RecyclerItemFrag2>) :
    RecyclerView.Adapter<RecyclerAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_frag2,parent, false)
        return VH(view).apply {
            send.setOnClickListener {
                Toast.makeText(parent.context, "send", Toast.LENGTH_SHORT).show()
                val imm = parent.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(send.windowToken, 0)
                comment.text=null
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }


    class VH(itemView:View) :RecyclerView.ViewHolder(itemView){
        val title:TextView=itemView.findViewById(R.id.rv_title)
        val image: ImageView =itemView.findViewById(R.id.rv_image)
        val mainText:TextView=itemView.findViewById(R.id.rv_tv)
        val send:ImageView=itemView.findViewById(R.id.iv_comment)
        val comment:EditText=itemView.findViewById(R.id.rv_et)


        fun bind(item:RecyclerItemFrag2){
            title.text=item.title
            image.setImageResource(item.image)
            mainText.text=item.mainText
        }

    }
}