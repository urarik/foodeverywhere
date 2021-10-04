package com.example.foodanywhere.businesslogic

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout
import com.example.foodanywhere.FoodCallBack
import com.example.foodanywhere.User
import com.example.foodanywhere.datatype.Nation
import com.example.foodanywhere.viewmodel.NationViewModel

class NationLogic(private val callback: FoodCallBack? = null, private val viewModel: NationViewModel, private val layoutExpand: LinearLayout) {

    fun onArrowClick(view: View, nation: Nation){
        with(viewModel) {
            val show = toggleNation(!isExpanded[nation.name]!!, view, layoutExpand)
            isExpanded[nation.name] = show
        }
    }

    private fun toggleNation(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
        if (isExpanded) {
            view.measure(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val actualHeight = view.measuredHeight

            layoutExpand.layoutParams.height = 0
            layoutExpand.visibility = View.VISIBLE

            val animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    layoutExpand.layoutParams.height =
                        if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT
                        else (actualHeight * interpolatedTime).toInt()

                    view.requestLayout()
                }
            }.apply {
                duration =
                    ((actualHeight / layoutExpand.context.resources.displayMetrics.density).toLong()) * 2
            }
            layoutExpand.startAnimation(animation)
        } else {
            val actualHeight = view.measuredHeight
            val animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    if (interpolatedTime == 1f) layoutExpand.visibility = View.GONE
                    else {
                        layoutExpand.layoutParams.height =
                            (actualHeight - (actualHeight * interpolatedTime)).toInt()
                        layoutExpand.requestLayout()
                    }
                }
            }.apply {
                duration =
                    (actualHeight / view.context.resources.displayMetrics.density).toLong()
            }
            layoutExpand.startAnimation(animation)
        }
        return isExpanded
    }

    fun onNationClick(nation: Nation) {
        User.currentNation = nation.name
        callback!!.transactFragment()
    }
}