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

	/**
	 * Invokes onCreateHeaderViewHolder, onCreateItemHolder or onCreateFooterViewHolder methods
	 * based on the view type param.
	 */
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

	/**
	 * Invokes onBindHeaderViewHolder, onBindItemHolder or onBindFooterViewHOlder methods based
	 * on the position param.
	 */
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

	/**
	 * Returns the type associated to an item given a position passed as arguments. If the position
	 * is related to a header item returns the constant TYPE_HEADER or TYPE_FOOTER if the position is
	 * related to the footer, if not, returns TYPE_ITEM.
	 *
	 * If your application has to support different types override this method and provide your
	 * implementation. Remember that TYPE_HEADER, TYPE_ITEM and TYPE_FOOTER are internal constants
	 * can be used to identify an item given a position, try to use different values in your
	 * application.
	 */
	@Override
	public int getItemViewType(int position) {
		int viewType = TYPE_ITEM;
		if (isHeaderPosition(position)) {
			viewType = TYPE_HEADER;
		}
		return viewType;
	}

	/**
	 * Returns the items list size if there is no a header configured or the size taking into account
	 * that if a header or a footer is configured the number of items returned is going to include
	 * this elements.
	 */
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

	/**
	 * Returns true if the position type parameter passed as argument is equals to 0 and the adapter
	 * has a not null header already configured.
	 */
	public boolean isHeaderPosition(int position) {
		return hasHeader() && position == 0;
	}

	/**
	 * Returns true if the view type parameter passed as argument is equals to TYPE_HEADER.
	 */
	protected boolean isHeaderType(int viewType) {
		return viewType == TYPE_HEADER;
	}

	/**
	 * Returns true if the header configured is not null.
	 */
	protected boolean hasHeader() {
		return recyclerHeader != null;
	}


	public interface RecyclerHeader<H> {
		H onCreateHeaderHolder(ViewGroup parent, int viewType);
		void onBindHeaderHolder(H holder, int position);
	}
}
