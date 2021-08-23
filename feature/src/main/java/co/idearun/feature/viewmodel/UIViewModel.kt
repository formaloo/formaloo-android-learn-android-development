package co.idearun.feature.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.idearun.common.base.BaseViewModel
import co.idearun.data.model.form.Fields
import co.idearun.data.model.form.Form
import co.idearun.data.model.form.SubmitEntity
import co.idearun.data.model.form.createForm.CreateFormData
import co.idearun.data.repository.FormzRepo
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import timber.log.Timber
import java.io.File
import kotlin.random.Random

class UIViewModel(private val repository: FormzRepo) : BaseViewModel() {

    private var formSlug: String? = null
    private var formReqList = HashMap<String, String>()
    private var fileList = HashMap<String, Fields>()
    private var files: ArrayList<MultipartBody.Part> = arrayListOf()

    private val _formData = MutableLiveData<CreateFormData>().apply { value = null }
    val formData: LiveData<CreateFormData> = _formData
    private val _form = MutableLiveData<Form>().apply { value = null }
    val form: LiveData<Form> = _form
    private val _submitEntity = MutableLiveData<SubmitEntity>().apply { value = null }
    val submitEntity: LiveData<SubmitEntity> = _submitEntity
    private val _fields = MutableLiveData<ArrayList<Fields>>().apply { value = null }
    val fields: LiveData<ArrayList<Fields>> = _fields
    private val _errorField = MutableLiveData<Fields>().apply { value = null }
    val errorField: LiveData<Fields> = _errorField
    private val _fieldFielName = MutableLiveData<Fields>().apply { value = null }
    val fieldFielName: LiveData<Fields> = _fieldFielName
    private val _selectedTime = MutableLiveData<String>().apply { value = null }
    val selectedTime: LiveData<String> = _selectedTime
    private val _selectedDate = MutableLiveData<String>().apply { value = null }
    val selectedDate: LiveData<String> = _selectedDate
    private val _npsPos = MutableLiveData<Int>().apply { value = null }
    val npsPos: LiveData<Int> = _npsPos

    private var requiredField = arrayListOf<Fields>()


    fun initFormSlug(slug: String) {
        formSlug = slug
    }

    fun addKeyValueToReq(slug: String, value: Any) {
        formReqList[slug] = value.toString()
    }

    fun addFileToReq(field: Fields?, value: String) {
        field?.let {
            fileList[value] = field
            initFileName(field.slug, value)
        }

    }

    fun initFileName(slug: String?, filePath: String) {
        val file = File(filePath)
        val fielField = Fields()
        fielField.slug = slug
        fielField.title = file.name
        _fieldFielName.value = fielField

    }

    fun removeFile(slug: String?) {
        initFileName(null, "")
        val fileList = files

        fileList.forEach { mp ->
            Timber.d("removeFile ${mp.body}")

            mp.headers?.let { headers ->
                Timber.d("headers $headers")
                if (slug != null && headers.toString().contains(slug)) {
                    files.remove(mp)
                }

            }
        }
    }


    fun initSelectedDate(date: String) {
        Timber.d("initSelectedDate $date")
        _selectedDate.value = date
    }

    fun getSubmitEntity() = viewModelScope.launch {
        val entity = repository.getSubmitEntity(formSlug ?: "")

        entity?.let {
            if (entity.newRow == true) {
            } else {
                formReqList = entity.formReq
                fileList = entity.files
            }

            _submitEntity.value = entity
        }


    }


    fun saveEditSubmitToDB(newRow: Boolean, visibleItemPosition: Int) = viewModelScope.launch {
        if (visibleItemPosition == 0) {
            val value = SubmitEntity(
                0,
                Random.nextInt(),
                newRow,
                formSlug,
                formReqList,
                fileList
            )
            _submitEntity.value = value
            repository.saveSubmit(value)

        } else if (visibleItemPosition >= 1) {
            submitEntity.value?.let { value ->
                value.files = fileList
                value.formReq = formReqList
                value.newRow = newRow
                repository.saveSubmit(value)

            }


        }


    }


    fun setErrorToField(it: Fields, msg: String) {
        val errField = Fields()
        errField.slug = it.slug
        errField.title = msg
        _errorField.value = errField

    }


    fun npsBtnClicked(field: Fields, pos: Int) {
        _npsPos.value = pos
        addKeyValueToReq(field.slug!!, pos)
    }

    fun reuiredField(it: Fields) {
        requiredField.add(it)
    }

}