package org.nv95.openmanga.ui.reader;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.nv95.openmanga.content.MangaPage;
import org.nv95.openmanga.ui.AppBaseFragment;
import org.nv95.openmanga.utils.CollectionsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koitharu on 08.01.18.
 */

public abstract class ReaderFragment extends AppBaseFragment implements ReaderCallback {

	@NonNull
	private final ArrayList<MangaPage> mPages = new ArrayList<>();
	@Nullable
	private ReaderCallback mCallback = null;

	public void setPages(List<MangaPage> pages) {
		mPages.clear();
		mPages.addAll(pages);
	}

	public ArrayList<MangaPage> getPages() {
		return mPages;
	}

	public abstract int getCurrentPageIndex();

	@Nullable
	public MangaPage getCurrentPage() {
		return CollectionsUtils.getOrNull(mPages, getCurrentPageIndex());
	}

	public abstract void scrollToPage(int index);

	public abstract void smoothScrollToPage(int index);

	public boolean scrollToPageById(long id) {
		for (int i = 0; i < mPages.size(); i++) {
			MangaPage o = mPages.get(i);
			if (o.id == id) {
				scrollToPage(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public void onPageChanged(int page) {
		if (mCallback != null) {
			mCallback.onPageChanged(page);
		}
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Activity activity = getActivity();
		if (activity != null && activity instanceof ReaderCallback) {
			mCallback = (ReaderCallback) activity;
		}
	}

	public abstract void moveLeft();

	public abstract void moveRight();

	public abstract void moveUp();

	public abstract void moveDown();

	protected void toggleUi() {
		final Activity activity = getActivity();
		if (activity != null && activity instanceof ReaderActivity) {
			((ReaderActivity) activity).toggleUi();
		}
	}
}
