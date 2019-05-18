package com.softaai.randomimagerenderers.renderers;

import androidx.recyclerview.widget.DiffUtil;

import com.pedrogomez.renderers.AdapteeCollection;

import java.util.List;

/**
 * Created by Amol Pawar on 18-05-2019.
 * softAai Apps
 */

class DiffCallback<T> extends DiffUtil.Callback {

    private final AdapteeCollection<T> oldList;
    private final List<T> newList;

    DiffCallback(AdapteeCollection<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override public int getOldListSize() {
        return oldList.size();
    }

    @Override public int getNewListSize() {
        return newList.size();
    }

    @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = oldList.get(oldItemPosition);
        T newItem = newList.get(newItemPosition);
        boolean areTheSameInstance = oldItem == newItem;
        boolean hasTheSameType = oldItem.getClass().equals(newItem.getClass());
        boolean hasTheSameHash = oldItem.hashCode() == newItem.hashCode();
        return areTheSameInstance || hasTheSameType && hasTheSameHash;
    }

    @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
