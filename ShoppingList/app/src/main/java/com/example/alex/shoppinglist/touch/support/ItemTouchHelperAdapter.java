package com.example.alex.shoppinglist.touch.support;

public interface ItemTouchHelperAdapter {

    void onItemDismiss(int position);

    void onItemMove(int fromPosition, int toPosition);
}
