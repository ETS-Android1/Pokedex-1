package com.example.pokedex.ui.adapter.binding_adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.pokedex.R;

public class CommonBindingAdapter {
    @BindingAdapter(value = {"imageUrl", "placeHolder"}, requireAll = false)
    public static void imageUrl(ImageView view, String url, Drawable placeHolder) {
        placeHolder = placeHolder == null ? AppCompatResources.getDrawable(view.getContext(), R.drawable.placeholder) : placeHolder;
        Glide.with(view.getContext()).load(url).placeholder(placeHolder).into(view);
    }
}
