package co.idearun.data.remote.di

import co.idearun.data.remote.FormDatasource
import co.idearun.data.remote.FormService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createRemoteFormModule(
    baseUrl: String,
    appToken: String
) = module {

    factory(named("formInterceptor")) {
        Interceptor { chain ->
            val original = chain.request()

            val request =
                original.newBuilder()
                    .header("x-api-key", appToken)
                    .method(original.method, original.body)
                    .build()

            chain.proceed(request)
        }
    }


    single(named("formClient")) {
        OkHttpClient.Builder().addInterceptor(get(named("formInterceptor")) as Interceptor)
            .connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, TimeUnit.MINUTES)
            .build()
    }

    factory(named("formRetrofit")) {
        Retrofit.Builder()
            .client(get(named("formClient")))
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    factory(named("formService")) {
        get<Retrofit>(named("formRetrofit")).create(
            FormService::class.java
        )
    }

    factory(named("FormzDatasource")) { FormDatasource(get(named("formService"))) }
}
