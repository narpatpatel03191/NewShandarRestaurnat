package narpatpatel031999.com.newshandarrestaurant;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class ProgressDialog  extends Dialog {
    public ProgressDialog(@NonNull Context context) {
        super(context);
        WindowManager.LayoutParams params=getWindow().getAttributes();

        params.gravity= Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(params);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        View view= LayoutInflater.from(context).inflate(R.layout.progressdialog,null);
        setContentView(view);
    }
}
