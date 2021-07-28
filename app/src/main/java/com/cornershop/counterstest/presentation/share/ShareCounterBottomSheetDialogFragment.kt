package com.cornershop.counterstest.presentation.share

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.BottomSheetDialogFragmentShareCounterBinding
import com.cornershop.counterstest.presentation.base.BaseBindingBottomSheetDialogFragment
import com.cornershop.counterstest.presentation.model.CounterToShare
import com.cornershop.counterstest.presentation.model.PeopleToShare
import com.cornershop.counterstest.presentation.share.adapter.CounterToShareAdapter
import com.cornershop.counterstest.presentation.share.adapter.PeopleToShareAdapter

class ShareCounterBottomSheetDialogFragment : BaseBindingBottomSheetDialogFragment<BottomSheetDialogFragmentShareCounterBinding>(),
    CounterToShareAdapter.CounterToShareListener, PeopleToShareAdapter.PeopleToShareListener {

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): BottomSheetDialogFragmentShareCounterBinding =
        BottomSheetDialogFragmentShareCounterBinding.inflate(inflater, container, false)

    private lateinit var viewModel: ShareCounterViewModel
    private lateinit var countersToShareAdapter: CounterToShareAdapter
    private lateinit var peopleToShareAdapter: PeopleToShareAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val counters: List<CounterToShare> = arguments?.getParcelableArrayList(ARG_COUNTERS_TO_SHARE)
            ?: throw IllegalArgumentException("Counters to share cannot be null")

        val people: List<PeopleToShare> = arguments?.getParcelableArrayList(ARG_PEOPLE_TO_SHARE)
            ?: throw IllegalArgumentException("People to share cannot be null")

        setupCountersAdapter(counters)
        setupPeopleAdapter(people)
    }

    override fun onCopyClicked(description: String) {
        val clipboardManager: ClipboardManager = ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText(getString(R.string.app_name), description))
        Toast.makeText(requireContext(), getString(R.string.content_copied), Toast.LENGTH_SHORT).show()
    }

    override fun onPeopleClicked(item: PeopleToShare) {
        Toast.makeText(requireContext(), getString(R.string.under_construction), Toast.LENGTH_SHORT).show()
    }

    private fun setupCountersAdapter(counters: List<CounterToShare>) {
        binding.rvCounters.adapter = CounterToShareAdapter(requireContext(), this).also {
            countersToShareAdapter = it
        }

        countersToShareAdapter.submitList(counters)
    }

    private fun setupPeopleAdapter(people: List<PeopleToShare>) {
        binding.rvPeople.adapter = PeopleToShareAdapter(requireContext(), this).also {
            peopleToShareAdapter = it
        }

        peopleToShareAdapter.submitList(people)
    }

    companion object {
        private const val TAG = "com.cornershop.counterstest.presentation.share.ShareCounterFragment"

        private const val ARG_COUNTERS_TO_SHARE = "$TAG.countersToShare"
        private const val ARG_PEOPLE_TO_SHARE = "$TAG.peopleToShare"

        fun show(fragmentManager: FragmentManager, counters: List<CounterToShare>,
            peopleToShare: List<PeopleToShare>): ShareCounterBottomSheetDialogFragment {
            return newInstance(counters, peopleToShare).also {
                fragmentManager.beginTransaction()
                    .add(it, TAG)
                    .commitAllowingStateLoss()
            }
        }

        private fun newInstance(counters: List<CounterToShare>,
            peopleToShare: List<PeopleToShare>): ShareCounterBottomSheetDialogFragment {
            return ShareCounterBottomSheetDialogFragment().apply {
                arguments = bundleOf(
                    ARG_COUNTERS_TO_SHARE to counters,
                    ARG_PEOPLE_TO_SHARE to peopleToShare
                )
            }
        }
    }

}