package com.example.myapplication3.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.interfaces.OnItemClickListener
import com.example.myapplication3.interfaces.OnItemClickListenerWithTag
import com.example.myapplication3.models.BaseModel


class RecyclerViewAdapter<T : BaseModel>(var list: List<T>, val onItemClickListener: OnItemClickListener<T>, var layout: Int) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    var onItemClickListenerWithTag: OnItemClickListenerWithTag<T>? = null
    var tag: Int? = null

    fun setItemClickListenerWithTag(onItemClickListenerWithTag: OnItemClickListenerWithTag<T>, tag: Int) {
        this.onItemClickListenerWithTag = onItemClickListenerWithTag
        this.tag = tag
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder?.binding?.setVariable(BR.item, getItem(position));
        holder?.binding?.setVariable(BR.itemClickListener, onItemClickListener)
        if (onItemClickListenerWithTag != null) {
            holder?.binding?.setVariable(BR.itemClickListenerWithTag, onItemClickListenerWithTag)
        }
        if (tag != null) {
            holder.binding.setVariable(BR.tag, tag)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.getContext()), viewType, parent, false);
        return MyViewHolder(binding);
    }

    class MyViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        var binding: ViewDataBinding
            internal set

        init {
            this.binding = binding
            this.binding.executePendingBindings()
        }
    }

    fun getItem(position: Int): BaseModel {
        return list.get(position)
    }

    override fun getItemViewType(position: Int): Int {
        return layout
    }


    fun setSearchList(list: List<T>) {
        this.list = list
    }

    fun getPosition(@Nullable item: T): Int {
        return list.indexOf(item)
    }

    fun setSelectedItems(list: List<T>) {
        for (mObject in this.list) {
            mObject.selected = list.contains(mObject)
        }
    }

    fun checkAll(value: Boolean) {
        for (i in list.indices) {
            val item = list[i]
            item.selected = value
            notifyItemChanged(i, item)
        }
    }

    fun changeSelection(position: Int, isMultiSelect: Boolean) {
        for (i in list.indices) {
            if (position == i) {
                list[i].selected = !list[i].selected
            } else {
                if (!isMultiSelect) {
                    list[i].selected = false
                    notifyItemChanged(i, list[i])
                }
            }
        }
        notifyItemChanged(position)
    }


}
