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
import co.planis.confession.model.ConfessionModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.row_confessions.view.*
import java.lang.reflect.InvocationTargetException
import java.text.SimpleDateFormat
import java.util.*


class MyFirebaseRecyclerAdapter(protected var mModelLayout: Int, ref: Query, val itemClick: (DataSnapshot) -> Unit, val likeClick: (DataSnapshot) -> Unit) : RecyclerView.Adapter<MyFirebaseRecyclerAdapter.ViewHolder>() {

    internal var mSnapshots: FirebaseArray

    companion object {
        @JvmStatic fun parseSnapshot(snapshot: DataSnapshot): ConfessionModel = snapshot.getValue(ConfessionModel::class.java)
    }

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

    override fun getItemCount(): Int = mSnapshots.count

    override fun getItemId(position: Int): Long = mSnapshots.getItem(position).key.hashCode().toLong()

    fun getItem(position: Int): DataSnapshot = mSnapshots.getItem(position)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mModelLayout, parent, false) as ViewGroup
        try {
            return ViewHolder(view, itemClick, likeClick)
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
        viewHolder.bindConfessions(model)
    }

    class ViewHolder(view: View, val itemClick: (DataSnapshot) -> Unit, val likeClick: (DataSnapshot) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindConfessions(confessions: DataSnapshot) {
            with(confessions) {
                itemView.confessionsContainerRl.setOnClickListener { itemClick(this) }
                itemView.confessionsLikesLabelTv.setOnClickListener { likeClick(this) }
            }

            val parsed: ConfessionModel = parseSnapshot(confessions)

            with(parsed) {
                itemView.confessionSimpleAuthorTv.text = op.name
                itemView.confessionSimpleContentTv.text = text
                itemView.confessionSimpleDateTv.text = SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY).format(date)
                itemView.confessionsCommentsNumberTv.text = comments.size.toString()
                itemView.confessionsLikesNumberTv.text = likes.toString()
            }
        }
    }
}
