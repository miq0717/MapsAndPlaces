package miq0717.mapsandplaces.customcontrols;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

//import timber.log.Timber;

/**
 * Created by Zahidul Islam on 19-11-16.
 */
public class InstantAutoComplete extends AppCompatAutoCompleteTextView {
    public InstantAutoComplete(Context context) {
        super(context);
    }

    public InstantAutoComplete(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public InstantAutoComplete(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (getText().toString().length() > 0) {
            if (focused && getAdapter() != null) {
//            if (focused && getAdapter() != null && getFilter() != null && !isPopupShowing()) {
//                Timber.e("onFocusChanged if");
                performFiltering(getText(), 1);
            }
//            else {
//                Timber.e("onFocusChanged else");
//                performFiltering("", 0);
//            }
        }
    }
}
