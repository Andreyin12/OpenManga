package org.nv95.openmanga.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;

import org.nv95.openmanga.content.MangaChapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by koitharu on 26.12.17.
 */

public final class CollectionsUtils {

	@Nullable
	public static MangaChapter findItemById(Collection<MangaChapter> collection, long id) {
		for (MangaChapter o : collection) {
			if (o.id == id) {
				return o;
			}
		}
		return null;
	}

	public static int findPositionById(List<MangaChapter> list, long id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).id == id) {
				return i;
			}
		}
		return -1;
	}

	@Nullable
	public static <T> T getOrNull(List<T> list, int position) {
		try {
			return list.get(position);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T getOrDefault(List<T> list, int position, @Nullable T defaultValue) {
		try {
			return list.get(position);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static <T> ArrayList<T> getIfTrue(T[] items, SparseBooleanArray booleanArray) {
		final ArrayList<T> values = new ArrayList<>();
		for (int i = 0; i < items.length; i++) {
			if (booleanArray.get(i, false)) {
				values.add(items[i]);
			}
		}
		return values;
	}

	public static <T> boolean contains(@NonNull T[] array, T value) {
		for (T o : array) {
			if (o != null && o.equals(value)) {
				return true;
			}
		}
		return false;
	}

	public static String toString(@NonNull Object[] elements, @NonNull String delimiter) {
		final StringBuilder builder = new StringBuilder();
		boolean nonFirst = false;
		for (Object o: elements) {
			if (nonFirst) {
				builder.append(delimiter);
			} else {
				nonFirst = true;
			}
			builder.append(o.toString());
		}
		return builder.toString();
	}

	public static <T> boolean removeByValue(Map<?, T> map, T value) {
		for (Map.Entry<?, T> o : map.entrySet()) {
			if (o.getValue() == value) {
				map.remove(o.getKey());
				return true;
			}
		}
		return false;
	}
}
