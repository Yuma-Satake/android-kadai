package jp.ac.nkc_ct4a14.quiz_ct4a14

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AnswerDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_IS_CORRECT = "is_correct"
        private const val ARG_ANSWER = "answer"

        fun newInstance(isCorrect: Boolean, answer: String): AnswerDialogFragment {
            val fragment = AnswerDialogFragment()
            val args = Bundle()
            args.putBoolean(ARG_IS_CORRECT, isCorrect)
            args.putString(ARG_ANSWER, answer)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val isCorrect = arguments?.getBoolean(ARG_IS_CORRECT) ?: false
        val answer = arguments?.getString(ARG_ANSWER) ?: ""

        val title = if (isCorrect) {
            getString(R.string.dialog_correct)
        } else {
            getString(R.string.dialog_incorrect)
        }

        val message = getString(R.string.dialog_answer, answer)

        isCancelable = false

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.btn_ok)) { _, _ ->
                (activity as? MainActivity)?.checkQuizCount()
            }
            .create()
    }
}
