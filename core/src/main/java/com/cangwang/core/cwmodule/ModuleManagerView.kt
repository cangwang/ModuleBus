import com.cangwang.core.cwmodule.ex.CWAbsExModule
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.pm.ApplicationInfo
import android.content.res.AssetManager
import org.json.JSONException
import com.cangwang.model.ICWModule
import com.cangwang.core.cwmodule.ex.CWFacotry
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import com.cangwang.core.MBaseApi
import androidx.fragment.app.FragmentActivity
import android.view.LayoutInflater
import com.cangwang.core.cwmodule.api.ModuleBackpress
import androidx.annotation.CallSuper
import com.cangwang.core.cwmodule.api.BackPressStack
import androidx.annotation.LayoutRes
import com.cangwang.core.ModuleApiManager
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.os.Looper
import com.cangwang.core.cwmodule.ex.CWModuleExFactory
import com.cangwang.core.cwmodule.ex.ModuleExManager
import com.cangwang.core.R
import com.cangwang.core.ModuleBus
import androidx.collection.SparseArrayCompat
import com.cangwang.core.ModuleCenter
import com.cangwang.core.ModuleEvent
import com.cangwang.core.IBaseClient
import com.cangwang.core.cwmodule.ex.ModuleLoadListener
import androidx.appcompat.app.AppCompatActivity
import com.cangwang.model.ModuleMeta
import android.content.Intent
import com.cangwang.bean.ModuleUnitBean