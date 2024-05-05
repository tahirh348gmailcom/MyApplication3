package com.example.myapplication3.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import com.example.myapplication3.R
import com.example.myapplication3.base.BaseDialog
import com.example.myapplication3.databinding.DialogInfoBinding
import com.example.myapplication3.models.MyExtraData
import com.example.myapplication3.interfaces.IDialog

class DialogInfo(var message: String, var positiveButtonText: String, var negativeButtonText: String, var iDialog: IDialog, var tag: Int, var extraData: MyExtraData, var layoutOption: Int, var cancellable: Boolean,) : BaseDialog(), View.OnClickListener {

    lateinit var binding: DialogInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.dialog_theme);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_info,
            container,
            false
        )
        resetViews()
        init()
        validateViews()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setDimAmount(0F)
        dialog?.window?.setGravity(Gravity.BOTTOM)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog?.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT

        dialog?.window?.attributes = lp
    }

    fun resetViews()
    {
        binding.llBottomOptionsOne.visibility = View.GONE
        binding.llBottomOptionsTwo.visibility = View.GONE
        binding.llBottomOptionsThree.visibility = View.GONE
        binding.etInputBox.visibility = View.GONE
        binding.tvMessageOne.visibility = View.GONE
        binding.tvMessageTwo.visibility = View.GONE
    }

    private fun init() {

        binding.tvMessageOne.text = message
        binding.tvMessageTwo.text = message
        binding.tvPositiveOne.text = positiveButtonText
        binding.tvPositiveTwo.text = positiveButtonText
        binding.tvPositiveThree.text = positiveButtonText

        binding.cvPositiveOne.setOnClickListener(this)
        binding.cvPositiveTwo.setOnClickListener(this)
        binding.cvPositiveThree.setOnClickListener(this)


        binding.tvNegativeOne.text = negativeButtonText
        binding.tvNegativeTwo.text = negativeButtonText

        binding.cvNegativeOne.setOnClickListener(this)
        binding.cvNegativeTwo.setOnClickListener(this)

        binding.blankView.setOnClickListener(this)
        binding.mainView.setOnClickListener(this)

    }

    fun validateViews()
    {
        when (layoutOption) {
            1 -> {
                binding.llBottomOptionsOne.visibility = View.VISIBLE
            }
            2 -> {
                binding.llBottomOptionsTwo.visibility = View.VISIBLE
            }
            3 -> {
                binding.llBottomOptionsThree.visibility = View.VISIBLE
            }
        }

        if(extraData.dialogEditText)
        {
            binding.etInputBox.visibility = View.VISIBLE
        }
        if(extraData.dialogTitleLeftAligned)
        {
            binding.tvMessageTwo.visibility = View.VISIBLE
        }else
        {
            binding.tvMessageOne.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.blankView -> {
                if(cancellable) {
                    dismiss()
                }
            }
            R.id.cvPositiveOne, R.id.cvPositiveTwo, R.id.cvPositiveThree -> {
                dismiss()
                extraData.dialogEditTextResult = binding.etInputBox.text.toString()
                iDialog.onDialogClick(true, tag, extraData)
            }
            R.id.cvNegativeOne, R.id.cvNegativeTwo -> {
                dismiss()
                extraData.dialogEditTextResult = binding.etInputBox.text.toString()
                iDialog.onDialogClick(false, tag, extraData)
            }
        }
    }
}
