package co.planis.confession

import co.planis.confession.model.ConfessionModel

/**
 * Created by janpluta on 03.10.16.
 */
abstract class OnItemListener {
    abstract fun onItemClick(model :ConfessionModel)
    abstract fun onLikeClick(model: ConfessionModel)
}