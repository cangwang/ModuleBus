package cangwang.com.slide

import android.os.Bundle

import android.view.animation.Animation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment

/**侧边弹框
 * Created by cangwang on 2018/2/10.
 */
class SlideFragment : Fragment() {
    private var slideView: View? = null
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_right)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.slide_out_from_left)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        slideView = inflater.inflate(R.layout.slide_fragment_layout, container, false)
        slideView?.setOnClickListener(View.OnClickListener { })
        return view
    }

    companion object {
        const val TAG = "SlideFragment"
    }
}