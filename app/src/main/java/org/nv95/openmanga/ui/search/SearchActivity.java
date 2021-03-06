package org.nv95.openmanga.ui.search;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.nv95.openmanga.R;
import org.nv95.openmanga.content.MangaHeader;
import org.nv95.openmanga.content.ProviderHeader;
import org.nv95.openmanga.content.SearchQueryArguments;
import org.nv95.openmanga.content.providers.MangaProvider;
import org.nv95.openmanga.ui.AppBaseActivity;
import org.nv95.openmanga.ui.common.EndlessRecyclerView;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by koitharu on 06.01.18.
 */

public final class SearchActivity extends AppBaseActivity implements LoaderManager.LoaderCallbacks<ArrayList<MangaHeader>>,
		EndlessRecyclerView.OnLoadMoreListener, View.OnClickListener {

	private EndlessRecyclerView mRecyclerView;
	private ProgressBar mProgressBar;
	private TextView mTextViewHolder;

	private final Stack<ProviderHeader> mProviders = new Stack<>();
	private SearchQueryArguments mQueryArguments;

	private final ArrayList<Object> mDataset = new ArrayList<>();
	private SearchAdapter mAdapter;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setSupportActionBar(R.id.toolbar);
		enableHomeAsUp();

		mRecyclerView = findViewById(R.id.recyclerView);
		mProgressBar = findViewById(R.id.progressBar);
		mTextViewHolder = findViewById(R.id.textView_holder);

		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setOnLoadMoreListener(this);

		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			beginSearch(query);
		}
	}

	private void beginSearch(@NonNull String query) {
		mProgressBar.setVisibility(View.VISIBLE);
		mTextViewHolder.setVisibility(View.GONE);
		setSubtitle(query);
		SearchSuggestionsProvider.getSuggestions(this).saveRecentQuery(query, null);
		mProviders.clear();
		mProviders.addAll(MangaProvider.getAvailableProviders(this));
		mQueryArguments = new SearchQueryArguments(query, mProviders.pop().cName);
		mDataset.clear();
		mAdapter = new SearchAdapter(mDataset);
		mRecyclerView.setAdapter(mAdapter);
		getLoaderManager().initLoader(0, mQueryArguments.toBundle(), this).forceLoad();
	}

	@Override
	public Loader<ArrayList<MangaHeader>> onCreateLoader(int id, Bundle args) {
		return new SearchLoader(this, SearchQueryArguments.from(args));
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<MangaHeader>> loader, @Nullable ArrayList<MangaHeader> data) {
		if (data == null) {
			Snackbar.make(mRecyclerView, R.string.loading_error, Snackbar.LENGTH_INDEFINITE)
					.setAction(R.string.retry, this)
					.show();
			mProgressBar.setVisibility(View.GONE);
			if (mDataset.isEmpty()) {
				mTextViewHolder.setVisibility(View.VISIBLE);
			}
			mRecyclerView.onLoadingFinished(false);
		} else if (data.isEmpty()) {
			//next provider
			if (mProviders.empty()) {
				mProgressBar.setVisibility(View.GONE);
				mRecyclerView.onLoadingFinished(false);
				if (mDataset.isEmpty()) {
					mTextViewHolder.setVisibility(View.VISIBLE);
				}
			} else {
				mQueryArguments.page = 0;
				mQueryArguments.providerCName = mProviders.pop().cName;
				mRecyclerView.onLoadingFinished(true);
			}
		} else {
			mProgressBar.setVisibility(View.GONE);
			int firstPos = mDataset.size();
			mDataset.addAll(data);
			if (firstPos == 0) {
				mAdapter.notifyDataSetChanged();
			} else {
				mAdapter.notifyItemRangeInserted(firstPos, data.size());
			}
			mQueryArguments.page++;
			mRecyclerView.onLoadingFinished(true);
		}
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<MangaHeader>> loader) {

	}

	@Override
	public boolean onLoadMore() {
		getLoaderManager().restartLoader(0, mQueryArguments.toBundle(), this).forceLoad();
		return true;
	}

	@Override
	public void onClick(View v) {
		if (mDataset.isEmpty()) {
			mProgressBar.setVisibility(View.VISIBLE);
		}
		mTextViewHolder.setVisibility(View.GONE);
		onLoadMore();
	}
}
