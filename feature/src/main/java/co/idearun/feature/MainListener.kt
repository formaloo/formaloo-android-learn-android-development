package co.idearun.feature

import android.content.Intent
import android.net.Uri
import android.view.View
import org.koin.core.KoinComponent

interface MainListener : KoinComponent {
    fun openFormaloo(v: View?) {
        v?.context?.let {context->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(context.getString(R.string.formaloo_site_address))
            context.startActivity(intent)
        }
    }
}