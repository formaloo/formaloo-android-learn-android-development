package co.idearun.data.remote

import co.idearun.common.BuildConfig.FORM_TAG
import okhttp3.MultipartBody
import okhttp3.RequestBody


/**
 * Implementation of [UserService] interface
 */

class FormDatasource(private val service: FormService) {
    suspend fun getForms(page: Int) = service.getForms(FORM_TAG, page, 20)
    fun getCatList() = service.getCategories()
    fun getFormData(formAddress: String?) = service.getFormDetail(formAddress)
    fun search(searchStr: String) = service.search(searchStr)
    fun searchForms(searchStr: String) = service.searchForms(searchStr)
    fun submitForm(
        slug: String,
        req: HashMap<String, RequestBody>,
        files: List<MultipartBody.Part>?
    ) = service.submitForm(slug, req, files)

}