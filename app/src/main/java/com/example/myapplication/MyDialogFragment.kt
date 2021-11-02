package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MyDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_dialog, container, false)

        val deferringInsetsListener = RootViewDeferringInsetsCallback(
            persistentInsetTypes = WindowInsetsCompat.Type.systemBars(),
            deferredInsetTypes = WindowInsetsCompat.Type.ime()
        )
      /*  ViewCompat.setWindowInsetsAnimationCallback(view, deferringInsetsListener)
        ViewCompat.setOnApplyWindowInsetsListener(view, deferringInsetsListener)*/
        ViewCompat.setWindowInsetsAnimationCallback(view.findViewById(R.id.edit_text), TranslateDeferringInsetsAnimationCallback(
            view = view.findViewById(R.id.edit_text),
            persistentInsetTypes = WindowInsetsCompat.Type.systemBars(),
            deferredInsetTypes = WindowInsetsCompat.Type.ime(),
            // We explicitly allow dispatch to continue down to binding.messageHolder's
            // child views, so that step 2.5 below receives the call
            dispatchMode = WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE
        ))

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.animation)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //WindowCompat.getInsetsController(dialog.window!!, view)?.hide(WindowInsetsCompat.Type.statusBars())

        super.onViewCreated(view, savedInstanceState)
        //view.findViewById<RecyclerView>(R.id.recycler).adapter = ConversationAdapter()
    }



    override fun onResume() {
        super.onResume()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        val window = dialog.window
        if (window != null) {
            window.setGravity(Gravity.BOTTOM)
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
            window.setBackgroundDrawable(ColorDrawable(0))
            WindowCompat.setDecorFitsSystemWindows(window, false)

            // we will manually show softKeyboard with postDelayed because of some bugs on vivo.
            //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            // TODO 隐藏状态栏
            //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            //window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            //window.statusBarColor = Color.TRANSPARENT
            // we will manually show softKeyboard with postDelayed because of some bugs on vivo.
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        }
        return dialog
    }


    fun showSoftKeyBoard(context: Context?, editText: EditText?) {
        if (context != null && editText != null) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

}