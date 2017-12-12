package br.com.pilovieira.tk303g.business;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.pilovieira.tk303g.R;

import static android.content.Intent.ACTION_VIEW;

public class ListenerProvider {

    public static void openGeo(View view, final String lat, final String lng) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format("geo:%s,%s?q=%s,%s", lat, lng, lat, lng);
                Intent intent = new Intent(ACTION_VIEW,	Uri.parse(uri));
                view.getContext().startActivity(intent);
            }
        });
    }

    public static void openDialogOneParam(final Fragment fragment, final Button btn, final int titleParam1, final CommandOneParam commandParam) {
        openDialogOneParam(fragment, btn.getText().toString(), titleParam1, commandParam);
    }

    public static void openDialogOneParam(final Fragment fragment, String title, final int titleParam1, final CommandOneParam commandParam) {
        final View viewOneParam = fragment.getLayoutInflater(null).inflate(R.layout.one_param, null);
        ((TextView) viewOneParam.findViewById(R.id.title_param1)).setText(titleParam1);

        final TextView textParam1 = (TextView) viewOneParam.findViewById(R.id.text_param1);

        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setTitle(title);
        builder.setView(viewOneParam);
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                commandParam.apply(textParam1.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    public static void openDialogTwoParam(final Fragment fragment, final Button btn, final int titleParam1, final int titleParam2, final CommandTwoParam commandParam) {
        final View viewOneParam = fragment.getLayoutInflater(null).inflate(R.layout.two_param, null);
        ((TextView) viewOneParam.findViewById(R.id.title_param1)).setText(titleParam1);
        ((TextView) viewOneParam.findViewById(R.id.title_param2)).setText(titleParam2);

        final TextView textParam1 = (TextView) viewOneParam.findViewById(R.id.text_param1);
        final TextView textParam2 = (TextView) viewOneParam.findViewById(R.id.text_param2);

        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setTitle(btn.getText().toString());
        builder.setView(viewOneParam);
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                commandParam.apply(textParam1.getText().toString(), textParam2.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    public static void openDialogThreeParam(final Fragment fragment, final Button btn, final int titleParam1, final int titleParam2, final int titleParam3, final CommandThreeParam commandParam) {
        final View viewOneParam = fragment.getLayoutInflater(null).inflate(R.layout.three_param, null);
        ((TextView) viewOneParam.findViewById(R.id.title_param1)).setText(titleParam1);
        ((TextView) viewOneParam.findViewById(R.id.title_param2)).setText(titleParam2);
        ((TextView) viewOneParam.findViewById(R.id.title_param3)).setText(titleParam3);

        final TextView textParam1 = (TextView) viewOneParam.findViewById(R.id.text_param1);
        final TextView textParam2 = (TextView) viewOneParam.findViewById(R.id.text_param2);
        final TextView textParam3 = (TextView) viewOneParam.findViewById(R.id.text_param3);

        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setTitle(btn.getText().toString());
        builder.setView(viewOneParam);
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                commandParam.apply(textParam1.getText().toString(), textParam2.getText().toString(), textParam3.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    public interface CommandOneParam {
        void apply(String param1Value);
    }

    public interface CommandTwoParam {
        void apply(String param1Value, String param2Value);
    }

    public interface CommandThreeParam {
        void apply(String param1Value, String param2Value, String param3Value);
    }

}
