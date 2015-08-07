package com.hovans.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * HeaderFooterCecyclerAdapter.java
 *
 * @author Hovan Yoo
 */
public abstract class HeaderFooterRecyclerAdapter<VH extends RecyclerView.ViewHolder, H extends RecyclerView.ViewHolder, F extends RecyclerView.ViewHolder> extends HeaderRecyclerAdapter<VH, H> {

	protected static final int TYPE_FOOTER = -3;

	RecyclerFooter<F> recyclerFooter;

	public void setRecyclerFooter(RecyclerFooter<F> recyclerFooter) {
		this.recyclerFooter = recyclerFooter;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RecyclerView.ViewHolder viewHolder;
		if (isFooterType(viewType)) {
			viewHolder = recyclerFooter.onCreateFooterHolder(parent, viewType);
		} else {
			viewHolder = super.onCreateViewHolder(parent, viewType);
		}
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (isFooterPosition(position)) {
			recyclerFooter.onBindFooterHolder((F) holder, position);
		} else {
			super.onBindViewHolder(holder, position);
		}
	}

	@Override
	public int getItemCount() {
		int size = super.getItemCount();
		if (hasFooter()) {
			size++;
		}
		return size;
	}

	@Override
	public int getItemViewType(int position) {
		if (isFooterPosition(position)) {
			return TYPE_FOOTER;
		}
		return super.getItemViewType(position);
	}

	protected boolean isFooterType(int viewType) {
		return viewType == TYPE_FOOTER;
	}

	public boolean isFooterPosition(int position) {
		int lastPosition = getItemCount() - 1;
		return hasFooter() && position == lastPosition;
	}

	/**
	 * Returns true if the footer configured is not null.
	 */
	protected boolean hasFooter() {
		return recyclerFooter != null;
	}

	public interface RecyclerFooter<F> {
		F onCreateFooterHolder(ViewGroup parent, int viewType);
		void onBindFooterHolder(F holder, int position);
	}
}
