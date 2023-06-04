package com.xyz.perfettodemo.memory

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.xyz.perfettodemo.databinding.ActivityLeakBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LeakActivity : AppCompatActivity() {
    private var view: View? = null

    private var context: Context? = null
    private var textView: TextView? = null
    private var testResource: TestResource? = null
    private var objectAnimator: ObjectAnimator? = null

    private lateinit var binding: ActivityLeakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 静态Activity(Activity上下文Context)和View
        context = this
        textView = TextView(this)
        // 单例造成的内存泄漏
        TestManager.getInstance(this)
        // 线程造成的内存泄漏
        anonymousInnerClass()
        // 非静态内部类创建静态实例造成的内存泄漏
        testResource = TestResource()

        // 在属性动画中有一类无限循环动画
        objectAnimator = ObjectAnimator.ofFloat(binding.btnAddView, "rotation", 0f, 360f)
        objectAnimator!!.repeatCount = ValueAnimator.INFINITE
        objectAnimator!!.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 静态Activity(Activity上下文Context)和View
//        context = null
//        textView = null
//        TestManager.getInstance(this)?.onDestroy()


    }


    //匿名内部类持有MemoryTestActivity实例引用，当耗时匿名线程内部类执行完成以后MemoryTestActivity实例才会回收；
    fun anonymousInnerClass() {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                //执行异步处理
                SystemClock.sleep(120000)
                return null
            }
        }.execute()
    }

    class TestResource {
        //资源类
    }


}


class TestManager private constructor(private var context: Context?) {
    companion object {
        private var manager: TestManager? = null

        /**
         * 如果传入的context是activity,service的上下文，会导致内存泄漏
         * 原因是我们的manger是一个static的静态对象，这个对象的生命周期和整个app的生命周期一样长
         * 当activity销毁的时候，我们的这个manger仍然持有者这个activity的context，就会导致activity对象无法被释放回收，就导致了内存泄漏
         */
        fun getInstance(context: Context): TestManager? {
            if (manager == null) {
                manager = TestManager(context)
            }
            return manager
        }
    }

    fun onDestroy() {
        context = null
        manager = null
    }
}