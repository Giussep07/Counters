package com.cornershop.counterstest.presentation.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.ActionMode
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.FragmentHomeBinding
import com.cornershop.counterstest.di.viewModel.ViewModelFactory
import com.cornershop.counterstest.presentation.base.BaseBindingFragment
import com.cornershop.counterstest.presentation.home.adapter.CounterAdapter
import com.cornershop.counterstest.presentation.home.adapter.CounterDetailsLookup
import com.cornershop.counterstest.presentation.home.adapter.CounterItemKeyProvider
import com.cornershop.counterstest.presentation.mapper.CounterPresentationMapper
import com.cornershop.counterstest.presentation.model.CounterItem
import com.cornershop.counterstest.presentation.model.PeopleToShare
import com.cornershop.counterstest.presentation.share.ShareCounterBottomSheetDialogFragment
import com.cornershop.counterstest.presentation.state.home.HomeDecreaseCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeDeleteCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeIncreaseCounterUiState
import com.cornershop.counterstest.presentation.state.home.HomeUiState
import com.cornershop.counterstest.presentation.utils.AlertDialogUtil
import javax.inject.Inject

class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(),
    CounterAdapter.CounterAdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject lateinit var counterPresentationMapper: CounterPresentationMapper

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }
    private lateinit var adapter: CounterAdapter
    private lateinit var tracker: SelectionTracker<CounterItem.CounterUiModel>
    private var actionMode: ActionMode? = null

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupRefreshLayout()

        binding.btnRetry.setOnClickListener {
            viewModel.getCounters()
        }

        binding.btnAddCounter.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCreateCounterFragment())
        }

        viewModel.getCounters()

        viewModel.homeUiState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeUiState.Loading -> {
                    binding.progressLoading.isVisible = true
                    binding.groupNoContent.isGone = true
                    binding.rvCounters.isGone = true
                    binding.groupError.isGone = true
                }
                is HomeUiState.Success -> {
                    binding.srlCounters.isRefreshing = false
                    if (it.counters.isEmpty()) {
                        binding.progressLoading.isGone = true
                        binding.groupNoContent.isVisible = true
                    } else {
                        binding.progressLoading.isGone = true
                        binding.rvCounters.isVisible = true
                        adapter.submitList(counterPresentationMapper.toUiModel(it.counters))
                    }
                }
                is HomeUiState.Error -> {
                    binding.srlCounters.isEnabled = false
                    binding.progressLoading.isGone = true
                    binding.groupError.isVisible = true
                    println("Counter Error: ${it.errorMessage}")
                }
            }
        }

        viewModel.homeDecreaseUiState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeDecreaseCounterUiState.Loading -> {
                    binding.progressLoading.isVisible = true
                    binding.groupError.isGone = true
                }
                is HomeDecreaseCounterUiState.Success -> {
                    if (it.counters.isEmpty()) {
                        binding.progressLoading.isGone = true
                        binding.groupNoContent.isVisible = true
                    } else {
                        binding.progressLoading.isGone = true
                        adapter.submitList(counterPresentationMapper.toUiModel(it.counters))
                    }
                }
                is HomeDecreaseCounterUiState.Error -> {
                    binding.progressLoading.isGone = true
                    binding.groupError.isVisible = true
                    println("Counter Error: ${it.errorMessage}")
                }
            }
        }

        viewModel.homeIncreaseUiState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeIncreaseCounterUiState.Loading -> {
                    binding.progressLoading.isVisible = true
                    binding.groupError.isGone = true
                }
                is HomeIncreaseCounterUiState.Success -> {
                    if (it.counters.isEmpty()) {
                        binding.progressLoading.isGone = true
                        binding.groupNoContent.isVisible = true
                    } else {
                        binding.progressLoading.isGone = true
                        adapter.submitList(counterPresentationMapper.toUiModel(it.counters))
                    }
                }
                is HomeIncreaseCounterUiState.Error -> {
                    binding.progressLoading.isGone = true
                    binding.groupError.isVisible = true
                    println("Counter Error: ${it.errorMessage}")
                }
            }
        }

        viewModel.homeDeleteUiState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeDeleteCounterUiState.Deleting -> {
                    binding.progressLoading.isVisible = true
                    binding.groupNoContent.isGone = true
                    binding.rvCounters.isGone = true
                    binding.groupError.isGone = true
                    tracker.clearSelection()
                    actionMode?.finish()
                }
                is HomeDeleteCounterUiState.Success -> {
                    tracker.clearSelection()
                    actionMode?.finish()
                    if (it.counters.isEmpty()) {
                        binding.progressLoading.isGone = true
                        binding.groupNoContent.isVisible = true
                    } else {
                        binding.progressLoading.isGone = true
                        binding.rvCounters.isVisible = true
                        adapter.submitList(counterPresentationMapper.toUiModel(it.counters))
                    }
                }
                is HomeDeleteCounterUiState.Error -> {
                    binding.progressLoading.isGone = true
                    binding.groupError.isVisible = true
                    binding.rvCounters.isGone = true
                    tracker.clearSelection()
                    actionMode?.finish()
                    println("Counter Error: ${it.errorMessage}")
                }
            }
        }
    }

    override fun onDecreaseClicked(counterUiModel: CounterItem.CounterUiModel) {
        viewModel.decreaseCounter(counterUiModel)
    }

    override fun onIncreaseClicked(counterUiModel: CounterItem.CounterUiModel) {
        viewModel.increaseCounter(counterUiModel)
    }

    private fun setupRecyclerView() {
        binding.rvCounters.adapter = CounterAdapter(requireContext(), this).also {
            adapter = it
        }

        tracker = SelectionTracker.Builder(
            "counter-tracker",
            binding.rvCounters,
            CounterItemKeyProvider(adapter),
            CounterDetailsLookup(binding.rvCounters),
            StorageStrategy.createParcelableStorage(CounterItem.CounterUiModel::class.java)
        ).build()

        tracker.addObserver(object : SelectionTracker.SelectionObserver<CounterItem.CounterUiModel>() {
            override fun onSelectionChanged() {
                if (tracker.hasSelection()) {
                    if (actionMode == null) {
                        actionMode = (requireActivity() as AppCompatActivity).startActionMode(actionModeCallback)
                    }
                    updateContextualActionBarTitle()
                } else {
                    actionMode?.finish()
                }
            }
        })

        adapter.selectionTracker = tracker
    }

    private fun updateContextualActionBarTitle() {
        actionMode?.title = getString(R.string.n_selected, tracker.selection.size())
    }

    private fun setupRefreshLayout() {
        binding.srlCounters.setColorSchemeResources(R.color.orange)

        binding.srlCounters.setOnRefreshListener {
            viewModel.getCounters()
        }
    }

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.home_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.option_delete -> {
                    deleteCounters()
                    true
                }
                R.id.option_share -> {
                    showShareBottomSheet()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            tracker.clearSelection()
            actionMode = null
        }
    }

    private fun deleteCounters() {
        val countersSelected = tracker.selection.map { it }
        val title: String = if (countersSelected.size == 1) {
            getString(R.string.delete_one_question, countersSelected[0].title)
        } else {
            getString(R.string.delete_x_question, countersSelected.size)
        }

        AlertDialogUtil.generalDialog(
            context = requireContext(),
            title = null,
            message = title,
            textButtonAccept = getString(R.string.delete),
            textButtonCancel = getString(R.string.cancel),
            action = {
                viewModel.deleteCounters(countersSelected)
            }
        )
    }

    private fun showShareBottomSheet() {
        val countersToShare = counterPresentationMapper.fromUIModelToCounterToShare(tracker.selection.map { it })
        val peopleToShare = mutableListOf<PeopleToShare>().apply {
            add(PeopleToShare(1, R.drawable.avatar_alvaro, "Alvaro"))
            add(PeopleToShare(2, R.drawable.avatar_daniel, "Daniel"))
            add(PeopleToShare(3, R.drawable.avatar_danito, "Danito"))
            add(PeopleToShare(4, R.drawable.avatar_gonzalo, "Gonzalo"))
        }

        ShareCounterBottomSheetDialogFragment.show(childFragmentManager, countersToShare, peopleToShare)
    }

}
