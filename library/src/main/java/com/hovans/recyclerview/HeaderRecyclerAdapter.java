package com.hovans.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * HeaderRecyclerAdapter.java
 *
 * @author Hovan Yoo
 */
public abstract class HeaderRecyclerAdapter<VH extends RecyclerView.ViewHolder, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	protected static final int TYPE_HEADER = -2;
	protected static final int TYPE_ITEM = -1;

	RecyclerHeader<H> recyclerHeader;

	public void setRecyclerHeader(RecyclerHeader<H> recyclerHeader) {
		this.recyclerHeader = recyclerHeader;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RecyclerView.ViewHolder viewHolder;
		if (isHeaderType(viewType)) {
			viewHolder = recyclerHeader.onCreateHeaderHolder(parent, viewType);
		} else {
			viewHolder = onCreateItemHolder(parent, viewType);
		}
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (isHeaderPosition(position)) {
			recyclerHeader.onBindHeaderHolder((H)holder, position);
		} else {
			int itemPosition = position;
			if(hasHeader()) --itemPosition;
			onBindItemHolder((VH)holder, itemPosition);
		}
	}

	@Override
	public int getItemViewType(int position) {
		int viewType = TYPE_ITEM;
		if (isHeaderPosition(position)) {
			viewType = TYPE_HEADER;
		}
		return viewType;
	}

	@Override
	public int getItemCount() {
		int size = getCount();
		if (hasHeader()) {
			size++;
		}

		return size;
	}

	protected abstract int getCount();

	protected abstract VH onCreateItemHolder(ViewGroup parent, int viewType);

	protected abstract void onBindItemHolder(VH holder, int position);

	public boolean isHeaderPosition(int position) {
		return hasHeader() && position == 0;
	}

	protected boolean isHeaderType(int viewType) {
		return viewType == TYPE_HEADER;
	}

	protected boolean hasHeader() {
		return recyclerHeader != null;
	}


	public interface RecyclerHeader<H> {
		H onCreateHeaderHolder(ViewGroup parent, int viewType);
		void onBindHeaderHolder(H holder, int position);
	}
}
