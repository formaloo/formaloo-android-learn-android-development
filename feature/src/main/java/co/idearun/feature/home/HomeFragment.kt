package co.idearun.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import co.idearun.common.base.BaseFragment
import co.idearun.common.base.BaseViewModel
import co.idearun.common.exception.Failure
import co.idearun.data.model.form.Form
import co.idearun.feature.databinding.FragmentHomeBinding
import co.idearun.feature.MainListener
import co.idearun.feature.home.adapter.LessonListListener
import co.idearun.feature.home.adapter.LessonsAdapter
import co.idearun.feature.lesson.LessonActivity
import co.idearun.feature.viewmodel.FormViewModel
import co.idearun.feature.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import timber.log.Timber


class HomeFragment : BaseFragment(), KoinComponent, LessonListListener, MainListener {

    private var openedForm: Form? = null
    private lateinit var binding: FragmentHomeBinding
    private lateinit var formListAdapter: LessonsAdapter
    private val viewModel: FormViewModel by viewModel()
    private val shardedVM: SharedViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()

    }

    private fun initData() {
        getLessonsList(true)

        getLastLessonData()

        viewModel.failure.observe(viewLifecycleOwner, {
            when (it) {
                is Failure.FeatureFailure -> renderFailure(it.msgRes)

                else -> {

                }
            }
        })

        viewModel.form.observe(viewLifecycleOwner, {
            it?.let {
                binding.lessonInprogress.progress = shardedVM.retrieveLessonProgress()[it.slug]
                binding.lessonInprogress.item = it
                binding.lessonInprogress.listener = this
                binding.executePendingBindings()

            }

        })


    }

    private fun getLastLessonData() {
        val lastLesson = shardedVM.getLastLesson()
        if (lastLesson?.isNotEmpty() == true) {
            viewModel.initLessonSlug(lastLesson)
            viewModel.retrieveLessonFromDB()

        } else {
            binding.lessonInprogress.progress = 0
            binding.executePendingBindings()
        }


    }

    private fun initView() {
        formListAdapter = LessonsAdapter(shardedVM.retrieveLessonProgress(), this)

        binding.lessonRv.apply {
            adapter = formListAdapter
            layoutManager = LinearLayoutManager(this.context)

        }

    }

    override fun openLessonPage(form: Form?, formItemLay: View, progress: Int) {
        openedForm = form
        val intent = Intent(requireActivity(), LessonActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            formItemLay,
            ViewCompat.getTransitionName(formItemLay) ?: ""
        )
        intent.putExtra("form", form)
        intent.putExtra("progress", progress)

        startActivity(intent, options.toBundle())

    }

    private fun renderFailure(message: String?) {
        Timber.d("renderFailure $message")
        getLessonsList(true)

    }

    override fun onResume() {
        getLastLessonData()
        formListAdapter.resetProgress(shardedVM.retrieveLessonProgress(), openedForm)
        super.onResume()

    }

    private fun getLessonsList(force: Boolean) {
        lifecycleScope.launch {
            viewModel.fetchLessonList(force).collectLatest { pagingData ->
                formListAdapter.submitData(pagingData)

            }
        }

    }
}

