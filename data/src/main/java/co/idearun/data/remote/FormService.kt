package co.idearun.data.remote

import co.idearun.data.model.cat.catList.CatListRes
import co.idearun.data.model.form.createForm.CreateFormRes
import co.idearun.data.model.form.formList.FormListRes
import co.idearun.data.model.search.SearchRes
import co.idearun.data.model.submitForm.SubmitFormRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface FormService {

    companion object {
        private const val VERSION1 = "v1/"
        private const val VERSION2 = "v2/"
        private const val VERSION3 = "v3/"

        private const val FORMS = "${VERSION3}form-displays/tag/{tag}/?pagination=0"
        private const val FORM_DETAIL = "${VERSION3}form-displays/address/{address}/"
        private const val submitForm = "${VERSION3}form-displays/slug/{slug}/submit/"
        private const val CAT_LIST = "${VERSION2}forms/category/list/"
        private const val SEARCH_FORMS = "${VERSION2}forms/list/?"
        private const val SEARCH = "${VERSION2}forms/search/?"

    }

    @GET(FORMS)
    suspend fun getForms(
        @Path("tag") tag: String,
        @Query("page") page: Int,
        @Query("page_size") page_size: Int
    ): Response<FormListRes>


    @GET(SEARCH)
    fun search(@Query("search") search: String?): Call<SearchRes>

    @GET(CAT_LIST)
    fun getCategories(): Call<CatListRes>


    @GET(SEARCH_FORMS)
    fun searchForms(@Query("search") query: String): Call<FormListRes>


    @GET(FORM_DETAIL)
    fun getFormDetail(@Path("address") address: String?): Call<CreateFormRes>

    @Multipart
    @POST(submitForm)
    fun submitForm(
        @Path("slug") slug: String,
        @PartMap req: HashMap<String, RequestBody>,
        @Part files: List<MultipartBody.Part>?
    ): Call<SubmitFormRes>


}