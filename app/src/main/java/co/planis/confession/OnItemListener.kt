package co.planis.confession

import co.planis.confession.model.ConfessionModel

/**
 * Created by janpluta on 03.10.16.
 */
interface OnItemListener {
    fun onItemClick(model :ConfessionModel)
    fun onLikeClick(model: ConfessionModel)
}