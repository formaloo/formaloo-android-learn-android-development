package co.idearun.feature.lesson

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.idearun.common.Constants
import co.idearun.common.extension.invisible
import co.idearun.common.extension.visible
import co.idearun.data.model.form.Fields
import co.idearun.data.model.form.Form
import co.idearun.feature.R
import co.idearun.feature.databinding.ActivityLessonBinding
import co.idearun.feature.lesson.adapter.LessonFieldsAdapter
import co.idearun.feature.lesson.listener.LessonListener
import co.idearun.feature.lesson.listener.SwipeStackListener
import co.idearun.feature.viewmodel.FormViewModel
import co.idearun.feature.viewmodel.SharedViewModel
import co.idearun.feature.viewmodel.UIViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class LessonActivity : AppCompatActivity(), LessonListener {

    private lateinit var binding: ActivityLessonBinding
    private val shardedVM: SharedViewModel by viewModel()
    private val viewModel: UIViewModel by viewModel()
    private val formVM: FormViewModel by viewModel()
    private var fieldsFlashAdapter: LessonFieldsAdapter? = null
    private var form: Form? = null
    private var lessonsProgressMap = hashMapOf<String?, Int?>()
    private var fields: ArrayList<Fields> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_lesson)
        binding.flashcardListener = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.layoutFashCong.flashcardListener = this
        binding.layoutFashCong.viewmodel = viewModel
        supportActionBar?.hide()

        checkBundle()
        initData()
        initView()
    }

    private fun checkBundle() {
        intent.extras?.let {
            it.getSerializable("form")?.let {
                if (it is Form) {

                    it.fields_list?.let {
                        this.fields = it
                    }
                    form = it


                } else {

                }
            }
        }
    }

    private fun initView() {
        fieldsFlashAdapter = LessonFieldsAdapter(
            this@LessonActivity,
            object : SwipeStackListener {
                override fun onSwipeEnd(position: Int) {
                    next()
                }
            },
            form!!, viewModel,
        )

        binding.flashcardFieldsRec.apply {
            adapter = fieldsFlashAdapter
            layoutManager =
                object : LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }

                    override fun canScrollHorizontally(): Boolean {
                        return false
                    }

                }

        }


    }

    private fun initData() {
        formVM.initLessonSlug(form?.slug ?: "")
        formVM.initLessonAddress(form?.address ?: "")
        formVM.getFormData()

        shardedVM.saveLastLesson(form?.slug ?: "")

        viewModel.initFormSlug(form?.slug ?: "")
        viewModel.getSubmitEntity()

        formVM.form.observe(this, {
            it?.let {
                form = it
                it.fields_list?.let {
                    this.fields = it
                }
                checkLessonProgress()

                binding.form = form
                binding.layoutFashCong.form = form
                binding.executePendingBindings()

            }

        })

    }


    override fun next() {
        with(binding.flashcardFieldsRec) {
            val visibleItemPosition =
                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            viewModel.saveEditSubmitToDB(false, visibleItemPosition)

            val next = visibleItemPosition + 1
            updateLessonProgress(next)

            if (fields.size > next) {
                Handler(Looper.getMainLooper()).postDelayed({
                    scrollToPosition(next)
                }, Constants.SCROLL_DELAY)

                checkNeedActionFields(next)

            } else {
                openCongView()
            }

            binding.flashPreBtn.visible()

            binding.progress = next


            Timber.d("visibleItemPosition$visibleItemPosition")
        }

    }

    private fun checkNeedActionFields(pos: Int): Boolean {
        val type = fieldsFlashAdapter?.collection?.get(pos)?.type

        if (type == Constants.SINGLE_SELECT || type == Constants.DROPDOWN || type == Constants.YESNO || type == Constants.RATING || type == Constants.META) {
            binding.flashcardDoneBtn.invisible()

        } else {
            binding.flashcardDoneBtn.visible()

        }

        return false
    }

    override fun pre() {
        with(binding.flashcardFieldsRec) {
            val visibleItemPosition =
                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()


            updateLessonProgress(visibleItemPosition - 1)

            if (0 <= visibleItemPosition - 1) {
                scrollToPosition(visibleItemPosition - 1)

            }

            if (visibleItemPosition == 1) {
                binding.flashPreBtn.invisible()
            }

            binding.flashNextBtn.visible()
            binding.progress = visibleItemPosition
        }
    }

    override fun closePage() {
        onBackPressed()

    }

    override fun share() {
        val shareTxt =
            "I'm learning ${form?.title ?: ""} in Learning App. Check it out on your phone: ${
                getString(R.string.appAddress)
            }"
        shareVia(shareTxt, getString(R.string.app_name), this)

    }
    fun shareVia(extraTxt: String, title: String, mContext: Context) {
        val sendIntent = Intent()
        sendIntent.type = "text/plain"
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, extraTxt)
        val createChooser = Intent.createChooser(sendIntent, title)
        createChooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        mContext.startActivity(createChooser)
    }
    private fun openCongView() {
        updateLessonProgress(-1)
        shardedVM.saveLastLesson("")
        val visibleItemPosition =
            (binding.flashcardFieldsRec.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        Timber.d("visibleItemPosition$visibleItemPosition")
        viewModel.saveEditSubmitToDB(true, visibleItemPosition)
        binding.flashCongView.visible()

    }

    private fun updateLessonProgress(pos: Int) {
        lessonsProgressMap[form?.slug] = pos
        shardedVM.saveLessonProgress(lessonsProgressMap)

    }

    private fun checkLessonProgress() {
        lessonsProgressMap = shardedVM.retrieveLessonProgress()

        val formSlug = form?.slug ?: ""
        val progress = lessonsProgressMap[form?.slug ?: ""]

        if (progress == null || progress == 0 || progress == -1) {
            lessonsProgressMap[formSlug] = 0
            shardedVM.saveLessonProgress(lessonsProgressMap)
        } else {
            binding.flashPreBtn.visible()
            binding.flashcardFieldsRec.scrollToPosition(progress + 1)
        }

        if (progress ?: 0 == 0) {
            binding.flashPreBtn.invisible()

        }

        binding.progress = (progress ?: 0)
        binding.executePendingBindings()

    }

}