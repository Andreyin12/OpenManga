CREATE TABLE history (
	id INTEGER PRIMARY KEY,
	name TEXT NOT NULL,
	summary TEXT,
	genres TEXT,
	url TEXT NOT NULL,
	thumbnail TEXT,
	provider TEXT NOT NULL,
	status INTEGER,
	rating INTEGER,
	chapter_id INTEGER NOT NULL,
    page_id INTEGER NOT NULL,
    updated_at INTEGER NOT NULL,
    reader_preset INTEGER,
    total_chapters INTEGER,
    total_pages_in_chapter INTEGER,
    removed INTEGER DEFAULT 0
);

CREATE TABLE favourites (
	id INTEGER PRIMARY KEY,
	name TEXT NOT NULL,
	summary TEXT,
	genres TEXT,
	url TEXT NOT NULL,
	thumbnail TEXT,
	provider TEXT NOT NULL,
	status INTEGER,
	rating INTEGER,
	created_at INTEGER,
	category_id INTEGER,
	total_chapters INTEGER,
	new_chapters INTEGER,
	removed INTEGER DEFAULT 0
);

CREATE TABLE search_history (
	query TEXT PRIMARY KEY,
	updated_at INTEGER
);

CREATE TABLE bookmarks (
	id INTEGER PRIMARY KEY,
	manga_id INTEGER,
    name TEXT NOT NULL,
	summary TEXT,
    genres TEXT,
    url TEXT NOT NULL,
    thumbnail TEXT,
    provider TEXT NOT NULL,
    status INTEGER,
    rating INTEGER,
	chapter_id INTEGER,
	page_id INTEGER,
	created_at INTEGER,
	removed INTEGER DEFAULT 0
);

CREATE TABLE saved (
	id INTEGER PRIMARY KEY,
	name TEXT NOT NULL,
	summary TEXT NOT NULL,
	genres TEXT NOT NULL,
	url TEXT NOT NULL,
	thumbnail TEXT NOT NULL,
	provider TEXT NOT NULL,
	status INTEGER,
	rating INTEGER,
	created_at INTEGER,
	local_path TEXT NOT NULL
);

CREATE TABLE saved_chapters (
	id INTEGER PRIMARY KEY,
	name TEXT NOT NULL,
	number INTEGER NOT NULL,
	url TEXT
);

CREATE TABLE saved_pages (
	id INTEGER PRIMARY KEY,
	url TEXT NOT NULL,
	number INTEGER
);

CREATE TABLE categories (
	id INTEGER PRIMARY KEY,
	name TEXT NOT NULL,
	created_at INTEGER
)