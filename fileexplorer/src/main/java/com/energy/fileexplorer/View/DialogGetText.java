package com.energy.fileexplorer.View;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.energy.fileexplorer.R;

/**
 * Created by MFC on 30/05/2014.
 */
public abstract class DialogGetText extends Dialog
{
    private String mMessage = "";
    private TextView mText;
    private Button mYes;
    private Button mNo;

    public DialogGetText(Context context, String message)
    {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = getLayoutInflater().inflate(R.layout.drialog_get_text, null);
        mText = (TextView) v.findViewById(R.id.dialogText);
        mMessage = message;
        mText.setText(mMessage);
        mYes = (Button) v.findViewById(R.id.dialogAcept);
        mYes.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                dismiss();
                onYes();
            }
        });
        mNo = (Button) v.findViewById(R.id.dialogCancel);
        mNo.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                dismiss();
                onNo();
            }
        });
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(v);
    }

    public abstract void onYes();

    public abstract void onNo();

}