/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.firebase.ui.database

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.planis.confession.OnItemListener
import co.planis.confession.model.ConfessionModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.row_confessions.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import java.lang.reflect.InvocationTargetException
import java.text.SimpleDateFormat
import java.util.*


class MyFirebaseRecyclerAdapter


(protected var mModelLayout: Int, ref: Query,var listener : OnItemListener?) : RecyclerView.Adapter<MyFirebaseRecyclerAdapter.ViewHolder>() {
    internal var mSnapshots: FirebaseArray

    init {
        mSnapshots = FirebaseArray(ref)

        mSnapshots.setOnChangedListener { type, index, oldIndex ->
            when (type) {
                FirebaseArray.OnChangedListener.EventType.Added -> notifyItemInserted(index)
                FirebaseArray.OnChangedListener.EventType.Changed -> notifyItemChanged(index)
                FirebaseArray.OnChangedListener.EventType.Removed -> notifyItemRemoved(index)
                FirebaseArray.OnChangedListener.EventType.Moved -> notifyItemMoved(oldIndex, index)
                else -> throw IllegalStateException("Incomplete case statement")
            }
        }
    }



    fun cleanup() {
        mSnapshots.cleanup()
    }

    override fun getItemCount(): Int {
        return mSnapshots.count
    }

    fun getItem(position: Int): ConfessionModel {
        return parseSnapshot(mSnapshots.getItem(position))
    }


    protected fun parseSnapshot(snapshot: DataSnapshot): ConfessionModel {
        return snapshot.getValue(ConfessionModel::class.java)
    }

    fun getRef(position: Int): DatabaseReference {
        return mSnapshots.getItem(position).ref
    }

    override fun getItemId(position: Int): Long {
        // http://stackoverflow.com/questions/5100071/whats-the-purpose-of-item-ids-in-android-listview-adapter
        return mSnapshots.getItem(position).key.hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mModelLayout, parent, false) as ViewGroup
        try {
            return ViewHolder(view,listener)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val model = getItem(position)
        populateViewHolder(viewHolder, model)
    }

    /**
     * Each time the data at the given Firebase location changes, this method will be called for each item that needs
     * to be displayed. The first two arguments correspond to the mLayout and mModelClass given to the constructor of
     * this class. The third argument is the item's position in the list.
     *
     *
     * Your implementation should populate the view using the data contained in the model.

     * @param viewHolder The view to populate
     * *
     * @param model      The object containing the data used to populate the view
     * *
     * @param position  The position in the list of the view being populated
     */
    protected fun populateViewHolder(viewHolder: ViewHolder, model: ConfessionModel){
        viewHolder.bindConfessions(model)
    }



    class ViewHolder(view: View, var listener: OnItemListener?) : RecyclerView.ViewHolder(view), AnkoLogger {

        fun bindConfessions(confessions: ConfessionModel) {

                itemView.confessionSimpleAuthorTv.text = confessions.op.name
                itemView.confessionSimpleContentTv.text = confessions.text
                itemView.confessionSimpleDateTv.text = SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY).format(confessions.date)
                itemView.confessionsCommentsNumberTv.text = confessions.comments.size.toString()
                itemView.confessionsLikesNumberTv.text = confessions.likes.toString()
                itemView.setOnClickListener { debug { "item click" } }
                itemView.confessionsContainerRl.setOnClickListener { debug { "container click"} }
                itemView.confessionsLikesLabelTv.setOnClickListener { debug { "like click" } }

        }
    }
}
